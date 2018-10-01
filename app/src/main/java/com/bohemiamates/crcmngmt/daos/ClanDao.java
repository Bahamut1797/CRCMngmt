package com.bohemiamates.crcmngmt.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bohemiamates.crcmngmt.entities.Clan;

@Dao
public interface ClanDao {
    @Insert
    void insertClan(Clan clan);

    @Update
    void updateClan(Clan clan);

    @Delete
    void deleteClan(Clan clan);

    @Query("SELECT * FROM clans WHERE tag = :tag")
    Clan loadClan(String tag);

}
