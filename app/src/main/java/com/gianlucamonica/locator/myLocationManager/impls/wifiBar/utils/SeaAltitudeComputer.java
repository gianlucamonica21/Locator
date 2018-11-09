package com.gianlucamonica.locator.myLocationManager.impls.wifiBar.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;

import static android.content.Context.LOCATION_SERVICE;

public class SeaAltitudeComputer implements LocationListener {

    private DatabaseManager databaseManager;
    private LocationManager locationManager;

    public SeaAltitudeComputer() {
        databaseManager = new DatabaseManager();
        locationManager = (LocationManager) MyApp.getContext()
                .getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return null;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Sea Altitude","sea altitude" + location.getAltitude());
        databaseManager.getAppDatabase().getLiveMeasurementsDAO().insert(
                new LiveMeasurements(3,-1 , "sea_altitude", 1 , location.getAltitude())
        );
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
