package com.gianlucamonica.locator.activities.scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.main.MainActivity;
import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummaryDAO;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {

    private ArrayList<IndoorParams> indoorParams;
    private MyLocationManager myLocationManager; // fare tutto con MyLocMiddleware
    private DatabaseManager databaseManager;
    private IndoorParamsUtils indoorParamsUtils;
    private LocationMiddleware locationMiddleware;

    private TextView buildingTV;
    private TextView floorTV;
    private TextView algorithmTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        MyApp.setActivity(this);

        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        Bundle bundle = getIntent().getExtras();
        indoorParams = (ArrayList<IndoorParams>) bundle.getSerializable("indoorParams");
        buildingTV = findViewById(R.id.buildingTextView);
        floorTV = findViewById(R.id.floorTextView);
        algorithmTV = findViewById(R.id.algorithmTextView);

        final Algorithm algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.ALGORITHM);
        final Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);
        final BuildingFloor buildingFloor = (BuildingFloor) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.FLOOR);



        buildingTV.setText(building.getName());
        if(buildingFloor != null){
            floorTV.setText(buildingFloor.getName());
        }
        else{
            floorTV.setText("Nessun Piano");
        }
        algorithmTV.setText(algorithm.getName());


        Log.i("scan act","alg name "+ AlgorithmName.valueOf(algorithm.getName()));

        // setting location middleware
        locationMiddleware = new LocationMiddleware(AlgorithmName.valueOf(algorithm.getName()),indoorParams);

        // setting algorithm in mylocationmanager
        //myLocationManager = new MyLocationManager(algorithmName, indoorParams);

        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

        // setting the map view
        //MapView v = (MapView) myLocationManager.build(MapView.class);
        MapView v = (MapView) locationMiddleware.build(MapView.class);
        mLinearLayout.addView(v);


        // handling redo scan
        Button redoButton = (Button) findViewById(R.id.redoButton);
        redoButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(MyApp.getContext(),
                        "Deleting scan",
                        Toast.LENGTH_SHORT).show();

                // delete scan
                deleteScanFromDB();
                // refreshing the mapview
//                MapView mapView = (MapView) myLocationManager.build(MapView.class);
                MapView mapView = (MapView) locationMiddleware.build(MapView.class);

                mLinearLayout.addView(mapView);
            }
        });

        // handling finish scan
        Button finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(MyApp.getContext(),
                        "Scan finished",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ScanActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void deleteScanFromDB(){
        try{
            ScanSummaryDAO scanSummaryDAO = databaseManager.getAppDatabase().getScanSummaryDAO();
            Algorithm algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.ALGORITHM);
            Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);
            Config config = (Config) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.CONFIG);

            Log.i("cancello", String.valueOf("a"+algorithm.getId() + "b" +  building.getId()+ "c" + config.getId() + " " + "OFFLINE"));
            scanSummaryDAO.deleteByBuildingAlgorithmConfig(building.getId(),algorithm.getId(),config.getId(),"offline");
        }catch (Exception e){
            Log.e("error get scan","error " +String.valueOf(e));
        }
    }
}
