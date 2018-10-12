package com.bohemiamates.crcmngmt.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import com.bohemiamates.crcmngmt.daos.PlayerDao;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.other.AppDatabase;

import java.util.List;

public class PlayerRepository {
    private PlayerDao mPlayerDao;

    public PlayerRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
    }

    public LiveData<List<Player>> getPlayers(String clanTag) {
        return mPlayerDao.loadAllPlayers(clanTag);
    }

    public void insertAll(List<Player> players) {
        for (Player player:
             players) {
            new InsertAsyncTask(mPlayerDao).execute(player);
        }
    }


    public static class InsertAsyncTask extends AsyncTask<Player, Void, Void>{

        private PlayerDao mAsyncTaskDao;

        InsertAsyncTask(PlayerDao playerDao) {
            mAsyncTaskDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            try {
                mAsyncTaskDao.insertPlayers(players[0]);
            } catch (SQLiteConstraintException e) {
                Log.e("SQLite_EXCEPTION", "Player " + players[0].getTag() + " already exist in DB");
            }
            return null;
        }
    }
}
