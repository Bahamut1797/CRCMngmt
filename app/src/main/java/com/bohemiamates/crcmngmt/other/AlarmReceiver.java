package com.bohemiamates.crcmngmt.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bohemiamates.crcmngmt.R;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.models.ClanWarLog;
import com.bohemiamates.crcmngmt.models.Participant;
import com.bohemiamates.crcmngmt.repositories.ClanRepository;
import com.bohemiamates.crcmngmt.repositories.PlayerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    private String mClanTag;
    private PrefManager prefManager;
    private List<Player> playerList;
    private PlayerRepository mPlayerRepository;
    private ClanRepository mRepository;
    private Context ctx;

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String ACTION = "com.bohemiamates.crcmngmt.UP_TO_DATE";

        Calendar c = Calendar.getInstance();
        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        Log.i("TIME", df.format(c.getTime()) );

        ctx = context;

        String action = intent.getAction();
        if (ACTION.equals(action)) {

            prefManager = new PrefManager(context);
            mClanTag = prefManager.getClanTag();

            // Clan update
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = context.getString(R.string.url) + "clan/" + mClanTag;

            StringRequest request = new StringRequest(Request.Method.GET, URL,
                    onClanLoaded, onClanError) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + context.getString(R.string.key));
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(5000, 2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);
        }
    }

    com.bohemiamates.crcmngmt.entities.Clan mClan;

    private final Response.Listener<String> onClanLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson gson = new GsonBuilder().create();
            com.bohemiamates.crcmngmt.models.Clan clan = gson.fromJson(response, com.bohemiamates.crcmngmt.models.Clan.class);

            mClan = new com.bohemiamates.crcmngmt.entities.Clan(clan);

            mRepository = new ClanRepository(ctx);
            mRepository.update(mClan);

            List<Player> mPlayers = clan.getMembers();

            // Set default attrs
            for (Player player :
                    mPlayers) {
                player.setClanTag(clan.getTag());
                player.setClanFails(0);
                player.setClanBadgeUri(clan.getBadge().getImage());
                player.setBattleLog("");
            }

            mPlayerRepository = new PlayerRepository(ctx);
            playerList = mPlayerRepository.getPlayers2(mClanTag);

            // Update current members and delete old ones
            for (Player current : playerList) {
                boolean delete = true;
                for (Player mCurrent : mPlayers) {
                    if (current.getTag().equals(mCurrent.getTag())) {
                        mCurrent.setClanFails(current.getClanFails());
                        mCurrent.setDateFail1(current.getDateFail1());
                        mCurrent.setDateFail2(current.getDateFail2());
                        mCurrent.setDateFail3(current.getDateFail3());

                        mCurrent.setTotalWinsMonth(current.getTotalWinsMonth());
                        mCurrent.setTotalWins(current.getTotalWins());
                        mCurrent.setTotalFailsMonth(current.getTotalFailsMonth());
                        mCurrent.setTotalFails(current.getTotalFails());
                        mPlayerRepository.update(mCurrent);
                        delete = false;
                        break;
                    }
                }

                if (delete) {
                    mPlayerRepository.delete(current);
                }
            }

            // Insert new members
            for (Player mCurrent : mPlayers) {
                boolean exist = false;
                for (Player current : playerList) {
                    if (mCurrent.getTag().equals(current.getTag())) {
                        exist = true;
                        break;
                    }
                }

                if (!exist) {
                    mPlayerRepository.insert(mCurrent);
                }
            }

            // Update clan fails
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            String URL = ctx.getString(R.string.url) + "clan/" + mClanTag + "/warlog";

            StringRequest request = new StringRequest(Request.Method.GET, URL,
                    onWarlogLoaded, onWarlogError) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + ctx.getString(R.string.key));
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(5000, 2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);
        }
    };

    private final Response.ErrorListener onClanError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(ctx, ctx.getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
        }
    };

    private final Response.Listener<String> onWarlogLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Type listType = new TypeToken<ArrayList<ClanWarLog>>() {}.getType();
            List<ClanWarLog> warLog = new Gson().fromJson(response, listType);

            // Reset clan fails the 1st day of month
            Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);

            calendar.setTimeInMillis(prefManager.getClanWarTime() * 1000);
            int warLogMonth = calendar.get(Calendar.MONTH);
            int warLogYear = calendar.get(Calendar.YEAR);

            if ((warLogYear == currentYear && currentMonth > warLogMonth) || (currentYear > warLogYear)) {
                for (Player current : playerList) {
                    current.setClanFails(0);
                    current.setDateFail1(0L);
                    current.setDateFail2(0L);
                    current.setDateFail3(0L);

                    current.setTotalFails(current.getTotalFails() + current.getTotalFailsMonth());
                    current.setTotalFailsMonth(0);

                    current.setTotalWins(current.getTotalWins() + current.getTotalWinsMonth());
                    current.setTotalWinsMonth(0);
                }

                calendar.set(currentYear, currentMonth, 1, 0, 0);

                prefManager.setClanWarTime(calendar.getTimeInMillis() / 1000);

                mPlayerRepository.updateAll(playerList);
            }

            if (warLog.size() > 0) {
                for (int i = 0; i < warLog.size(); i++) {
                    ClanWarLog clanWarLog = warLog.get(i);

                    Calendar c = Calendar.getInstance();
                    long clanWarTime = TimeConverter.UTCDateTime(clanWarLog.getWarEndTime());
                    c.setTimeInMillis(clanWarTime);
                    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    String formatted = format1.format(c.getTime());

                    List<Player> mPlayers = playerList;

                    List<Participant> participants = clanWarLog.getParticipants();

                    for (Player player :
                            mPlayers) {
                        for (Participant participant : participants) {
                            if (participant.getTag().equals(player.getTag())) {
                                player.setBattleLog(player.getBattleLog() + formatted + ":"
                                        + participant.getBattlesPlayed() + ":"
                                        + participant.getWins() + "|");
                            }
                        }
                    }
                }

                mPlayerRepository.updateAll(playerList);

                for (int i = warLog.size() - 1; i > -1; i--) {
                    ClanWarLog clanWarLog = warLog.get(i);
                    // Log.i("WARLOG", clanWarLog.toString());
                    long clanWarTime = TimeConverter.UTCDateTime(clanWarLog.getWarEndTime());
                    if (clanWarTime > prefManager.getClanWarTime()) {

                        List<Player> mPlayers = playerList;

                        List<Participant> participants = clanWarLog.getParticipants();

                        for (Player player :
                                mPlayers) {
                            for (Participant participant : participants) {
                                if (participant.getTag().equals(player.getTag())) {
                                    if (participant.getBattlesPlayed() == 0) {
                                        player.setClanFails(player.getClanFails() + 1);
                                        player.setTotalFailsMonth(player.getTotalFailsMonth() + 1);

                                        if (player.getDateFail1() == 0) {
                                            player.setDateFail1(clanWarTime);
                                        } else if (player.getDateFail2() == 0) {
                                            player.setDateFail2(clanWarTime);
                                        } else if (player.getDateFail3() == 0) {
                                            player.setDateFail3(clanWarTime);
                                        }
                                    } else {
                                        if (participant.getWins() == participant.getBattlesPlayed()) {
                                            player.setTotalWinsMonth(player.getTotalWinsMonth() + participant.getWins());
                                        } else {
                                            int losses = participant.getBattlesPlayed() - participant.getWins();
                                            player.setTotalWinsMonth(player.getTotalWinsMonth() + participant.getWins());
                                            player.setTotalFailsMonth(player.getTotalFailsMonth() + losses);
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        mPlayerRepository.updateAll(mPlayers);
                        prefManager.setClanWarTime(clanWarTime);
                    }
                }
            } else {
                Toast.makeText(ctx, ctx.getResources().getString(R.string.clanWarLog), Toast.LENGTH_SHORT).show();
            }

            mClan.setLastWarTime(prefManager.getClanWarTime());
            mRepository.update(mClan);

            Toast.makeText(ctx, ctx.getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
        }
    };

    private final Response.ErrorListener onWarlogError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(ctx, ctx.getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
        }
    };
}