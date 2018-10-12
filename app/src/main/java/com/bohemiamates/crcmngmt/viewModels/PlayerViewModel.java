package com.bohemiamates.crcmngmt.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.repositories.PlayerRepository;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository mPlayerRepository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        mPlayerRepository = new PlayerRepository(application);
    }

    public LiveData<List<Player>> getAllPlayers(String clanTag) {
        return mPlayerRepository.getPlayers(clanTag);
    }

    //public void insert(Player player) { mPlayerRepository.insertAll(player); }
}
