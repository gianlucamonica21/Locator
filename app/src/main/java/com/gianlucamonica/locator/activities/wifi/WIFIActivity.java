package com.gianlucamonica.locator.activities.wifi;

import android.Manifest;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.wifi.offlineActivity.OfflineActivity;
import com.gianlucamonica.locator.activities.wifi.onlineActivity.OnlineActivity;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

public class WIFIActivity extends AppCompatActivity {

    MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        myLocationManager = new MyLocationManager(AlgorithmName.WIFI, this);
        MyApp.setMyLocationManagerInstance(myLocationManager);
    }

    public void openOfflineActivity(View view){
        Intent intent = new Intent(this, OfflineActivity.class);
        startActivity(intent);
    }

    public void openOnlineActivity(View view){
        Intent intent = new Intent(this, OnlineActivity.class);
        startActivity(intent);
    }



}
