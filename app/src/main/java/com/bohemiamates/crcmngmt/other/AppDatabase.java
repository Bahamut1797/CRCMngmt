package com.bohemiamates.crcmngmt.other;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.NonNull;

import com.bohemiamates.crcmngmt.daos.ClanDao;
import com.bohemiamates.crcmngmt.daos.PlayerDao;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.entities.Player;

@Database(entities = {Clan.class, Player.class}, exportSchema = false, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClanDao clanDao();
    public abstract PlayerDao playerDao();

    private static AppDatabase INSTANCE;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2){

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE players "
                    + " ADD COLUMN battleLog VARCHAR");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3){

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE players "
                    + " ADD COLUMN arena_id INTEGER");

            database.execSQL("ALTER TABLE players "
                    + " ADD COLUMN arena_name VARCHAR");
        }
    };

    /*private static final Migration MIGRATION_3_4 = new Migration(2, 3){

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE players "
                    + " ADD COLUMN clanCardsCollected INTEGER");

            database.execSQL("ALTER TABLE players "
                    + " ADD COLUMN warDayWins INTEGER");
        }
    };*/

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "crcmngt_db")
                            .addMigrations(MIGRATION_1_2)
                            .addMigrations(MIGRATION_2_3)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

