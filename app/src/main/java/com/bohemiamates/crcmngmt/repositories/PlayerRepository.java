package com.bohemiamates.crcmngmt.repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.content.Context;
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

    public PlayerRepository(Context application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
    }

    public LiveData<List<Player>> getPlayers(String clanTag) {
        return mPlayerDao.loadAllPlayers(clanTag);
    }

    public List<Player> getPlayers2(String clanTag) {
        return mPlayerDao.getAllPlayers(clanTag);
    }

    public LiveData<List<Player>> getPlayersByFails(String clanTag) {
        return mPlayerDao.loadAllPlayersByFails(clanTag);
    }

    public LiveData<List<Player>> getPlayersByDonation(String clanTag) {
        return mPlayerDao.loadAllPlayersByDonation(clanTag);
    }

    public LiveData<Player> getPlayer(String tag) {
        return mPlayerDao.loadPlayer(tag);
    }

    public void insertAll(List<Player> players) {
        for (Player player :
                players) {
            new InsertAsyncTask(mPlayerDao).execute(player);
        }
    }

    public void insert(Player player) {
        new InsertAsyncTask(mPlayerDao).execute(player);

    }

    public void updateAll(List<Player> players) {
        for (Player player :
                players) {
            new UpdateAsyncTask(mPlayerDao).execute(player);
        }
    }

    public void update(Player player) {
        new UpdateAsyncTask(mPlayerDao).execute(player);
    }

    public void delete(Player player) {
        new DeleteAsyncTask(mPlayerDao).execute(player);
    }

    public static class InsertAsyncTask extends AsyncTask<Player, Void, Void> {

        private PlayerDao mAsyncTaskDao;

        InsertAsyncTask(PlayerDao playerDao) {
            mAsyncTaskDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            try {
                mAsyncTaskDao.insertPlayer(players[0]);
            } catch (SQLiteConstraintException e) {
                Log.e("SQLite_EXCEPTION", "Player " + players[0].getTag() + " already exist in DB");
            }
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Player, Void, Void> {

        private PlayerDao mAsyncTaskDao;

        UpdateAsyncTask(PlayerDao playerDao) {
            mAsyncTaskDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            try {
                mAsyncTaskDao.updatePlayer(players[0]);
            } catch (SQLiteConstraintException e) {
                Log.e("SQLite_EXCEPTION", e.getMessage());
            }
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Player, Void, Void> {

        private PlayerDao mAsyncTaskDao;

        DeleteAsyncTask(PlayerDao playerDao) {
            mAsyncTaskDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            try {
                mAsyncTaskDao.deletePlayer(players[0]);
            } catch (SQLiteConstraintException e) {
                Log.e("SQLite_EXCEPTION", e.getMessage());
            }
            return null;
        }
    }
}
