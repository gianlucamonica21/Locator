package com.gianlucamonica.locator.activities.wifi.offlineActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gianlucamonica.locator.activities.wifi.offlineActivity.mapBuilder.MapView;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;

public class OfflineActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_offline);

        myLocationManager = new MyLocationManager(AlgorithmName.WIFI, this);
        MapView v = (MapView) myLocationManager.build(MapView.class);
        setContentView(v);
    }
}
