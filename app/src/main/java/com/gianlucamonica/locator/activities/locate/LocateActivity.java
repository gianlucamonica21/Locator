package com.gianlucamonica.locator.activities.locate;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummaryDAO;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
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
    private LocationMiddleware locationMiddleware;

    private EditText actualGrid;
    private EditText estimatedGrid;
    private TextView buildingTV;
    private TextView floorTV;
    private TextView algorithmTV;
    private TextView sizeTV;

    private int idFloor;

    // loop
    final Handler handler = new Handler();
    final int delay = 5000; //milliseconds
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        MyApp.setActivity(this);
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        Bundle bundle = getIntent().getExtras();
        indoorParams = (ArrayList<IndoorParams>) bundle.getSerializable("indoorParams");

        actualGrid = (EditText) findViewById(R.id.actualGridEditText);
        estimatedGrid = (EditText) findViewById(R.id.estimateGridEditText);
        buildingTV = findViewById(R.id.buildingTextView);
        floorTV = findViewById(R.id.floorTextView);
        algorithmTV = findViewById(R.id.algorithmTextView);
        sizeTV = findViewById(R.id.sizeTextView);

        estimatedGrid.setEnabled(false);
        // recupero parametri indoor
        final Algorithm algorithm;
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        AlgorithmName algorithmName = AlgorithmName.MAGNETIC_FP;
        algorithmName = AlgorithmName.valueOf(algorithm.getName());
        final Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);
        final BuildingFloor buildingFloor = (BuildingFloor) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.FLOOR);
        final Config config = (Config) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.CONFIG);

        buildingTV.setText(building.getName());

        if(buildingFloor != null){
            floorTV.setText(buildingFloor.getName());
            idFloor = buildingFloor.getId();
        }
        else{
            floorTV.setText("Nessun Piano");
            idFloor = -1;
       }
        algorithmTV.setText(algorithm.getName());
        sizeTV.setText(String.valueOf(config.getParValue()));

        try{

            List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().
                    getScanSummaryByBuildingAlgorithm(building.getId(),idFloor,algorithm.getId(),config.getId(),"offline");
            final List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getOfflineScanDAO().
                    getOfflineScansById(scanSummary.get(0).getId());
            Log.i("locate activity","scansummary " +scanSummary.toString());
            Log.i("locate activity","offlinescans " +offlineScans.toString());


            Log.i("locate activity","instatiating " + algorithmName);
            locationMiddleware = MyApp.getLocationMiddlewareInstance();
            locationMiddleware.istantiate(algorithmName);
            // setting algorithm in mylocationmanager
            //myLocationManager = new MyLocationManager(algorithmName, indoorParams);

            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

            // setting the map view
            MapView mapView = new MapView(MyApp.getContext(), null,null, indoorParams, offlineScans);
            mLinearLayout.addView(mapView);

            actualGrid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    //prima scansione
                    if(buildingFloor == null)
                        Log.i("loc act","piano selezionato: " + "NULL");

                    OnlineScan onlineScan = locationMiddleware.locate();
                    if(onlineScan != null){
                        Log.i("locate activity", "onlinescan " + onlineScan.toString());
                        estimatedGrid.setText(String.valueOf(onlineScan.getIdEstimatedPos()));
                        handler.removeCallbacks(runnable);

                        if(onlineScan != null){
                            int actualPos = -1;
                            if(!actualGrid.getText().toString().equals("")){
                                actualPos = Integer.parseInt(actualGrid.getText().toString());
                            }
                            onlineScan.setIdActualPos(actualPos);
                            try {
                                // vecchio inserimento scan summary

                                List<ScanSummary> scanSummaries = databaseManager.getAppDatabase().getScanSummaryDAO().
                                        getScanSummaryByBuildingAlgorithm(building.getId(),algorithm.getId(),config.getId(),"online");
                                onlineScan.setIdScan(scanSummaries.get(0).getId());
                                Log.i("loc act","scan summary trovato: " + scanSummaries.get(0).toString());
                                Log.i("loc act","sei al piano: " + scanSummaries.get(0).getIdBuildingFloor());
                                databaseManager.getAppDatabase().getOnlineScanDAO().insert(onlineScan);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //successive altre scansioni
                    handler.postDelayed(runnable = new Runnable(){
                        public void run() {
                            //do something
                            //OnlineScan onlineScan = myLocationManager.locate();
                            OnlineScan onlineScan = locationMiddleware.locate();

                            if (onlineScan != null) {
                                int actualPos = -1;
                                if (!actualGrid.getText().toString().equals("")) {
                                    actualPos = Integer.parseInt(actualGrid.getText().toString());
                                }
                                onlineScan.setIdActualPos(actualPos);
                                try {
                                    List<ScanSummary> scanSummaries = databaseManager.getAppDatabase().getScanSummaryDAO().
                                            getScanSummaryByBuildingAlgorithm(building.getId(), idFloor, algorithm.getId(),
                                                    config.getId(), "online");
                                    onlineScan.setIdScan(scanSummaries.get(0).getId());
                                    Log.i("loc act", "id scan online " + scanSummaries.get(0).getId());
                                    databaseManager.getAppDatabase().getOnlineScanDAO().insert(onlineScan);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                estimatedGrid.setText(String.valueOf(onlineScan.getIdEstimatedPos()));
                                Log.i("locate activity", "onlinescan " + onlineScan.toString());
                                //todo inserire online scan in db
                                final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

                            // setting the map view
                            MapView mapView = new MapView(MyApp.getContext(),
                                    String.valueOf(onlineScan.getIdEstimatedPos()), actualGrid.getText().toString(), indoorParams, offlineScans);
                            mLinearLayout.addView(mapView);
                            handler.postDelayed(this, delay);

                            }
                        }
                    }, delay);
                }
            });




        }catch(Exception e){
            Log.e("error get scan",String.valueOf(e));
        }
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
