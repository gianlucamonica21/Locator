package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrintDAO;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrintDAO;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.AP.AP;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.AP.APDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.AlgorithmDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.BuildingDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScanDAO;

@Database(entities = {Building.class,Algorithm.class,OfflineScan.class}, version = 13)
public abstract class AppDatabase extends RoomDatabase {

    /*public abstract APDAO getAPDAO();
    public abstract WifiFingerPrintDAO getFingerPrintDAO();
    public abstract MagneticFingerPrintDAO getMagneticFingerPrintDAO();*/

    public abstract BuildingDAO getBuildingDAO();
    public abstract AlgorithmDAO getAlgorithmDAO();
    public abstract OfflineScanDAO getOfflineScanDAO();


}