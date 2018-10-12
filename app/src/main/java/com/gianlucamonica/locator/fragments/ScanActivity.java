package com.gianlucamonica.locator.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {

    private ArrayList<IndoorParams> indoorParams;
    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Bundle bundle = getIntent().getExtras();
        indoorParams = (ArrayList<IndoorParams>) bundle.getSerializable("indoorParams");

        Algorithm algorithm;
        AlgorithmName algorithmName = AlgorithmName.MAGNETIC_FP;
        for (int i = 0; i < indoorParams.size(); i++){
            if (indoorParams.get(i).getName().equals("algorithm")){
                algorithm = (Algorithm) indoorParams.get(i).getParamObject();
                algorithmName = AlgorithmName.valueOf(algorithm.getName());
            }
        }
        // setting algorithm in mylocationmanager
        myLocationManager = new MyLocationManager(algorithmName,this, indoorParams);

        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

        // setting the map view
        MapView v = (MapView) myLocationManager.build(MapView.class);
        mLinearLayout.addView(v);
    }
}
