package com.gianlucamonica.locator.utils.permissionsManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;
import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;
import static android.provider.Settings.ACTION_WIFI_SETTINGS;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class MyPermissionsManager {

    private Activity activity;

    public MyPermissionsManager(Activity activity){
        this.activity = activity;
    }

    public void requestPermission(final String[] permissions){
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity,
            permissions, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(activity,
                                "Sorry, we need the Storage Permission to do that",
                                Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(
                                activity,
                                permissions,200);

                    }
                });

    }

    public void turnOnServiceIfOff(AlgorithmName algorithmName){

        switch (algorithmName){
            case GPS:
                LocationManager locationManager = (LocationManager) MyApp.getContext()
                        .getSystemService(LOCATION_SERVICE);

                // get GPS status
                boolean checkGPS = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                if(!checkGPS){
                    showDialog(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                }
                break;
            case WIFI:
                // to turn on WIFI
                WifiManager wifi = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(MyApp.getContext().WIFI_SERVICE);
                if (!wifi.isWifiEnabled()){
                    //wifi is not enabled
                    showDialog(Settings.ACTION_WIFI_SETTINGS);
                }
                break;
        }
    }

    public void showDialog(final String providerToEnable){
        String providerMsg = "";
        switch (providerToEnable){
            case ACTION_LOCATION_SOURCE_SETTINGS:
                providerMsg = "GPS";
                break;
            case ACTION_WIFI_SETTINGS:
                providerMsg = "WIFI";
                break;
        }

        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(activity);

        alertDialog.setTitle(providerMsg + " is not Enabled!");

        alertDialog.setMessage("Do you want to turn on " + providerMsg + "?");


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(providerToEnable);
                activity.startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }
}
