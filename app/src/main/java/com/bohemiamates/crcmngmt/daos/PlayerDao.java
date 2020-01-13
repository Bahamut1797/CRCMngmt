package com.bohemiamates.crcmngmt.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM players WHERE clanTag = :clanTag ORDER BY rank ASC")
    LiveData<List<Player>> loadAllPlayers(String clanTag);

    @Query("SELECT * FROM players WHERE clanTag = :clanTag ORDER BY rank ASC")
    List<Player> getAllPlayers(String clanTag);

    @Query("SELECT * FROM players WHERE clanTag = :clanTag ORDER BY clanFails DESC, rank ASC")
    LiveData<List<Player>> loadAllPlayersByFails(String clanTag);

    @Query("SELECT * FROM players WHERE clanTag = :clanTag ORDER BY donations DESC, rank ASC")
    LiveData<List<Player>> loadAllPlayersByDonation(String clanTag);

    @Query("SELECT * FROM players WHERE tag = :tag")
    LiveData<Player> loadPlayer(String tag);
}
