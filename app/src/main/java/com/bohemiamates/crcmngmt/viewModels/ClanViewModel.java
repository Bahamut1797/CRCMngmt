package com.bohemiamates.crcmngmt.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.repositories.ClanRepository;

import java.util.List;

public class ClanViewModel extends AndroidViewModel {

    private ClanRepository mClanRepository;

    public ClanViewModel(@NonNull Application application) {
        super(application);
        mClanRepository = new ClanRepository(application);
    }

    public LiveData<Clan> getClan(String clanTag) {
        return mClanRepository.getClan(clanTag);
    }

    public LiveData<List<Clan>> getAllClans() {
        return mClanRepository.getAllClans();
    }

    public void insert(Clan clan) {
        mClanRepository.insert(clan);
    }

    public void update(Clan clan) {
        mClanRepository.update(clan);
    }
}
