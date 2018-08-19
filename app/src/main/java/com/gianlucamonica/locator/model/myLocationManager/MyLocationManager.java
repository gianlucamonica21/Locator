package com.gianlucamonica.locator.model.myLocationManager;

import android.Manifest;
import android.app.Activity;
import android.location.Location;
import android.view.View;

import com.gianlucamonica.locator.model.impls.wifi.WifiAlgorithm;
import com.gianlucamonica.locator.model.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.model.impls.gps.GPSLocationManager;
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
     * constructor which build a new istance of a particular algorithm according to algoName
     * for each algorithm permissions and enables are checked
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

                if ( myPermissionsManager.isGPSEnabled())
                    localizationAlgorithmInterface = new GPSLocationManager(MyApp.getContext());
                break;
            case WIFI_RSS_FP:
                this.algoName = algoName;
                permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                checkPermissions();

                if (myPermissionsManager.isWIFIEnabled())
                    localizationAlgorithmInterface = new WifiAlgorithm(activity);
                break;
            default:
        }

    }

    /**
     * @param activity
     * @return algorithm build class
     */
    @Override
    public Object getBuildClass(Activity activity) {
        return localizationAlgorithmInterface.getBuildClass(activity);
    }

    /**
     * @param type
     * @param <T>
     * @return class subtype of View which is used for the build phase
     */
    @Override
    public  <T extends View> T build(Class<T> type) {
        return localizationAlgorithmInterface.build(type);
    }

    /**
     * @return Location computed with the specified algorithm
     */
    public Location locate() {
        return localizationAlgorithmInterface.locate();
    }

    /**
     * check permissions and turn on the providers according to the algorithm
     */
    @Override
    public void checkPermissions() {
        myPermissionsManager.requestPermission(permissions);
        myPermissionsManager.turnOnServiceIfOff(algoName);
    }

    public MyPermissionsManager getMyPermissionsManager() {
        return myPermissionsManager;
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
