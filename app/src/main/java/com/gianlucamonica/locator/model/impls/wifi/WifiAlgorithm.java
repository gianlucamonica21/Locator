package com.gianlucamonica.locator.model.impls.wifi;


import android.app.Activity;
import android.location.Location;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;

import com.gianlucamonica.locator.model.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.AP;
import com.gianlucamonica.locator.model.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.model.impls.wifi.online.WifiOnlineManager;
import com.gianlucamonica.locator.utils.MyApp;

import static android.content.Context.WIFI_SERVICE;

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
    public Location locate() {
        this.wifiOnlineManager = new WifiOnlineManager(activity);
        wifiOnlineManager.locate();
        return null;
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
