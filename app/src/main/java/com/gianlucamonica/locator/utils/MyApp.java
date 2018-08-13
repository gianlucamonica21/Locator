package com.gianlucamonica.locator.utils;

import android.app.Application;
import android.content.Context;

import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;


public class MyApp extends Application {

    private static MyApp instance;
    private static MyLocationManager myLocationManagerInstance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static MyLocationManager getMyLocationManagerInstance() {
        return myLocationManagerInstance;
    }

    public static void setMyLocationManagerInstance(MyLocationManager myLocationManagerInstance) {
        MyApp.myLocationManagerInstance = myLocationManagerInstance;
    }
}
