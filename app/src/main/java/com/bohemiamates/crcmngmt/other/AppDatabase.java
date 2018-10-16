package com.bohemiamates.crcmngmt.other;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bohemiamates.crcmngmt.daos.ClanDao;
import com.bohemiamates.crcmngmt.daos.PlayerDao;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.entities.Player;

@Database(entities = {Clan.class, Player.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClanDao clanDao();
    public abstract PlayerDao playerDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "crcmngt_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

