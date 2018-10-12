package com.bohemiamates.crcmngmt;

import android.content.Context;

import com.android.volley.RequestQueue;

public class Api {
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
    /*public void getJSON(String clanTAG, int option) {
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

        StringRequest request = new StringRequest(Request.Method.GET, URL + endpoint,
                onPostsLoaded, onPostsError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("Authorization", "Bearer " + API_KEY);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy( 5000, 2,
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
*/
}
