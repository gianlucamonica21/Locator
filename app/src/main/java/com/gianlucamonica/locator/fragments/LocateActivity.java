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
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class LocateActivity extends AppCompatActivity {

    private ArrayList<IndoorParams> indoorParams;
    private MyLocationManager myLocationManager; // fare tutto con MyLocMiddleware
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
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        AlgorithmName algorithmName = AlgorithmName.MAGNETIC_FP;
        algorithmName = AlgorithmName.valueOf(algorithm.getName());
        Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);
        int size =(int) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.SIZE);

        try{
            List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(building.getId(),algorithm.getId(),size);
            List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScansById(scanSummary.get(0).getId());

            // setting algorithm in mylocationmanager
            myLocationManager = new MyLocationManager(algorithmName,this, indoorParams);
            OnlineScan onlineScan = myLocationManager.locate();
            Log.i("online scan",onlineScan.toString());

            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

            // setting the map view
            MapView mapView = new MapView(this, String.valueOf(onlineScan.getIdEstimatedPos()),indoorParams, offlineScans);
            mLinearLayout.addView(mapView);

        }catch(Exception e){
            Log.e("error get scan",String.valueOf(e));
        }
    }
}
