package com.bohemiamates.crcmngmt.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import com.bohemiamates.crcmngmt.daos.ClanDao;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.other.AppDatabase;

public class ClanRepository {
    private ClanDao mClanDao;

    public ClanRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mClanDao = db.clanDao();
    }

    public LiveData<Clan> getClan(String clanTag) {
        return mClanDao.loadClan(clanTag);
    }

    public void insert(Clan clan) {
        new InsertAsyncTask(mClanDao).execute(clan);
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
}
