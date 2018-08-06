package com.gianlucamonica.locator.model.outdoorLocationManager;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;
import com.google.android.gms.common.api.GoogleApiClient;

public class OutdoorLocationManager extends Service implements LocalizationAlgorithmInterface {

    public Location mLocation;
    private LocationManager locationManager;
    private LocationListener listener;
    private GoogleApiClient mGoogleApiClient;


    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    boolean isProviderEnabled = false;


    /**
     * constructor
     * @param context
     */
    public OutdoorLocationManager(Context context){

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE); // istantiate manager

        listener = new LocationListener() { // istantiate listener
            @Override
            public void onLocationChanged(Location location) {
//                mLocation.set(location);
                Log.i("onLocationChanged:","\n " + mLocation.getLongitude() + " " + mLocation.getLatitude());
                canGetLocation = true;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.i("onStatusChanged","i am here");
            }

            @Override
            public void onProviderEnabled(String s) {
                isProviderEnabled = true;
                Log.i("onProviderEnabled","i am here");
            }

            @Override
            public void onProviderDisabled(String s) {
                isProviderEnabled = false;
                Log.i("onProviderDisabled","i am here");
            }
        };

        // 2a versione
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return null;
    }

    @Override
    public void build() {

    }

    /**
     *
     * @return location
     */
    public Location locate() {

        if(mLocation == null){
            // getting last location or requesting update from listener
            try{
                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(mLocation == null){
                    Log.i("configure:button","i am requesting loc upd");
                    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                    locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);
                }else{
                    Log.i("mLocation: ",mLocation.toString());
                    return  mLocation;

                }
            }
            catch (SecurityException e){

            }
        }
        return  mLocation;


    }

    @Override
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public Location getmLocation(){
        return this.mLocation;
    }

    public boolean isProviderEnabled(){
        return this.isProviderEnabled;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
