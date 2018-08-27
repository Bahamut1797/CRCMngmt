package com.bohemiamates.crcmngmt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bohemiamates.crcmngmt.models.ClanBattle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class Api {
    private static final String URL = "https://api.royaleapi.com/";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUzMCwi" +
            "aWRlbiI6IjQ3MjA3MTUzMjUxMjkzNTk1NiIsIm1kIjp7fSwidHMiOjE1MzQ4NjM2OTY4Mzh9.G391OKyRmW" +
            "bOZ1mZRaxz--rN7yaM1J6NaAV-tDb-TQ4";
    private String responseJSON;
    public static final int CLAN = 1001;
    public static final int CLAN_BATTLE = 1002;
    public static final int CLAN_WAR = 1003;
    public static final int CLAN_WAR_LOG = 1004;

    private RequestQueue requestQueue;
    private Context context;

    public Api(final Context ctx) {
        this.context = ctx;
    }

    /**
     *
     * @param clanTAG
     * @param option
     * @return
     */
    public void getJSON(String clanTAG, int option) {
        switch (option) {
            case CLAN:
                fetchPosts("clan/" + clanTAG);
                break;
            case CLAN_BATTLE:
                fetchPosts("clan/" + clanTAG + "/battles?type=clanWarWarDay");
                break;
            case CLAN_WAR:
                fetchPosts("clan/" + clanTAG + "/war");
                break;
            case CLAN_WAR_LOG:
                fetchPosts("clan/" + clanTAG + "/warlog");
                break;
            default:
                break;
        }
    }

    private void fetchPosts(String endpoint) {
        requestQueue = Volley.newRequestQueue(this.context);

        /*StringRequest request = new StringRequest(Request.Method.GET, URL + endpoint,
                onPostsLoaded, onPostsError) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("Authorization", "Bearer " + API_KEY);
                return params;
            }
        };*/

        StringRequest request = new StringRequest(Request.Method.GET, URL + endpoint,
                onPostsLoaded, onPostsError) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("Authorization", "Bearer " + API_KEY);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy( 5000, 5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }


    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            //responseJSON = response;

            Intent i = new Intent(context, Main3Activity.class);
            i.putExtra("CLAN_BATTLE",  response);
            context.startActivity(i);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

}
