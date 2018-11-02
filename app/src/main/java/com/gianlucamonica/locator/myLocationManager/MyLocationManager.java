package com.gianlucamonica.locator.myLocationManager;

import android.Manifest;
import android.view.View;

import com.gianlucamonica.locator.myLocationManager.impls.magnetic.MagneticFieldAlgorithm;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.WifiAlgorithm;
import com.gianlucamonica.locator.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.myLocationManager.impls.gps.GPSLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.permissionsManager.MyPermissionsManager;

import java.util.ArrayList;
import java.util.Arrays;

public class MyLocationManager implements LocalizationAlgorithmInterface {

    private AlgorithmName algoName;
    private LocalizationAlgorithmInterface localizationAlgorithmInterface;
    private MyPermissionsManager myPermissionsManager;
    private String[] permissions;
    // infos
    private Algorithm algorithm;
    private Building building;
    private int gridSize;
    private ArrayList<IndoorParams> indoorParams;

    /**
     * istantiate this class according to algoName
     * @param algoName
     * @param indoorParams
     */
    public MyLocationManager(AlgorithmName algoName, ArrayList<IndoorParams> indoorParams) {


        myPermissionsManager = new MyPermissionsManager(algoName);
        if( indoorParams != null )
            this.indoorParams = indoorParams;

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

                if (myPermissionsManager.isWIFIEnabled()){
                    localizationAlgorithmInterface = new WifiAlgorithm(indoorParams);
                }

                break;
            case MAGNETIC_FP:
                this.algoName = algoName;
                permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                checkPermissions();

                localizationAlgorithmInterface = new MagneticFieldAlgorithm(indoorParams);

                break;
            default:
        }

    }

    /**
     * @return algorithm build class
     */
    @Override
    public Object getBuildClass() {
        return localizationAlgorithmInterface.getBuildClass();
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
    public <T> T locate() {
        return localizationAlgorithmInterface.locate();
    }

    /**
     * check permissions and turn on the providers according to the algorithm
     */
    @Override
    public void checkPermissions() {
        myPermissionsManager.requestPermission(permissions);
        myPermissionsManager.turnOnServiceIfOff();
    }

    public MyPermissionsManager getMyPermissionsManager() {
        return myPermissionsManager;
    }

    public void setMyPermissionsManager(MyPermissionsManager myPermissionsManager) {
        this.myPermissionsManager = myPermissionsManager;
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
