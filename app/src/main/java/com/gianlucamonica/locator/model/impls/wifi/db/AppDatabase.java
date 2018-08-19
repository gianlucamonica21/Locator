package com.gianlucamonica.locator.model.impls.wifi.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.FingerPrint;
import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.FingerPrintDAO;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.AP;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.APDAO;

@Database(entities = {AP.class, FingerPrint.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract APDAO getAPDAO();
    public abstract FingerPrintDAO getFingerPrintDAO();

}