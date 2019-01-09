package com.bohemiamates.crcmngmt.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import com.bohemiamates.crcmngmt.daos.ClanDao;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.other.AppDatabase;

import java.util.List;

public class ClanRepository {
    private ClanDao mClanDao;

    public ClanRepository(Context application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mClanDao = db.clanDao();
    }

    public LiveData<Clan> getClan(String clanTag) {
        return mClanDao.loadClan(clanTag);
    }

    public LiveData<List<Clan>> getAllClans() {
        return mClanDao.loadAllClans();
    }

    public void insert(Clan clan) {
        new InsertAsyncTask(mClanDao).execute(clan);
    }

    public void update(Clan clan) {
        new UpdateAsyncTask(mClanDao).execute(clan);
    }

    public void delete(String clan) {
        new DeleteAsyncTask(mClanDao).execute(clan);
    }

    private static class InsertAsyncTask extends AsyncTask<Clan, Void, Void> {

        private ClanDao mAsyncTaskDao;

        InsertAsyncTask(ClanDao clanDao) {
            mAsyncTaskDao = clanDao;
        }

        @Override
        protected Void doInBackground(Clan... clans) {
            try {
                mAsyncTaskDao.insertClan(clans[0]);
            } catch (SQLiteConstraintException e) {
                Log.e("SQLite_EXCEPTION", "Clan " + clans[0].getTag() + " already exist in DB");
            }
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Clan, Void, Void> {

        private ClanDao mAsyncTaskDao;

        UpdateAsyncTask(ClanDao clanDao) {
            mAsyncTaskDao = clanDao;
        }

        @Override
        protected Void doInBackground(Clan... clans) {
            try {
                mAsyncTaskDao.updateClan(clans[0]);
            } catch (SQLiteConstraintException e) {
                Log.e("SQLite_EXCEPTION", e.getMessage());
            }
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {

        private ClanDao mAsyncTaskDao;

        DeleteAsyncTask(ClanDao clanDao) {
            mAsyncTaskDao = clanDao;
        }

        @Override
        protected Void doInBackground(String... clans) {
            try {
                mAsyncTaskDao.deleteClan(clans[0]);
            } catch (SQLiteConstraintException e) {
                Log.e("SQLite_EXCEPTION", e.getMessage());
            }
            return null;
        }
    }
}
