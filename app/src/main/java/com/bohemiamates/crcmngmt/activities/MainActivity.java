package com.bohemiamates.crcmngmt.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bohemiamates.crcmngmt.R;
import com.bohemiamates.crcmngmt.adapters.PlayerListAdapter;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.models.ClanWarLog;
import com.bohemiamates.crcmngmt.models.Participant;
import com.bohemiamates.crcmngmt.other.PrefManager;
import com.bohemiamates.crcmngmt.viewModels.ClanViewModel;
import com.bohemiamates.crcmngmt.viewModels.PlayerViewModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PlayerViewModel mPlayerViewModel;
    private ClanViewModel mClanViewModel;
    private String mClanTag;
    RecyclerView recyclerView;
    PrefManager prefManager;
    private TextView clanName;
    private TextView clanDesc;
    private ImageView clanBadge;
    private List<Player> playerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mClanTag = getIntent().getStringExtra("CLAN_TAG");

        prefManager = new PrefManager(this);
        mDialog = new ProgressDialog(this);

        // RecycleView with Adapter
        recyclerView = findViewById(R.id.recyclerview);
        final PlayerListAdapter adapter = new PlayerListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // PlayerViewModel
        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        mPlayerViewModel.getAllPlayers(mClanTag).observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable final List<Player> players) {
                // Update the cached copy of the players in the adapter.
                adapter.setPlayers(players);
                playerList = players;
            }
        });

        // ClanViewModel
        mClanViewModel = ViewModelProviders.of(this).get(ClanViewModel.class);

        mClanViewModel.getClan(mClanTag).observe(this, new Observer<Clan>() {
            @Override
            public void onChanged(@Nullable final Clan clan) {
                if (clan != null) {
                    clanName.setText(clan.getName());
                    clanDesc.setText(clan.getDescription());
                    Glide.with(getApplication())
                            .load(clan.getBadge().getImage())
                            .thumbnail(0.5f)
                            .into(clanBadge);
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);
        clanBadge = navHeader.findViewById(R.id.clanBadge);
        clanName = navHeader.findViewById(R.id.clanName);
        clanDesc = navHeader.findViewById(R.id.clanDesc);

        //refreshData();
    }

    public ProgressDialog mDialog;

    private void refreshData() {
        mDialog.setMessage(getResources().getString(R.string.refreshData));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        // Clan update
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String URL = getString(R.string.url) + "clan/" + mClanTag;

        StringRequest request = new StringRequest(Request.Method.GET, URL,
                onClanLoaded, onClanError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("Authorization", "Bearer " + getString(R.string.key));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy( 5000, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    private final Response.Listener<String> onClanLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("Clan members", response);

            Gson gson = new GsonBuilder().create();
            com.bohemiamates.crcmngmt.models.Clan clan = gson.fromJson(response, com.bohemiamates.crcmngmt.models.Clan.class);

            com.bohemiamates.crcmngmt.entities.Clan mClan = new com.bohemiamates.crcmngmt.entities.Clan(clan);

            mClanViewModel.update(mClan);

            List<Player> mPlayers = clan.getMembers();

            // Set default attrs
            for (Player player :
                    mPlayers) {
                player.setClanTag(clan.getTag());
                player.setClanFails(0);
                player.setClanBadgeUri(clan.getBadge().getImage());
            }

            // Update current members and delete old ones
            for (Player current : playerList) {
                boolean delete = true;
                for (Player mCurrent : mPlayers) {
                    if (current.getTag().equals(mCurrent.getTag())) {
                        mCurrent.setClanFails(current.getClanFails());
                        mPlayerViewModel.update(mCurrent);
                        delete = false;
                        break;
                    }
                }

                if (delete) {
                    mPlayerViewModel.delete(current);
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
                    mPlayerViewModel.insert(mCurrent);
                }
            }

            // Update clan fails
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL = getString(R.string.url) + "clan/" + mClanTag + "/warlog";

            StringRequest request = new StringRequest(Request.Method.GET, URL,
                    onWarlogLoaded, onWarlogError) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Authorization", "Bearer " + getString(R.string.key));
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy( 5000, 2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);
        }
    };

    private final Response.ErrorListener onClanError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Error_on_upd_clan", error.toString());

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_LONG).show();

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    };

    private final Response.Listener<String> onWarlogLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("Upd_Clan_Fails", response);

            Type listType = new TypeToken<ArrayList<ClanWarLog>>(){}.getType();
            List<ClanWarLog> warLog = new Gson().fromJson(response, listType);

            // Reset clan fails the 1st day of month
            Calendar calendar = Calendar.getInstance();
            Log.i("CURR_TIME", calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);

            calendar.setTimeInMillis(prefManager.getClanWarTime() * 1000);
            Log.i("LAST_WARLOG_TIME", calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
            int warLogMonth = calendar.get(Calendar.MONTH);
            int warLogYear = calendar.get(Calendar.YEAR);

            if ((warLogYear == currentYear && currentMonth > warLogMonth) || (currentYear > warLogYear)) {
                Log.i("RESET", "All fails set to 0.");

                for (Player current : playerList) {
                    current.setClanFails(0);
                }

                calendar.set(currentYear, currentMonth, 1, 0, 0);

                prefManager.setClanWarTime(calendar.getTimeInMillis() / 1000);

                mPlayerViewModel.updateAll(playerList);
            }

            if (warLog.size() > 0) {
                for (int i = warLog.size()-1; i > -1; i--) {
                    ClanWarLog clanWarLog = warLog.get(i);
                    // Log.i("WARLOG", clanWarLog.toString());

                    if (clanWarLog.getCreatedDate() > prefManager.getClanWarTime()) {

                        List<Player> mPlayers = playerList;

                        List<Participant> participants = clanWarLog.getParticipants();

                        for (Player player :
                                mPlayers) {
                            for (Participant participant : participants) {
                                if (participant.getTag().equals(player.getTag())) {
                                    if (participant.getBattlesPlayed() == 0) {
                                        player.setClanFails(player.getClanFails() + 1);
                                    }
                                    break;
                                }
                            }
                        }

                        mPlayerViewModel.updateAll(mPlayers);
                        prefManager.setClanWarTime(clanWarLog.getCreatedDate());
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.clanWarLog), Toast.LENGTH_LONG).show();
            }

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    };

    private final Response.ErrorListener onWarlogError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Error_clan_fails", error.toString());

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_LONG).show();

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.exitTitle));

            alert.setPositiveButton(getResources().getString(R.string.exitConfirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    System.exit(0);
                }
            });

            alert.setNegativeButton(getResources().getString(R.string.exitCancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_camera:
                // Handle the camera action
                break;
            case R.id.nav_gallery:

                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
