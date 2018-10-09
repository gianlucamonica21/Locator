package com.gianlucamonica.locator.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

public class ScanActivity extends AppCompatActivity {

    private Algorithm algorithm;
    private Building building;
    private int gridSize;

    private MyLocationManager myLocationManager;
    private AlgorithmName algorithmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Bundle bundle = getIntent().getExtras();
        building = (Building) bundle.getSerializable("building");
        algorithm = (Algorithm) bundle.getSerializable("algorithm");
        gridSize = (int) bundle.getSerializable("gridSize");

        if(algorithm != null){

            if( algorithm.getName().equals(String.valueOf(AlgorithmName.MAGNETIC_FP)) ){
                algorithmName = AlgorithmName.MAGNETIC_FP;
            }
            else if( algorithm.getName().equals(String.valueOf(AlgorithmName.WIFI_RSS_FP)) ){
                algorithmName = AlgorithmName.WIFI_RSS_FP;
            }
        }

        // setting algorithm in mylocationmanager
        myLocationManager = new MyLocationManager(algorithmName,this);

        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

        // setting the map view
        MapView v = (MapView) myLocationManager.build(MapView.class);
        mLinearLayout.addView(v);
    }
}
