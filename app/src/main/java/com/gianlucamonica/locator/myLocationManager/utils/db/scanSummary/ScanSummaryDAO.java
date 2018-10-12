package com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;

import java.util.List;

@Dao
public abstract class ScanSummaryDAO {

    @Insert
    public abstract void insert(ScanSummary... scanSummaries);

    @Update
    public void update(ScanSummary... scanSummaries){};

    @Delete
    public void delete(ScanSummary... scanSummaries){};

    @Query("DELETE FROM scanSummary")
    public void deleteAll(){};

    @Query("DELETE FROM scanSummary WHERE idBuilding = :idBuilding AND idAlgorithm = :idAlgorithm AND gridSize = :gridSize")
    public void deleteByBuildingAlgorithmSize(int idBuilding, int idAlgorithm, int gridSize){};

    @Query("SELECT * FROM scanSummary")
    public abstract List<ScanSummary> getScanSummary();

    @Query("SELECT * FROM scanSummary WHERE idBuilding = :idBuilding AND idAlgorithm = :idAlgorithm AND gridSize = :gridSize")
    public abstract List<ScanSummary>  getScanSummaryByBuildingAlgorithm(int idBuilding, int idAlgorithm, int gridSize);

}
