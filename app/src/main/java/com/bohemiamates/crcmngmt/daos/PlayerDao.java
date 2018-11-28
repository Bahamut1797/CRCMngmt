package com.bohemiamates.crcmngmt.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bohemiamates.crcmngmt.entities.Player;

import java.util.List;

@Dao
public interface PlayerDao {
    @Insert
    void insertPlayer(Player player);

    @Update
    void updatePlayer(Player player);

    @Delete
    void deletePlayer(Player player);

    @Query("SELECT * FROM players WHERE clanTag = :clanTag ORDER BY clanFails DESC, rank ASC")
    LiveData<List<Player>> loadAllPlayers(String clanTag);

    @Query("SELECT * FROM players WHERE tag = :tag")
    LiveData<Player> loadPlayer(String tag);
}
