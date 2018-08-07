package com.gianlucamonica.locator.model.myLocationManager;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.WifiAlgorithm;
import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.model.outdoorLocationManager.OutdoorLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;

public class MyLocationManager implements LocalizationAlgorithmInterface {

    private AlgorithmName algoName;
    private LocalizationAlgorithmInterface localizationAlgorithmInterface;

    /**
     * @param algoName
     */
    public MyLocationManager(AlgorithmName algoName) {

        switch (algoName) {
            case GPS:
                this.algoName = algoName;
                if(MyApp.getContext() == null)
                    Log.i("Context: ", "null");
                localizationAlgorithmInterface = new OutdoorLocationManager(MyApp.getContext());
                break;
            case WIFI:
                this.algoName = algoName;
                localizationAlgorithmInterface = new WifiAlgorithm();
                break;
            default:
        }
    }

    /**
     *
     * @param algoName
     * @param activity
     */
    public MyLocationManager(AlgorithmName algoName, Activity activity) {

        switch (algoName) {
            case GPS:
                this.algoName = algoName;
                if(MyApp.getContext() == null)
                    Log.i("Context: ", "null");
                localizationAlgorithmInterface = new OutdoorLocationManager(MyApp.getContext());
                break;
            case WIFI:
                this.algoName = algoName;
                localizationAlgorithmInterface = new WifiAlgorithm(activity);
                break;
            default:
        }
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return localizationAlgorithmInterface.getBuildClass(activity);
    }

    @Override
    public void build() {
        localizationAlgorithmInterface.build();
    }

    public Location locate() {
        Location l;
        switch (this.algoName) {
            case GPS:
                l = localizationAlgorithmInterface.locate();
                break;
            default:
                l = localizationAlgorithmInterface.locate();
        }
        return null;
    }

    @Override
    public boolean canGetLocation() {
        return false;
    }

    public boolean isProviderEnabled(){
        return localizationAlgorithmInterface.isProviderEnabled();
    }

}
