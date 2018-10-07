package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.AlgorithmDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.BuildingDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScanDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScanDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummaryDAO;

@Database(entities = {Building.class,Algorithm.class,OfflineScan.class, OnlineScan.class, ScanSummary.class}, version = 17)
public abstract class AppDatabase extends RoomDatabase {

    /*public abstract APDAO getAPDAO();
    public abstract WifiFingerPrintDAO getFingerPrintDAO();
    public abstract MagneticFingerPrintDAO getMagneticFingerPrintDAO();*/

    public abstract BuildingDAO getBuildingDAO();
    public abstract AlgorithmDAO getAlgorithmDAO();
    public abstract OfflineScanDAO getOfflineScanDAO();
    public abstract OnlineScanDAO getOnlineScanDAO();
    public abstract ScanSummaryDAO getScanSummaryDAO();


}