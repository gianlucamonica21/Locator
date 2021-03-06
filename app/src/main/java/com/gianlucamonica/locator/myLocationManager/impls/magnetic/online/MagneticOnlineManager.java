package com.gianlucamonica.locator.myLocationManager.impls.magnetic.online;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.impls.onlinePhaseAlgs.EuclideanDistanceAlg;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locator.myLocationManager.utils.db.currentGPSPosition.CurrentGPSPosition;
import com.gianlucamonica.locator.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class MagneticOnlineManager implements SensorEventListener {

    private DatabaseManager databaseManager;
    private Activity  activity;
    private SensorManager sensorManager; // magnetic sensor's stuffs
    private int scanNumber = 0;
    private double magnitudeValue;
    private EuclideanDistanceAlg euclideanDistanceAlg;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;
    private int idScan;
    private OnlineScan onlineScan;
    private List<OfflineScan> offlineScans;
    private boolean getMagnitude = false;

    public MagneticOnlineManager(ArrayList<IndoorParams> indoorParams){
        databaseManager = new DatabaseManager();
        sensorManager = (SensorManager) MyApp.getContext().getSystemService(SENSOR_SERVICE);
        this.indoorParams = indoorParams;
        this.indoorParamsUtils = new IndoorParamsUtils();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public OnlineScan locate(){

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);

        //getting map from db and do alg
        List<OfflineScan> offlineScans = getMagneticFingerPrintsFromDb();
        Log.i("offlineScan",offlineScans.toString());

        if (offlineScans.size() > 0) {

            List<LiveMeasurements> liveMeasurements =
                    databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(1,"magn_rss");

            if(liveMeasurements.size() == 0){
                databaseManager.getAppDatabase().getLiveMeasurementsDAO().insert(
                        new LiveMeasurements(1,-1 , "magn_rss", 1, 50)
                );
                liveMeasurements =
                        databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(1,"magn_rss");
            }

            if(liveMeasurements.size() != 0){
                double liveMagnitude = liveMeasurements.get(0).getValue();
                euclideanDistanceAlg = new EuclideanDistanceAlg(offlineScans,liveMagnitude, indoorParams);
                int index = euclideanDistanceAlg.compute(AlgorithmName.MAGNETIC_FP);
                Log.i("magn online manag","index " + index);
                CurrentGPSPosition currentGPSPositions = databaseManager.getAppDatabase().getCurrentGPSPositionsDAO().getCurrentGPSPositions();
                onlineScan = new OnlineScan(idScan,index,0,new Date(), currentGPSPositions.getLatitude() , currentGPSPositions.getLongitude() );
                return onlineScan;
            }

        } else {
            Toast.makeText(MyApp.getContext(),
                    "Non info in db",
                    Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public List<OfflineScan> getMagneticFingerPrintsFromDb(){
        Building building =  (Building) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.BUILDING);
        int idBuilding = building.getId();
        Algorithm algorithm =  (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        int idAlgorithm= algorithm.getId();
        Config config = (Config) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.CONFIG);
        int idConfig = config.getId();
        BuildingFloor buildingFloor = (BuildingFloor) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.FLOOR);
        int idFloor;
        try {

            if(buildingFloor ==  null){
                idFloor = -1;
            }else{
                idFloor = buildingFloor.getId();
            }
            /*if(buildingFloor == null){
                Log.i("magn online man","floor null");
                List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().
                        getScanSummaryByBuildingAlgorithm(idBuilding,idAlgorithm,idConfig,"offline");
                Log.i("magn online man","scans NULL: \n" + scanSummary.toString());

                List<Integer> ids = new ArrayList<>();
                for (ScanSummary s: scanSummary) {
                    ids.add(s.getId());
                }
                offlineScans = databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScansByIds(ids);
                Log.i("magn online man","offliscans id: \n" + ids.toString());

                Log.i("magn online man","offliscans: \n" + offlineScans.toString());
            }
            else{
                Log.i("magn online man","floor NON null");
                List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().
                        getScanSummaryByBuildingAlgorithm(idBuilding, buildingFloor.getId(),idAlgorithm,idConfig,"offline");
                Log.i("magn online man","scans NON NULL: \n" + scanSummary.toString());
            }*/

            List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().
                    getScanSummaryByBuildingAlgorithm(idBuilding,idFloor,idAlgorithm,idConfig,"offline");
            idScan = scanSummary.get(0).getId();
            Log.i("idScan", String.valueOf(idScan));
            offlineScans = databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScansById(idScan);

            return  offlineScans;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // get values for each axes X,Y,Z
            float magX = event.values[0];
            float magY = event.values[1];
            float magZ = event.values[2];
            this.magnitudeValue = Math.sqrt((magX * magX) + (magY * magY) + (magZ * magZ));
            Log.i("magn online man","magn value " + this.magnitudeValue);
            Log.i("sono qui","sono qui");
            getMagnitude = true;
            //todo inserisco in db live Measurements
            databaseManager.getAppDatabase().getLiveMeasurementsDAO().insert(
                    new LiveMeasurements(1,-1 , "magn_rss", 1, magnitudeValue)
            );
            // set value on the screen
            Toast.makeText(MyApp.getContext(), "scanning...", Toast.LENGTH_SHORT).show();
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
