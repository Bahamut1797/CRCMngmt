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
    void insertPlayers(Player... player);

    @Update
    void updatePlayers(Player... player);

    @Delete
    void deletePlayers(Player... player);

    @Query("SELECT * FROM players WHERE clanTag = :clanTag")
    LiveData<List<Player>> loadAllPlayers(String clanTag);

    @Query("SELECT * FROM players WHERE clanTag = :clanTag")
    List<Player> loadPlayers(String clanTag);

    @Query("SELECT * FROM players WHERE tag = :tag")
    Player loadPlayer(String tag);
}
