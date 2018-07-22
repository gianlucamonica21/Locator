package com.gianlucamonica.locator.model.myLocationManager;

import android.content.Context;
import android.location.Location;

import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.WifiAlgorithm;
import com.gianlucamonica.locator.model.indoorAlgorithmInterface.IndoorAlgorithmInterface;
import com.gianlucamonica.locator.model.outdoorLocationManager.OutdoorLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;

public class MyLocationManager {

    private final Context context;
    private AlgorithmName algoName;
    private OutdoorLocationManager outdoorLocationManager; // manager for GPS localization
    private IndoorAlgorithmInterface indoorLocationManager;
    private Object manager;

    /**
     * @param algoName
     * @param context
     */
    public MyLocationManager(AlgorithmName algoName, Context context) {

        this.context = context;
        switch (algoName) {
            case GPS:
                this.algoName = algoName;
                outdoorLocationManager = new OutdoorLocationManager(this.context);
                break;
            case WIFI:
                this.algoName = algoName;
                indoorLocationManager = new WifiAlgorithm();
                break;
            default:
        }
    }

    public Location locate() {
        Location l;
        switch (this.algoName) {
            case GPS:
                l = outdoorLocationManager.locate();
                break;
            default:
                l = indoorLocationManager.locate();
        }
        return null;
    }

}
