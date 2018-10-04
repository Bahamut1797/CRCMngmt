package com.bohemiamates.crcmngmt.other;

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

    public PrefManager(Context context) {
        // shared pref mode
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstClanInit( boolean isFirstClanInit) {
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

}