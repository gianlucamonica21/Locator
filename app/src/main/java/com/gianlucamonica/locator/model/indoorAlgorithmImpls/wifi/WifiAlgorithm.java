package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi;


import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.gianlucamonica.locator.activities.wifi.mapBuilder.WIFIMapBuilder;
import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;

public class WifiAlgorithm implements LocalizationAlgorithmInterface {

    public WifiAlgorithm(){
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return new WIFIMapBuilder(activity);
    }

    @Override
    public void build() {

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
