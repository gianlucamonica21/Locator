package com.gianlucamonica.locator.myLocationManager.impls.magnetic.online;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrintDAO;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.online.EuclideanDistanceAlg;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScanDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class MagneticOnlineManager implements SensorEventListener {

    private DatabaseManager databaseManager;
    private Activity  activity;
    private ArrayList<MagneticFingerPrint> magneticFingerPrints;
    private SensorManager sensorManager; // magnetic sensor's stuffs
    private int scanNumber = 0;
    private double magnitudeValue;
    private EuclideanDistanceAlg euclideanDistanceAlg;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;
    private int idScan;

    public MagneticOnlineManager(Activity activity, ArrayList<IndoorParams> indoorParams){
        this.activity = activity;
        databaseManager = new DatabaseManager(activity);
        magneticFingerPrints = new ArrayList<>();
        sensorManager = (SensorManager) MyApp.getContext().getSystemService(SENSOR_SERVICE);
        this.indoorParams = indoorParams;
        this.indoorParamsUtils = new IndoorParamsUtils();
    }

    public OnlineScan locate(){

        //getting m.f. value
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

        //getting map from db and do alg
        List<OfflineScan> offlineScans = getMagneticFingerPrintsFromDb();
        Log.i("offlineScan",offlineScans.toString());
        if (offlineScans.size() > 0) {

            Log.i("calcolo euclidean","");
            euclideanDistanceAlg = new EuclideanDistanceAlg(offlineScans, magnitudeValue);
            int index = euclideanDistanceAlg.compute(AlgorithmName.MAGNETIC_FP);

            return new OnlineScan(idScan,index,0,new Date());

        } else {
            Toast.makeText(MyApp.getContext(),
                    "Non info in db",
                    Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public List<OfflineScan> getMagneticFingerPrintsFromDb(){
        int idBuilding = indoorParamsUtils.getBuilding(indoorParams).getId();
        int idAlgorithm= indoorParamsUtils.getAlgorithm(indoorParams).getId();
        int gridSize = indoorParamsUtils.getSize(indoorParams);

        List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(idBuilding,idAlgorithm,gridSize);
        idScan = scanSummary.get(0).getId();
        Log.i("idScan", String.valueOf(idScan));
        List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScansById(idScan);

        return  offlineScans;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        while(scanNumber == 0) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // get values for each axes X,Y,Z
                float magX = event.values[0];
                float magY = event.values[1];
                float magZ = event.values[2];
                double magnitude = Math.sqrt((magX * magX) + (magY * magY) + (magZ * magZ));
                scanNumber++;
                magnitudeValue = magnitude;
                // set value on the screen
                Toast.makeText(activity, "m.f. value " + magnitude, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
