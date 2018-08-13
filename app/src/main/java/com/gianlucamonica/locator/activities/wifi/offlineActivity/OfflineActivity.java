package com.gianlucamonica.locator.activities.wifi.offlineActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.mapBuilder.MapView;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;
import java.lang.reflect.Type;

public class OfflineActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_offline);
        myLocationManager = MyApp.getMyLocationManagerInstance();
        MapView v = (MapView) myLocationManager.build(MapView.class);
        setContentView(v);

    }
}
