package com.bohemiamates.crcmngmt.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.models.Clan;
import com.bohemiamates.crcmngmt.models.ClanWarLog;
import com.bohemiamates.crcmngmt.models.Participant;
import com.bohemiamates.crcmngmt.other.PrefManager;
import com.bohemiamates.crcmngmt.repositories.ClanRepository;
import com.bohemiamates.crcmngmt.repositories.PlayerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    public TextView txtClanTag;
    public ProgressDialog mDialog;
    public Button btnGetClan;
    public PrefManager prefManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstClanInit()) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("CLAN_TAG", prefManager.getClanTag());
            launchHomeScreen();
            finish();
        }

        btnGetClan = findViewById(R.id.btnGetClan);
        txtClanTag = findViewById(R.id.txtClanTag);

        mDialog = new ProgressDialog(this);

        btnGetClan.setOnClickListener(new btnOnClickListener());
    }

    private class btnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String URL = getString(R.string.url) + "clan/" + txtClanTag.getText() + "/warlog";
            RequestQueue requestQueue;

            hideKeyboard(StartActivity.this);

            btnGetClan.setEnabled(false);

            mDialog.setMessage("Please wait...");
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            requestQueue = Volley.newRequestQueue(getApplicationContext());


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
    }

    private List<ClanWarLog> warLog;

    private final Response.Listener<String> onWarlogLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);

            Type listType = new TypeToken<ArrayList<ClanWarLog>>(){}.getType();
            warLog = new Gson().fromJson(response, listType);

            if (warLog.size() > 0) {
                Log.i("WARLOG", warLog.size() + " - " + warLog.get(0).toString());
                prefManager.setClanWarTime(warLog.get(0).getCreatedDate());
            } else {
                Toast.makeText(getApplicationContext(), "Clan doesn't have a War log yet, getting members...", Toast.LENGTH_LONG).show();
            }


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            String URL = getString(R.string.url) + "clan/" + txtClanTag.getText();

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
    };

    private final Response.ErrorListener onWarlogError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());

            Toast.makeText(getApplicationContext(), "Clan maybe doesn't exist, try again.", Toast.LENGTH_LONG).show();

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }

            btnGetClan.setEnabled(true);
        }
    };

    private final Response.Listener<String> onClanLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);

            Gson gson = new GsonBuilder().create();
            Clan clan = gson.fromJson(response, Clan.class);

            com.bohemiamates.crcmngmt.entities.Clan mClan = new com.bohemiamates.crcmngmt.entities.Clan(clan);

            new ClanRepository(getApplication()).insert(mClan);

            List<Player> mPlayers = clan.getMembers();

            for (Player player :
                    mPlayers) {
                player.setClanTag(clan.getTag());
                player.setClanFails(0);
                player.setClanBadgeUri(clan.getBadge().getImage());
            }

            for (ClanWarLog clanWarLog : warLog) {
                List<Participant> participants = clanWarLog.getParticipants();

                for (Player player : mPlayers) {
                    for (Participant participant : participants) {
                        if (participant.getTag().equals(player.getTag())) {
                            if (participant.getBattlesPlayed() == 0) {
                                player.setClanFails(player.getClanFails() + 1);
                            }
                            break;
                        }
                    }
                }
            }

            new PlayerRepository(getApplication()).insertAll(mPlayers);

            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("CLAN_TAG", clan.getTag());
            prefManager.setClanTag(clan.getTag());
            launchHomeScreen();
        }
    };

    private final Response.ErrorListener onClanError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }

            btnGetClan.setEnabled(true);

            Toast.makeText(getApplicationContext(), "Clan maybe doesn't exist, try again.", Toast.LENGTH_LONG).show();
        }
    };

    private void launchHomeScreen() {
        prefManager.setFirstClanInit(false);

        startActivity(intent);
        finish();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
