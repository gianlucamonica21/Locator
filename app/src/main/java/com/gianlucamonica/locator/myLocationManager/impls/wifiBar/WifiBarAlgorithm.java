package com.gianlucamonica.locator.myLocationManager.impls.wifiBar;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.view.View;

import com.gianlucamonica.locator.myLocationManager.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.utils.WifiScanReceiver;
import com.gianlucamonica.locator.myLocationManager.impls.wifiBar.offline.WifiBarOfflineManager;
import com.gianlucamonica.locator.myLocationManager.impls.wifiBar.online.WifiBarOnlineManager;
import com.gianlucamonica.locator.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

public class WifiBarAlgorithm implements LocalizationAlgorithmInterface {

    private ArrayList<IndoorParams> indoorParams;
    private WifiManager wifiManager;
    private final WifiScanReceiver wifiScanReceiver;

    private WifiBarOfflineManager wifiBarOfflineManager;
    private WifiBarOnlineManager wifiBarOnlineManager;

    public WifiBarAlgorithm(ArrayList<IndoorParams> indoorParams) {
        this.indoorParams = indoorParams;
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        // faccio partire lo scan
        wifiManager.startScan();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        wifiScanReceiver = new WifiScanReceiver();
        MyApp.getContext().registerReceiver(wifiScanReceiver, intentFilter);
    }

    @Override
    public <T extends View> T build(Class<T> type) {
        this.wifiBarOfflineManager = new WifiBarOfflineManager(indoorParams);
        return wifiBarOfflineManager.build(type);
    }

    @Override
    public OnlineScan locate() {
        this.wifiBarOnlineManager = new WifiBarOnlineManager(indoorParams);
        return wifiBarOnlineManager.locate();
    }

    @Override
    public Object getBuildClass() {
        return null;
    }

    @Override
    public void checkPermissions() {

    }
}
