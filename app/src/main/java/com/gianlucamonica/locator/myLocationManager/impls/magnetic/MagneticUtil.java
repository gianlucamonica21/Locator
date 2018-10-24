package com.gianlucamonica.locator.myLocationManager.impls.magnetic;

import com.gianlucamonica.locator.myLocationManager.utils.MyApp;

public class MagneticUtil extends MyApp {

    private double latestMagnitude;

    public double getLatestMagnitude() {
        return latestMagnitude;
    }

    public void setLatestMagnitude(double latestMagnitude) {
        this.latestMagnitude = latestMagnitude;
    }
}
