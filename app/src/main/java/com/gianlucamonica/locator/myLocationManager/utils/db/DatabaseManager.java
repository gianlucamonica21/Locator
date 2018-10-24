package com.gianlucamonica.locator.myLocationManager.utils.db;

import android.arch.persistence.room.Room;

import com.gianlucamonica.locator.myLocationManager.utils.MyApp;


public class DatabaseManager {

    private AppDatabase appDatabase;

    public DatabaseManager(){
        //setting db
        this.appDatabase = Room.databaseBuilder(MyApp.getContext(), AppDatabase.class, "algsDB")
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
