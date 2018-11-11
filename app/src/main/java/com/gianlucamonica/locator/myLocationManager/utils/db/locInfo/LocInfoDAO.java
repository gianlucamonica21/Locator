package com.gianlucamonica.locator.myLocationManager.utils.db.locInfo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;

import java.util.List;

@Dao
public abstract class LocInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LocInfo... locInfos);

    @Update
    public void update(LocInfo... locInfos){};

    @Delete
    public void delete(LocInfo... locInfos){};

    @Query("DELETE FROM locInfo")
    public void deleteAll(){};

    @Query("SELECT indoorLoc FROM locInfo")
    public abstract boolean getLocInfo();

}
