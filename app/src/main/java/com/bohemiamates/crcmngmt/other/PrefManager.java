package com.bohemiamates.crcmngmt.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // Shared preferences file name
    private static final String PREF_NAME = "bohemiamates-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_TIME_CLAN_INIT = "IsFirstTimeClanInit";
    private static final String CLAN_TAG = "ClanTag";
    private static final String CLAN_WAR_TAG = "ClanWar";
    private static final String CURRENT_ORDER_BY = "CurrentOrderBy";

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        // shared pref mode
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstClanInit(boolean isFirstClanInit) {
        editor.putBoolean(IS_FIRST_TIME_CLAN_INIT, isFirstClanInit);
        editor.commit();
    }

    public boolean isFirstClanInit() {
        return pref.getBoolean(IS_FIRST_TIME_CLAN_INIT, true);
    }

    public void setClanTag(String clanTag) {
        editor.putString(CLAN_TAG, clanTag);
        editor.commit();
    }

    public String getClanTag() {
        return pref.getString(CLAN_TAG, "NO EXIST");
    }

    public void setClanWarTime(long time) {
        editor.putLong(CLAN_WAR_TAG, time);
        editor.commit();
    }

    public long getClanWarTime() {
        return pref.getLong(CLAN_WAR_TAG, 0);
    }

    public void setCurrentOrderBy(int option) {
        editor.putInt(CURRENT_ORDER_BY, option);
        editor.commit();
    }

    public int getCurrentOrderBy() {
        return pref.getInt(CURRENT_ORDER_BY, 1);
    }
}