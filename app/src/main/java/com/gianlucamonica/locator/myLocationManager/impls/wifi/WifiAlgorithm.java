package com.gianlucamonica.locator.myLocationManager.impls.wifi;


import android.view.View;

import com.gianlucamonica.locator.myLocationManager.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locator.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.online.WifiOnlineManager;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;

import java.util.ArrayList;

public class WifiAlgorithm implements LocalizationAlgorithmInterface {

    private WifiOfflineManager wifiOfflineManager;
    private WifiOnlineManager wifiOnlineManager;

    private ArrayList<IndoorParams> indoorParams;

    public WifiAlgorithm(ArrayList<IndoorParams> indoorParams){
        this.indoorParams = indoorParams;
    }

    @Override
    public Object getBuildClass() {
        return new WifiOfflineManager(indoorParams);
    }

    @Override
    public <T extends View> T build(Class<T> type) {
        this.wifiOfflineManager = new WifiOfflineManager(indoorParams);
        MyApp.setWifiOfflineManagerInstance(wifiOfflineManager);
        return wifiOfflineManager.build(type);
    }

    @Override
    public OnlineScan locate() {
        this.wifiOnlineManager = new WifiOnlineManager(indoorParams);
        return  wifiOnlineManager.locate();
    }

    @Override
    public void checkPermissions() {
    }
    

}
