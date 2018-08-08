package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi;


import android.app.Activity;
import android.location.Location;
import android.view.View;

import com.gianlucamonica.locator.activities.wifi.mapBuilder.MapView;
import com.gianlucamonica.locator.activities.wifi.mapBuilder.WIFIMapBuilder;
import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;

public class WifiAlgorithm implements LocalizationAlgorithmInterface {

    private WIFIMapBuilder wifiMapBuilder;
    private Activity activity;

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
    public boolean canGetLocation() {
        return false;
    }

    @Override
    public boolean isProviderEnabled() {
        return false;
    }
}
