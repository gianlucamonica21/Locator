package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrintDAO;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrintDAO;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.AP.AP;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.AP.APDAO;

@Database(entities = {AP.class, WifiFingerPrint.class, MagneticFingerPrint.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    public abstract APDAO getAPDAO();
    public abstract WifiFingerPrintDAO getFingerPrintDAO();
    public abstract MagneticFingerPrintDAO getMagneticFingerPrintDAO();

}