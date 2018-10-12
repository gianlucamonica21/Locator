package com.gianlucamonica.locator.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScanDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummaryDAO;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {

    private ArrayList<IndoorParams> indoorParams;
    private MyLocationManager myLocationManager;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

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

        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

        // setting the map view
        MapView v = (MapView) myLocationManager.build(MapView.class);
        mLinearLayout.addView(v);

        // handling redo scan
        Button redoButton = (Button) findViewById(R.id.redoButton);
        redoButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(MyApp.getContext(),
                        "Deleting entries",
                        Toast.LENGTH_SHORT).show();

                // refreshing the mapview
                MapView mapView = (MapView) myLocationManager.build(MapView.class);
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

            }
        });
    }

    private void deleteScanFromDB(){
        ScanSummaryDAO scanSummaryDAO = databaseManager.getAppDatabase().getScanSummaryDAO();


    }
}
