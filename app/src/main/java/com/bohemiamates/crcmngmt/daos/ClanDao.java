package com.bohemiamates.crcmngmt.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM clans")
    LiveData<List<Clan>> loadAllClans();

}
