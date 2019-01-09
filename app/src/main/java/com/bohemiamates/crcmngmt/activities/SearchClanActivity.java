package com.bohemiamates.crcmngmt.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.bohemiamates.crcmngmt.adapters.ClanListAdapter;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.models.ClanWarLog;
import com.bohemiamates.crcmngmt.models.Participant;
import com.bohemiamates.crcmngmt.other.PrefManager;
import com.bohemiamates.crcmngmt.repositories.ClanRepository;
import com.bohemiamates.crcmngmt.repositories.PlayerRepository;
import com.bohemiamates.crcmngmt.viewModels.ClanViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchClanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PrefManager prefManager;
    public ClanListAdapter adapter;
    Button btnGetSearchClan;
    TextView txtClanTag;
    ProgressDialog mDialog;
    List<Clan> listAllClans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_clan);

        getWindow().setBackgroundDrawableResource(R.drawable.main_bg);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // RecycleView with Adapter
        recyclerView = findViewById(R.id.rv_searchclan);
        adapter = new ClanListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ClanViewModel
        ClanViewModel mClanViewModel = ViewModelProviders.of(this).get(ClanViewModel.class);
        LiveData<List<Clan>> listClans = mClanViewModel.getAllClans();
        listClans.observe(this, new Observer<List<Clan>>() {
            @Override
            public void onChanged(@Nullable final List<Clan> clans) {
                // Update the cached copy of the players in the adapter.
                adapter.setClans(clans);
                listAllClans = clans;

                String clanName = "";

                if (clans != null)
                    for (Clan clan : clans) {
                        if (clan.getTag().equals(prefManager.getClanTag())) {
                            clanName = clan.getName();
                            break;
                        }
                    }

                TextView txtCurrent = findViewById(R.id.txtCurrent);
                txtCurrent.setText(clanName);
            }
        });

        mDialog = new ProgressDialog(this);

        prefManager = new PrefManager(this);
        txtClanTag = findViewById(R.id.txtSearchClanTag);
        btnGetSearchClan = findViewById(R.id.btnGetSearchClan);
        btnGetSearchClan.setEnabled(false);
        btnGetSearchClan.setOnClickListener(new btnOnClickListener());

        txtClanTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtClanTag.getText().length() == 0 || txtClanTag.getText().toString().toUpperCase().equals(prefManager.getClanTag())) {
                    btnGetSearchClan.setEnabled(false);
                } else {
                    btnGetSearchClan.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private class btnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (txtClanTag.getText().charAt(0) == '#') {
                txtClanTag.setText(txtClanTag.getText().toString().substring(1));
            }

            txtClanTag.setText(txtClanTag.getText().toString().toUpperCase());

            hideKeyboard(SearchClanActivity.this);

            String URL = getString(R.string.url) + "clan/" + txtClanTag.getText() + "/warlog";
            RequestQueue requestQueue;

            btnGetSearchClan.setEnabled(false);

            mDialog.setMessage(getResources().getString(R.string.pleaseWait));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            requestQueue = Volley.newRequestQueue(getApplicationContext());


            StringRequest request = new StringRequest(Request.Method.GET, URL,
                    onWarlogLoaded, onWarlogError) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + getString(R.string.key));
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(5000, 2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);
        }
    }

    private List<ClanWarLog> warLog;

    private final Response.Listener<String> onWarlogLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Type listType = new TypeToken<ArrayList<ClanWarLog>>() {
            }.getType();
            warLog = new Gson().fromJson(response, listType);

            if (warLog.size() > 0) {
                prefManager.setClanWarTime(warLog.get(0).getCreatedDate());
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.clanNotWarLog), Toast.LENGTH_SHORT).show();
            }

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            String URL = getString(R.string.url) + "clan/" + txtClanTag.getText();

            StringRequest request = new StringRequest(Request.Method.GET, URL,
                    onClanLoaded, onClanError) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + getString(R.string.key));
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(5000, 2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);
        }
    };

    private final Response.ErrorListener onWarlogError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Snackbar.make(findViewById(R.id.btnGetSearchClan), getResources().getString(R.string.errorClan), Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorClan), Toast.LENGTH_LONG).show();

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }

            btnGetSearchClan.setEnabled(true);
        }
    };

    private final Response.Listener<String> onClanLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson gson = new GsonBuilder().create();
            com.bohemiamates.crcmngmt.models.Clan clan = gson.fromJson(response, com.bohemiamates.crcmngmt.models.Clan.class);

            com.bohemiamates.crcmngmt.entities.Clan mClan = new com.bohemiamates.crcmngmt.entities.Clan(clan);

            new ClanRepository(getApplication()).insert(mClan);

            List<Player> mPlayers = clan.getMembers();

            for (Player player :
                    mPlayers) {
                player.setClanTag(clan.getTag());
                player.setClanFails(0);
                player.setClanBadgeUri(clan.getBadge().getImage());
            }

            Calendar calendar = Calendar.getInstance();
            // Log.i("CURR_TIME", calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));

            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);

            for (int i = warLog.size() - 1; i > -1; i--) {
                ClanWarLog clanWarLog = warLog.get(i);

                calendar.setTimeInMillis(clanWarLog.getCreatedDate() * 1000);
                // Log.i("WARLOG_TIME", calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                int warLogMonth = calendar.get(Calendar.MONTH);
                int warLogYear = calendar.get(Calendar.YEAR);

                if (warLogYear == currentYear) {
                    if (warLogMonth == currentMonth) {
                        List<Participant> participants = clanWarLog.getParticipants();

                        for (Player player : mPlayers) {
                            for (Participant participant : participants) {
                                if (participant.getTag().equals(player.getTag())) {
                                    if (participant.getBattlesPlayed() == 0) {
                                        player.setClanFails(player.getClanFails() + 1);

                                        if (player.getDateFail1() == 0) {
                                            player.setDateFail1(clanWarLog.getCreatedDate() * 1000L);
                                        } else if (player.getDateFail2() == 0) {
                                            player.setDateFail2(clanWarLog.getCreatedDate() * 1000L);
                                        } else if (player.getDateFail3() == 0) {
                                            player.setDateFail3(clanWarLog.getCreatedDate() * 1000L);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            new PlayerRepository(getApplication()).insertAll(mPlayers);

            prefManager.setClanTag(clan.getTag());

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
            finish();
        }
    };

    private final Response.ErrorListener onClanError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }

            btnGetSearchClan.setEnabled(true);

            Snackbar.make(findViewById(R.id.btnGetSearchClan), getResources().getString(R.string.errorClan), Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorClan), Toast.LENGTH_LONG).show();
        }
    };

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

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
