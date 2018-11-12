package com.gianlucamonica.locator.myLocationManager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.gianlucamonica.locator.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.locInfo.LocInfo;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.permissionsManager.MyPermissionsManager;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

/**
 * this class lies between the app and the my loc manager class
 * the user only instantiate it by the public constructor, then an outdoor or indoor alg is called according to the GPS acc
 */
public class LocationMiddleware implements LocationListener, LocalizationAlgorithmInterface {

    /**
     * location listener's and location params
     */
    private static final float GPS_ACC_THRESHOLD = 20;
    protected LocationManager locationManager; // in order to retrieve the gps loc
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_TIME_BW_UPDATES = 0;
    private Boolean checkGPS;
    private Boolean checkNetwork;

    private DatabaseManager databaseManager;

    private MyPermissionsManager myPermissionsManager; // in order to get the necessary perm
    private String[] permissions;
    private float liveGPSAcc = 0; // gps acc just registered

    private boolean INDOOR_LOC;
    private int updatesNumber = 0;
    private ArrayList<IndoorParams> indoorParams; // indoor algorithm's params such as building, algorithm and grid size
    private MyLocationManager myLocationManager;

    /**
     * @param indoorParams
     */
    public LocationMiddleware(ArrayList<IndoorParams> indoorParams){
        databaseManager = new DatabaseManager();
        this.indoorParams = indoorParams;
        this.myPermissionsManager = new MyPermissionsManager(AlgorithmName.GPS);
        permissions = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        checkPermissions();  // asking gps permissions
        initLocManager();

        if(checkGPS || checkNetwork){
            requestUpdates(); // request location updates
        }
    }

    public void istantiate(AlgorithmName chosenIndoorAlg){
        //  new
        // this.INDOOR_LOC = databaseManager.getAppDatabase().getLocInfoDAO().getLocInfo();

        checkPermissions();  // asking permissions
        if(INDOOR_LOC){
            myLocationManager = new MyLocationManager(chosenIndoorAlg,indoorParams);
        }else{
            myLocationManager = new MyLocationManager(AlgorithmName.GPS,indoorParams);
        }
    }


    /**
     * checks if the user is outside or inside a building and instatiate relative myLocMan
     */
    private void init(){

        Log.i("loc midd","live gpsacc " + liveGPSAcc + "thres " + GPS_ACC_THRESHOLD);
        if(liveGPSAcc > GPS_ACC_THRESHOLD){
            databaseManager.getAppDatabase().getLocInfoDAO().insert(new LocInfo(false));
        }else {
            databaseManager.getAppDatabase().getLocInfoDAO().insert(new LocInfo(true));
        }

        updatesNumber++;
    }

    /**
     * instatiate locationManager
     */
    private void initLocManager(){
        locationManager = (LocationManager) MyApp.getContext()
                .getSystemService(LOCATION_SERVICE);
        // get GPS status
        checkGPS = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // get network provider status
        checkNetwork = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void requestUpdates(){
        if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    private void stopListener() {
        if (locationManager != null) {

            if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(LocationMiddleware.this);
        }
    }

    @Override
    public Object getBuildClass() {
        return null;
    }

    @Override
    public <T extends View> T build(Class<T> type) {
        return myLocationManager.build(type);
    }

    @Override
    public <T> T locate() {
        return myLocationManager.locate();
    }

    /**
     * check permissions for the gps algorithm
     */
    public void checkPermissions() {
        myPermissionsManager.requestPermission(permissions);
        myPermissionsManager.turnOnServiceIfOff();
    }

    /**
     * wait for location's change and call init to instantiate indoor or outdoor algorithm
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        liveGPSAcc = location.getAccuracy();
        Log.i("onLocationChanged","acc: " + liveGPSAcc);
        init();
        //stopListener();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean isINDOOR_LOC() {
        if(updatesNumber > 0){
            return INDOOR_LOC;
        }else{
            INDOOR_LOC = databaseManager.getAppDatabase().getLocInfoDAO().getLocInfo();
            return INDOOR_LOC;
        }
    }

}
