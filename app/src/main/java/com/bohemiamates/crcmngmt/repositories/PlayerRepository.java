package com.bohemiamates.crcmngmt.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.bohemiamates.crcmngmt.daos.PlayerDao;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.other.AppDatabase;

import java.util.List;

public class PlayerRepository {
    private PlayerDao mPlayerDao;
    private LiveData<List<Player>> mAllPlayer;

    PlayerRepository(Application application, String clanTag) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
        mAllPlayer = mPlayerDao.loadAllPlayers(clanTag);
    }

    public LiveData<List<Player>> getPlayer() {
        return mAllPlayer;
    }

    public void insert(Player... players) {
        new InsertAsyncTask(mPlayerDao).execute(players);
    }

    public static class InsertAsyncTask extends AsyncTask<Player, Void, Void>{

        private PlayerDao mAsyncTaskDao;

        InsertAsyncTask(PlayerDao playerDao) {
            mAsyncTaskDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            mAsyncTaskDao.insertPlayers(players);
            return null;
        }
    }
}
