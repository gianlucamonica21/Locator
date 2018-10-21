package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;

import java.util.List;

@Dao
public interface MyDao {
    @Query("SELECT config.* FROM config "
            + "INNER JOIN scanSummary ON scanSummary.idConfig = config.id "
            + "WHERE scanSummary.idBuilding = :idBuilding AND scanSummary.idAlgorithm = :idAlgorithm")
    public List<Config> findConfigByBuildingAndAlgorithm(int idBuilding, int idAlgorithm);
}
