package com.bohemiamates.crcmngmt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bohemiamates.crcmngmt.models.Clan;
import com.bohemiamates.crcmngmt.models.ClanBattle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Collection;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Log.i("AAAAAAAAAAAA", getIntent().getStringExtra("CLAN_BATTLE"));

        Gson gson = new GsonBuilder().create();
        Clan clan = gson.fromJson(getIntent().getStringExtra("CLAN_BATTLE"), Clan.class);
        Log.w("*** AA CLAN INFO AA ***", clan.toString());
    }
}
