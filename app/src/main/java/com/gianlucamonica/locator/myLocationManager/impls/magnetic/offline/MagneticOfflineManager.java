package com.gianlucamonica.locator.myLocationManager.impls.magnetic.offline;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.map.Grid;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.SENSOR_SERVICE;

public class MagneticOfflineManager implements SensorEventListener {

    private MapView mV; // map building
    private Activity activity;
    private SensorManager sensorManager; // magnetic sensor's stuffs
    private DatabaseManager databaseManager;

    private int scanNumber = 0; // m. field scan counter
    private ArrayList<Double> magnitudes;
    private ArrayList<String> zones;
    private ArrayList<OfflineScan> offlineScans;
    private int clicksOnMap = 0;
    private boolean inserted = false;
    private double liveMagnitude;
    private String liveGridName;

    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;

    private int idBuilding;
    private int idAlgorithm;
    private int gridSize;

    public MagneticOfflineManager(Activity activity,ArrayList<IndoorParams> indoorParams){
        this.activity = activity;
        this.indoorParams = indoorParams;
        sensorManager = (SensorManager) MyApp.getContext().getSystemService(SENSOR_SERVICE);
        magnitudes = new ArrayList<>();
        zones = new ArrayList<>();
        offlineScans = new ArrayList<>();
        indoorParamsUtils = new IndoorParamsUtils();
        databaseManager = new DatabaseManager(activity);
        idBuilding = indoorParamsUtils.getBuilding(indoorParams) != null ? indoorParamsUtils.getBuilding(indoorParams).getId() : -1;
        idAlgorithm = indoorParamsUtils.getAlgorithm(indoorParams) != null ? indoorParamsUtils.getAlgorithm(indoorParams).getId() : -1;
        gridSize = indoorParamsUtils.getSize(indoorParams) != -1 ? indoorParamsUtils.getSize(indoorParams) : -1;
    }

    /**
     * build and draw the map, listen for scans
     * @param type
     * @param <T>
     * @return
     */
    public <T extends View> T build(Class<T> type){

        mV = new MapView(this.activity,null,indoorParams, null);
        mV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //todo get magnetic value
                collectMagneticFieldValueByUI(event);
                return false;
            }
        });
        Toast.makeText(this.activity,
                "Tap on the grid corresponding to your position to do a scan, if you want to redo it click 'Redo Scan'",
                Toast.LENGTH_LONG).show();

        return type.cast(mV);

    }

    public void collectMagneticFieldValueByUI(MotionEvent event){

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            Log.i("TOUCHED ", x + " " + y);

            ArrayList<Grid> rects = mV.getRects();

            if(rects.size() == 0){
                // scan finished
                Toast.makeText(MyApp.getContext(),"scan is finished, you can go to the online localization",Toast.LENGTH_SHORT).show();
                Log.i("zone and magn size", zones.size() + " - " + magnitudes.size());
                if(zones.size() == magnitudes.size()){
                    if(inserted){
                        //1a) recupero id scan
                        int idScan = getIdScanSummary();
                        if( idScan != -1){
                            for (int i = 0; i < zones.size(); i++){
                                //2) inserisco in offline scan
                                try {
                                    databaseManager.getAppDatabase().getOfflineScanDAO().insert(
                                            new OfflineScan(idScan,Integer.parseInt(zones.get(i)),magnitudes.get(i),new Date())
                                    );
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }else{
                        Log.i("insert scan summary","non riuscito");
                    }
                }

            }else{
                for(int i = 0; i < rects.size(); i = i + 1){
                    float aX = ((rects.get(i).getA().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bX = ((rects.get(i).getB().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bY = ((rects.get(i).getB().getY()*mV.getScaleFactor())+ mV.getAdd());
                    float aY = ((rects.get(i).getA().getY()*mV.getScaleFactor())+ mV.getAdd());

                    if( x >= aX && x <= bX){
                        if( y <= bY && y >= aY){
                            liveGridName = rects.get(i).getName();
                            Log.i("gridname clicked",liveGridName);

                            clicksOnMap++;
                            // se clicco all'interno della mappa, la prima volta inserisco in scan summary
                            if(clicksOnMap == 1){
                                inserted = insertNewScanSummary();
                            }
                            //get magnetic field value
                            scanNumber = 0;
                            sensorManager.registerListener(this,
                                    sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                                    SensorManager.SENSOR_DELAY_FASTEST);


                            rects.remove(i);
                            Log.i("rects size on touch",String.valueOf(rects.size()));
                        }
                    }
                }
                mV.invalidate(); // redrawing during the scan, in order to set invisible the grid already scanned
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        while(scanNumber == 0) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // get values for each axes X,Y,Z
                float magX = event.values[0];
                float magY = event.values[1];
                float magZ = event.values[2];
                liveMagnitude = Math.sqrt((magX * magX) + (magY * magY) + (magZ * magZ));
                scanNumber++;

                if(inserted){
                    //1a) recupero id scan
                    int idScan = getIdScanSummary();
                    if( idScan != -1){
                        //2) inserisco in offline scan
                        Log.i("inserisco magnitude", String.valueOf(liveMagnitude));
                        try {
                            databaseManager.getAppDatabase().getOfflineScanDAO().insert(
                                    new OfflineScan(idScan,Integer.parseInt(liveGridName),liveMagnitude,new Date()));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Log.i("insert scan summary","non riuscito");
                    }
                }
                else{
                    Log.i("insert scan summary","non riuscito");
                }
                // set value on the screen
                Log.i("live magnitude", String.valueOf(liveMagnitude));
                Toast.makeText(activity, "liveMagnitude  " + liveMagnitude + " grid " + liveGridName, Toast.LENGTH_SHORT).show();
            }
        }
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private boolean insertNewScanSummary(){
        if( idAlgorithm != -1 && idBuilding != -1 && gridSize != -1){
            // inserisco in scan summary
            try{
                databaseManager.getAppDatabase().getScanSummaryDAO().insert(
                        new ScanSummary(idBuilding, -1 , idAlgorithm, gridSize, "offline")
                );
            }catch(Exception e){
                Log.i("catched ", String.valueOf(e));
            }
            return true;
        }

        return false;
    }

    private int getIdScanSummary(){
        if( idAlgorithm != -1 && idBuilding != -1 && gridSize != -1){
            ArrayList<ScanSummary> scanSummary;
            // inserisco in scan summary
            databaseManager.getAppDatabase().getScanSummaryDAO().insert(
                    new ScanSummary(idBuilding, -1, idAlgorithm, 1, "offline")
            );
            scanSummary = (ArrayList<ScanSummary>) databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(idBuilding, idAlgorithm, gridSize);
            return scanSummary.get(0).getId();
        }
        return -1;
    }
}
