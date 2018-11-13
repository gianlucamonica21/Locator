package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;

import java.util.List;

@Dao
public interface MyDao {
    @Query("SELECT config.* FROM config "
            + "INNER JOIN scanSummary ON scanSummary.idConfig = config.id "
            + "WHERE scanSummary.idBuilding = :idBuilding AND scanSummary.idAlgorithm = :idAlgorithm " +
            "AND scanSummary.idBuildingFloor = :idBuildingFloor " +
            "AND scanSummary.type = :type")
    public List<Config> findConfigByBuildingAndAlgorithm(int idBuilding, int idAlgorithm, int idBuildingFloor, String type);

    @Query("SELECT offlineScan.* FROM offlineScan "
            + "INNER JOIN scanSummary ON scanSummary.id = offlineScan.idScan "
            + "WHERE scanSummary.idBuilding = :idBuilding AND scanSummary.idBuildingFloor = :idFloor " +
            "AND scanSummary.idAlgorithm = :idAlgorithm AND scanSummary.idConfig = :idConfig AND scanSummary.type = :type")
    public List<OfflineScan> getOfflineScan(int idBuilding, int idFloor, int idAlgorithm, int idConfig, String type);

    @Query("SELECT offlineScan.* FROM offlineScan "
            + "INNER JOIN scanSummary ON scanSummary.id = offlineScan.idScan "
            + "INNER JOIN buildingFloor ON scanSummary.idBuildingFloor = buildingFloor.id "
            + "WHERE scanSummary.idBuilding = :idBuilding AND buildingFloor.name = :idFloor " +
            "AND scanSummary.idAlgorithm = :idAlgorithm AND scanSummary.idConfig = :idConfig AND scanSummary.type = :type")
    public List<OfflineScan> getOfflineScanWifiBar(int idBuilding, int idFloor, int idAlgorithm, int idConfig, String type);



    @Query("SELECT offlineScan.* FROM offlineScan "
            + "INNER JOIN scanSummary ON scanSummary.id = offlineScan.idScan "
            + "WHERE scanSummary.idBuilding = :idBuilding AND scanSummary.idAlgorithm = :idAlgorithm AND "
            + "scanSummary.idBuildingFloor = :idFloor AND scanSummary.idConfig = :idConfig")
    public List<OfflineScan> getOfflineScan(int idBuilding, int idAlgorithm, int idFloor, int idConfig);

}
