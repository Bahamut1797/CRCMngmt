package com.bohemiamates.crcmngmt.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.bohemiamates.crcmngmt.daos.ClanDao;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.other.AppDatabase;

public class ClanRepository {
    private ClanDao mClanDao;
    private Clan mClan;

    ClanRepository(Application application, String clanTag) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mClanDao = db.clanDao();
        mClan = mClanDao.loadClan(clanTag);
    }

    public Clan getClan() {
        return mClan;
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
            mAsyncTaskDao.insertClan(clans[0]);
            return null;
        }
    }
}
