package com.gianlucamonica.locator.activities.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.fragments.MapsActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.model.outdoorLocationManager.OutdoorLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class GPSActivity extends Activity implements OnMapReadyCallback {
    public static final String EXTRA_LAT = "lat";
    public static final String EXTRA_LNG = "lng";

    private GoogleMap mMap;
    // location's stuff
    private MyLocationManager myLocationManager;;

    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gps);
        Button btn = (Button) findViewById(R.id.button3);

        myLocationManager = new MyLocationManager(AlgorithmName.GPS,this);

        if(!myLocationManager.getMyPermissionsManager().isCheckGPS()){
            btn.setEnabled(false);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting location
                if (myLocationManager.canGetLocation()) {

                    double longitude = myLocationManager.getLongitude();
                    double latitude = myLocationManager.getLatitude();

                    Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(GPSActivity.this, MapsActivity.class);
                    intent.putExtra(EXTRA_LAT, longitude);
                    intent.putExtra(EXTRA_LNG, latitude);

                    startActivity(intent);
                }
            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // drawing location on map
     /*   if(this.mLocation != null){
            LatLng mPos = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(mPos)
                    .title("You are here"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(mPos));
        }else{
            Log.i("onMapReady:", "location null");
        }*/

    }


}
