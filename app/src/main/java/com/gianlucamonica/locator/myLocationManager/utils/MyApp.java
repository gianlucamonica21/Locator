package com.gianlucamonica.locator.myLocationManager.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;


public class MyApp extends Application {

    private static MyApp instance;
    private static MyLocationManager myLocationManagerInstance;
    private static LocationMiddleware locationMiddlewareInstance;
    private static Activity activity;
    private static double magnitude;

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

    public static LocationMiddleware getLocationMiddlewareInstance() {
        return locationMiddlewareInstance;
    }

    public static void setLocationMiddlewareInstance(LocationMiddleware locationMiddlewareInstance) {
        MyApp.locationMiddlewareInstance = locationMiddlewareInstance;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        MyApp.activity = activity;
    }

    public static double getMagnitude() {
        return magnitude;
    }

    public static void setMagnitude(double magnitude) {
        MyApp.magnitude = magnitude;
    }
}
