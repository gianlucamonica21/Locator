package com.gianlucamonica.locator.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

import java.util.ArrayList;

public class LocateActivity extends AppCompatActivity {

    private ArrayList<IndoorParams> indoorParams;
    private MyLocationManager myLocationManager;
    private DatabaseManager databaseManager;
    private IndoorParamsUtils indoorParamsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        databaseManager = new DatabaseManager(this);
        indoorParamsUtils = new IndoorParamsUtils();
        Bundle bundle = getIntent().getExtras();
        indoorParams = (ArrayList<IndoorParams>) bundle.getSerializable("indoorParams");

        Algorithm algorithm;
        AlgorithmName algorithmName = AlgorithmName.MAGNETIC_FP;
        for (int i = 0; i < indoorParams.size(); i++){
            if (indoorParams.get(i).getName() == IndoorParamName.ALGORITHM){
                algorithm = (Algorithm) indoorParams.get(i).getParamObject();
                algorithmName = AlgorithmName.valueOf(algorithm.getName());
            }
        }
        // setting algorithm in mylocationmanager
        myLocationManager = new MyLocationManager(algorithmName,this, indoorParams);
        OnlineScan onlineScan = myLocationManager.locate();
        Log.i("online scan",onlineScan.toString());

        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

        // setting the map view
        MapView mapView = new MapView(this, String.valueOf(onlineScan.getIdEstimateGrid()),indoorParams);
        mLinearLayout.addView(mapView);
    }
}
