package com.gianlucamonica.locator.myLocationManager.impls.wifiBar.utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;

import static android.content.Context.SENSOR_SERVICE;

public class AltitudeComputer implements SensorEventListener {

    private DatabaseManager databaseManager;
    private SensorManager sensorManager; // barometer sensor's stuffs

    private int numberOfMeasurements = 0;
    private int threshold = 5;

    public AltitudeComputer() {
        databaseManager = new DatabaseManager();

        sensorManager = (SensorManager) MyApp.getContext().getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            numberOfMeasurements++;
            float[] values = event.values;
            float livePressure = values[0];
            Log.i("WifiBarOnline","pressure: " + livePressure);
            if(numberOfMeasurements <= threshold){

                float liveAltitude = sensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE,livePressure);
                Log.i("WifiBarOnline","altitude: " + liveAltitude);
                double selfMadeAltitude = (float) (44300 * ( 1 -Math.pow( (  livePressure / SensorManager.PRESSURE_STANDARD_ATMOSPHERE ), 1/5.255)));

                selfMadeAltitude =  ( ( ( ( Math.pow(SensorManager.PRESSURE_STANDARD_ATMOSPHERE /
                                        livePressure,(1/5.257)) -1)
                                        * ( 20 + 273.15)) ) / 0.0065);

                Log.i("WifiBarOnline","computed altitude: " + selfMadeAltitude);

                databaseManager.getAppDatabase().getLiveMeasurementsDAO().insert(
                        new LiveMeasurements(3,-1 , "act_altitude", numberOfMeasurements , selfMadeAltitude)
                );

            }

            //sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
