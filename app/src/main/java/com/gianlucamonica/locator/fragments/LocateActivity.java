package com.gianlucamonica.locator.fragments;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamsUtils;
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

    private EditText actualGrid;

    // loop
    final Handler handler = new Handler();
    final int delay = 5000; //milliseconds
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        databaseManager = new DatabaseManager(this);
        indoorParamsUtils = new IndoorParamsUtils();
        Bundle bundle = getIntent().getExtras();
        indoorParams = (ArrayList<IndoorParams>) bundle.getSerializable("indoorParams");

        actualGrid = (EditText) findViewById(R.id.actualGridEditText);

        // recupero parametri indoor
        Algorithm algorithm;
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        AlgorithmName algorithmName = AlgorithmName.MAGNETIC_FP;
        algorithmName = AlgorithmName.valueOf(algorithm.getName());
        Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);
        Config config = (Config) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.CONFIG);

        try{
            List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().
                    getScanSummaryByBuildingAlgorithm(building.getId(),algorithm.getId(),config.getId());
            final List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScansById(scanSummary.get(0).getId());
            Log.i("locate activity","scansummary " +scanSummary.toString());
            Log.i("locate activity","offlinescans " +offlineScans.toString());

            // setting algorithm in mylocationmanager
            //todo fare scan ogni tot secondi
            myLocationManager = new MyLocationManager(algorithmName,this, indoorParams);

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
                    OnlineScan onlineScan = myLocationManager.locate();
                    Log.i("locate activity", "onlinescan " + onlineScan.toString());
                    //todo inserire online scan in db
                    //successive altre scansioni
                    handler.postDelayed(runnable = new Runnable(){
                        public void run(){
                            //do something
                            OnlineScan onlineScan = myLocationManager.locate();
                            Log.i("locate activity", "onlinescan " + onlineScan.toString());
                            //todo inserire online scan in db
                            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

                            // setting the map view
                            MapView mapView = new MapView(MyApp.getContext(),
                                    String.valueOf(onlineScan.getIdEstimatedPos()), actualGrid.getText().toString()  , indoorParams, offlineScans);
                            mLinearLayout.addView(mapView);
                            handler.postDelayed(this, delay);
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
