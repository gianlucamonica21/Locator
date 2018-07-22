package com.gianlucamonica.locator.model.outdoorLocationManager;

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

public class OutdoorLocationManager extends Service implements LocationListener {

    private final Context context;

    public Location location;
    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;


    /**
     * constructor
     * @param context
     */
    public OutdoorLocationManager(Context context){
        this.context = context;
    }

    /**
     *
     * @return location
     */
    public Location locate() {
        return  null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("onLocationChanged", location.getLatitude() + " " + location.getLongitude() );
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
