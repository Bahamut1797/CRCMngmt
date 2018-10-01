package com.bohemiamates.crcmngmt.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bohemiamates.crcmngmt.R;
import com.bohemiamates.crcmngmt.models.Clan;
import com.bohemiamates.crcmngmt.repositories.ClanRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    public TextView txtClanTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnGetClan = findViewById(R.id.btnGetClan);
        txtClanTag = findViewById(R.id.txtClanTag);

        btnGetClan.setOnClickListener(new btnOnClickListener());
    }

    private class btnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final String URL = getString(R.string.url) + "clan/" + txtClanTag.getText();
            RequestQueue requestQueue;

            requestQueue = Volley.newRequestQueue(getApplicationContext());


            StringRequest request = new StringRequest(Request.Method.GET, URL,
                    onPostsLoaded, onPostsError) {
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

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);

            Gson gson = new GsonBuilder().create();
            Clan clan = gson.fromJson(response, Clan.class);

            //Log.i("CLAAAAAAAAAAAN", clan.toString());

            com.bohemiamates.crcmngmt.entities.Clan mClan = new com.bohemiamates.crcmngmt.entities.Clan(clan);

            new ClanRepository(getApplication()).insert(mClan);

            Log.i("CLAAAAAAAAAAAN", new ClanRepository(getApplication(), txtClanTag.getText().toString()).getClan().toString());
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };
}
