package com.gianlucamonica.locator.myLocationManager.impls.wifi;


import android.app.Activity;
import android.view.View;

import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locator.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.online.WifiOnlineManager;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;

import java.util.ArrayList;

public class WifiAlgorithm implements LocalizationAlgorithmInterface {

    private WifiOfflineManager wifiOfflineManager;
    private WifiOnlineManager wifiOnlineManager;
    private Activity activity;

    private ArrayList<IndoorParams> indoorParams;

    public WifiAlgorithm(Activity activity, ArrayList<IndoorParams> indoorParams){
        this.activity = activity;
        this.indoorParams = indoorParams;
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return new WifiOfflineManager(this.activity, indoorParams);
    }

    @Override
    public <T extends View> T build(Class<T> type) {
        this.wifiOfflineManager = new WifiOfflineManager(this.activity, indoorParams);
        MyApp.setWifiOfflineManagerInstance(wifiOfflineManager);
        return wifiOfflineManager.build(type);
    }

    @Override
    public WifiFingerPrint locate() {
        this.wifiOnlineManager = new WifiOnlineManager(activity);
        return  wifiOnlineManager.locate();
    }

    @Override
    public void checkPermissions() {
    }

    /*@Override
    public boolean canGetLocation() {
        return false;
    }

    @Override
    public boolean isProviderEnabled() {
        return false;
    }

    @Override
    public double getLongitude() {
        return 0;
    }

    @Override
    public double getLatitude() {
        return 0;
    }*/

}
