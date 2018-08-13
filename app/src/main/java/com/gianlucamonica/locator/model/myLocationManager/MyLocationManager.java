package com.gianlucamonica.locator.model.myLocationManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.WifiAlgorithm;
import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.model.outdoorLocationManager.OutdoorLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.permissionsManager.MyPermissionsManager;

import java.io.Serializable;
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

        switch (algoName) {
            case GPS:
                this.algoName = algoName;
                localizationAlgorithmInterface = new OutdoorLocationManager(MyApp.getContext());
                permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
                break;
            case WIFI:
                this.algoName = algoName;
                localizationAlgorithmInterface = new WifiAlgorithm(activity);
                permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
                break;
            default:
        }

        myPermissionsManager = new MyPermissionsManager(activity);
        checkPermissions();
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
        Location l = localizationAlgorithmInterface.locate();
        return l;
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
    public void showSettingsAlert() {
        localizationAlgorithmInterface.showSettingsAlert();
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
}
