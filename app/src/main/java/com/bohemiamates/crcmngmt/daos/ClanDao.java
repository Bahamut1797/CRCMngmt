package com.bohemiamates.crcmngmt.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bohemiamates.crcmngmt.entities.Clan;

import java.util.List;

@Dao
public interface ClanDao {
    @Insert
    void insertClan(Clan clan);

    @Update
    void updateClan(Clan clan);

    @Query("DELETE FROM clans WHERE tag = :tag")
    void deleteClan(String tag);

    @Query("SELECT * FROM clans WHERE tag = :tag")
    LiveData<Clan> loadClan(String tag);

    @Query("SELECT * FROM clans WHERE tag = :tag")
    Clan getClan(String tag);

    @Query("SELECT * FROM clans")
    LiveData<List<Clan>> loadAllClans();

}
