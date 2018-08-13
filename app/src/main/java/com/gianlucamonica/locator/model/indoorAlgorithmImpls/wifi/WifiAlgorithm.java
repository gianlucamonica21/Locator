package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi;


import android.app.Activity;
import android.location.Location;
import android.view.View;

import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.mapBuilder.WIFIMapBuilder;
import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;

import java.io.Serializable;

public class WifiAlgorithm implements Serializable, LocalizationAlgorithmInterface {

    private transient WIFIMapBuilder wifiMapBuilder;
    private transient Activity activity;

    public WifiAlgorithm(){
    }

    public WifiAlgorithm(Activity activity){
        this.activity = activity;
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return new WIFIMapBuilder(this.activity);
    }

    @Override
    public <T extends View> T build(Class<T> type) {
        this.wifiMapBuilder = new WIFIMapBuilder(this.activity);
        return wifiMapBuilder.build(type);
    }

    @Override
    public Location locate() {
        return null;
    }

    @Override
    public void checkPermissions() {
    }

    @Override
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
    }
}
