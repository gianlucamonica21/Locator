package com.gianlucamonica.locator.myLocationManager.impls.magnetic.online;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrintDAO;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.online.EuclideanDistanceAlg;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;

import java.util.ArrayList;
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



    public MagneticOnlineManager(Activity activity){
        this.activity = activity;
        databaseManager = new DatabaseManager(activity);
        magneticFingerPrints = new ArrayList<>();
        sensorManager = (SensorManager) MyApp.getContext().getSystemService(SENSOR_SERVICE);
    }

    public MagneticFingerPrint locate(){

        //getting m.f. value
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

        //getting map from db and do alg
        List<MagneticFingerPrint> magneticFingerPrintsDB = getMagneticFingerPrintsFromDb();
        if (magneticFingerPrintsDB.size() > 0) {

            euclideanDistanceAlg = new EuclideanDistanceAlg(magneticFingerPrintsDB, magnitudeValue);
            int index = euclideanDistanceAlg.compute(AlgorithmName.MAGNETIC_FP);

            return magneticFingerPrintsDB.get(index);

        } else {
            Toast.makeText(MyApp.getContext(),
                    "Non info in db",
                    Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public List<MagneticFingerPrint> getMagneticFingerPrintsFromDb(){
        MagneticFingerPrintDAO magneticFingerPrintDAO = databaseManager.getAppDatabase().getMagneticFingerPrintDAO();
        List<MagneticFingerPrint> magneticFingerPrints = magneticFingerPrintDAO.getMagneticFingerPrints();
        return magneticFingerPrints;
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
