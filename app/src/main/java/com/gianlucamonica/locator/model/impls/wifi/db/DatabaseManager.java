package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.facebook.stetho.Stetho;

public class DatabaseManager {

    private AppDatabase appDatabase;

    public DatabaseManager(Activity activity){
        //setting db
        this.appDatabase = Room.databaseBuilder(activity, AppDatabase.class, "wifiAlg")
                .allowMainThreadQueries()//Allows room to do operation on main thread
                .fallbackToDestructiveMigration()
                .build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}
