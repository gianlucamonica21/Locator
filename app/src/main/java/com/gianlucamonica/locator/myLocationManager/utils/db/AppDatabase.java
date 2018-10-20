package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.ConfigDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.AlgorithmDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.BuildingDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloorDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScanDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScanDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummaryDAO;

@Database(entities = {
        Building.class,
        BuildingFloor.class,
        Algorithm.class,
        Config.class,
        OfflineScan.class,
        OnlineScan.class,
        ScanSummary.class
        }, version = 36)
public abstract class AppDatabase extends RoomDatabase {

    /*public abstract APDAO getAPDAO();
    public abstract WifiFingerPrintDAO getFingerPrintDAO();
    public abstract MagneticFingerPrintDAO getMagneticFingerPrintDAO();*/

    public abstract BuildingDAO getBuildingDAO();
    public abstract AlgorithmDAO getAlgorithmDAO();
    public abstract OfflineScanDAO getOfflineScanDAO();
    public abstract OnlineScanDAO getOnlineScanDAO();
    public abstract ScanSummaryDAO getScanSummaryDAO();
    public abstract BuildingFloorDAO getBuildingFloorDAO();
    public abstract ConfigDAO getConfigDAO();
    public abstract MyDao getMyDAO();


}