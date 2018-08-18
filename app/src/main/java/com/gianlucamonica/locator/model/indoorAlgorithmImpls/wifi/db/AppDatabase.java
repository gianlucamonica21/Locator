package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db.AP.AP;
import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db.AP.APDAO;
import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db.fingerPrint.FingerPrint;
import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db.fingerPrint.FingerPrintDAO;

@Database(entities = {AP.class, FingerPrint.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract APDAO getAPDAO();
    public abstract FingerPrintDAO getFingerPrintDAO();

}