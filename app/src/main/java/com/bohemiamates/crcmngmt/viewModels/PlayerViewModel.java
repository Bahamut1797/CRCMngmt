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

    public LiveData<List<Player>> getAllPlayersByFails(String clanTag) {
        return mPlayerRepository.getPlayersByFails(clanTag);
    }

    public LiveData<List<Player>> getAllPlayersByDonation(String clanTag) {
        return mPlayerRepository.getPlayersByDonation(clanTag);
    }

    public LiveData<Player> getPlayer(String tag) {
        return mPlayerRepository.getPlayer(tag);
    }

    public void updateAll(List<Player> player) {
        mPlayerRepository.updateAll(player);
    }

    public void update(Player player) {
        mPlayerRepository.update(player);
    }

    public void insert(Player player) {
        mPlayerRepository.insert(player);
    }

    public void delete(Player player) {
        mPlayerRepository.delete(player);
    }
}
