package com.gianlucamonica.locator.model.myLocationManager;

import android.Manifest;
import android.app.Activity;
import android.location.Location;
import android.view.View;

import com.gianlucamonica.locator.model.impls.wifi.WifiAlgorithm;
import com.gianlucamonica.locator.model.LocAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.model.impls.gps.OutdoorLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.permissionsManager.MyPermissionsManager;

import java.util.Arrays;

public class MyLocationManager implements LocalizationAlgorithmInterface {

    private AlgorithmName algoName;
    private LocalizationAlgorithmInterface localizationAlgorithmInterface;
    private MyPermissionsManager myPermissionsManager;
    private String[] permissions;

    /*
     * @param algoName
     * @param activity
     */
    public MyLocationManager(AlgorithmName algoName, Activity activity) {

        myPermissionsManager = new MyPermissionsManager(activity);

        switch (algoName) {
            case GPS:
                this.algoName = algoName;
                permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
                checkPermissions();

                if ( myPermissionsManager.isCheckGPS())
                    localizationAlgorithmInterface = new OutdoorLocationManager(MyApp.getContext());
                break;
            case WIFI:
                this.algoName = algoName;
                permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                checkPermissions();

                if (myPermissionsManager.isCheckWIFI())
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
    public  <T extends View> T build(Class<T> type) {
        return localizationAlgorithmInterface.build(type);
    }

    public Location locate() {
        return localizationAlgorithmInterface.locate();
    }

    @Override
    public void checkPermissions() {
        myPermissionsManager.requestPermission(permissions);
        myPermissionsManager.turnOnServiceIfOff(algoName);
    }

    @Override
    public boolean canGetLocation() {
        return localizationAlgorithmInterface.canGetLocation();
    }

    public boolean isProviderEnabled(){
        return localizationAlgorithmInterface.isProviderEnabled();
    }

    @Override
    public double getLongitude() {
        return localizationAlgorithmInterface.getLongitude();
    }

    @Override
    public double getLatitude() {
        return localizationAlgorithmInterface.getLatitude();
    }

    @Override
    public String toString() {
        return "MyLocationManager{" +
                "algoName=" + algoName +
                ", localizationAlgorithmInterface=" + localizationAlgorithmInterface +
                ", myPermissionsManager=" + myPermissionsManager +
                ", permissions=" + Arrays.toString(permissions) +
                '}';
    }

    public MyPermissionsManager getMyPermissionsManager() {
        return myPermissionsManager;
    }
}