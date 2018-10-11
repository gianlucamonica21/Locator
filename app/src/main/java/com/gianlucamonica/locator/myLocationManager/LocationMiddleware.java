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
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.permissionsManager.MyPermissionsManager;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

/**
 * this class lies between the app and the my loc manager class
 * the user only instantiate it by the public constructor, then an outdoor or indoor alg is called according to the GPS acc
 */
public class LocationMiddleware implements LocationListener {

    // listener's and location params
    private static final float GPS_ACC_THRESHOLD = 15;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_TIME_BW_UPDATES = 0;
    private Boolean checkGPS;
    private Boolean checkNetwork;

    // my loc manager's stuff
    private MyLocationManager  myLocationManager;
    protected LocationManager locationManager; // in order to retrieve the gps loc
    private MyPermissionsManager myPermissionsManager; // in order to get the necessary perm
    private String[] permissions;
    private float liveGPSAcc = 0; // gps acc just registered
    private AlgorithmName chosenIndoorAlg = AlgorithmName.MAGNETIC_FP; // default indoor alg

    private Activity activity; // ?
    private ArrayList<IndoorParams> indoorParams;

    /**
     * @param activity
     * @param indoorParams
     */
    public LocationMiddleware(Activity activity, ArrayList<IndoorParams> indoorParams){
        this.activity = activity;
        this.indoorParams = indoorParams;
        Log.i("receveid indoor params",indoorParams.toString());

        myPermissionsManager = new MyPermissionsManager(activity, AlgorithmName.GPS);
        permissions = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        checkPermissions();  // asking gps permissions
        initLocManager();

        if(checkGPS || checkNetwork){
            requestUpdates(); // request location updates
        }
    }

    /**
     * checks if the user is outside or inside a building and instatiate relative myLocMan
     */
    public void init(){
        if(liveGPSAcc > GPS_ACC_THRESHOLD){
            // istantiate outdoor alg
            Log.i("instantiate","GPS location");
            Toast.makeText(MyApp.getContext(),"istantiate GPS",Toast.LENGTH_SHORT).show();
         }else {
            // istantiate indoor alg
            Log.i("instantiate", "Indoor location");
            Toast.makeText(MyApp.getContext(),"istantiate indoor",Toast.LENGTH_SHORT).show();
         }

        // cancel location updates
        stopListener();
    }

    public void locate(){
        myLocationManager.locate();
    }

    /**
     * instatiate locationManager
     */
    public void initLocManager(){
        locationManager = (LocationManager) MyApp.getContext()
                .getSystemService(LOCATION_SERVICE);
        // get GPS status
        checkGPS = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // get network provider status
        checkNetwork = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void requestUpdates(){
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

    public void stopListener() {
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

    /**
     * check permissions for the gps algorithm
     */
    public void checkPermissions() {
        myPermissionsManager.requestPermission(permissions);
        myPermissionsManager.turnOnServiceIfOff();
    }

    @Override
    public void onLocationChanged(Location location) {
        liveGPSAcc = location.getAccuracy();
        Log.i("onLocationChanged","acc: " + liveGPSAcc);
        init();
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
}
