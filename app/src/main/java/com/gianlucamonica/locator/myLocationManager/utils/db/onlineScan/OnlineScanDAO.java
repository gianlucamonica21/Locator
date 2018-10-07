package com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class OnlineScanDAO {

    @Insert
    public abstract void insert(OnlineScan... onlineScans);

    @Update
    public void update(OnlineScan... onlineScans){};

    @Delete
    public void delete(OnlineScan... onlineScans){};

    @Query("DELETE FROM onlineScan")
    public void deleteAll(){};

    @Query("SELECT * FROM onlineScan")
    public abstract List<OnlineScan> getOnlineScans();

    @Query("SELECT * FROM onlineScan WHERE idBuilding = :idBuilding AND idAlgorithm = :idAlgorithm")
    public abstract List<OnlineScan>  getOnlineScansByBuildingAlgorithm(int idBuilding, int idAlgorithm);

}
