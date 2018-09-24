package com.gianlucamonica.locator.myLocationManager.impls.wifi;


import android.app.Activity;
import android.view.View;

import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locator.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.online.WifiOnlineManager;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;

public class WifiAlgorithm implements LocalizationAlgorithmInterface {

    private WifiOfflineManager wifiOfflineManager;
    private WifiOnlineManager wifiOnlineManager;
    private Activity activity;

    public WifiAlgorithm(){
    }

    public WifiAlgorithm(Activity activity){
        this.activity = activity;
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return new WifiOfflineManager(this.activity);
    }

    @Override
    public <T extends View> T build(Class<T> type) {
        this.wifiOfflineManager = new WifiOfflineManager(this.activity);
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
