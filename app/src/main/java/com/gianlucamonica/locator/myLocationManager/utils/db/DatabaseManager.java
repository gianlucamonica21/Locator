package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.app.Activity;
import android.arch.persistence.room.Room;


public class DatabaseManager {

    private AppDatabase appDatabase;

    public DatabaseManager(Activity activity){
        //setting db
        this.appDatabase = Room.databaseBuilder(activity, AppDatabase.class, "algsDB")
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
