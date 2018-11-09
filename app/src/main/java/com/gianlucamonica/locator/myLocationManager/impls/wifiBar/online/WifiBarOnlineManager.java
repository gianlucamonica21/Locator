package com.gianlucamonica.locator.myLocationManager.impls.wifiBar.online;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.gianlucamonica.locator.myLocationManager.impls.wifiBar.utils.AltitudeComputer;
import com.gianlucamonica.locator.myLocationManager.impls.wifiBar.utils.SeaAltitudeComputer;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class WifiBarOnlineManager{

    private DatabaseManager databaseManager;
    private AltitudeComputer altitudeComputer;
    private SeaAltitudeComputer seaAltitudeComputer;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;


    public WifiBarOnlineManager(ArrayList<IndoorParams> indoorParams) {
        databaseManager = new DatabaseManager();
        altitudeComputer = new AltitudeComputer();
        seaAltitudeComputer = new SeaAltitudeComputer();
        this.indoorParams = indoorParams;
        this.indoorParams = indoorParams;
        this.indoorParamsUtils = new IndoorParamsUtils();
    }

    public OnlineScan locate(){

        List<LiveMeasurements> actAltitudeList = databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(3,"act_altitude");
        List<LiveMeasurements> seaAltitudeList = databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(3,"sea_altitude");

        float actAltitude = (float) actAltitudeList.get(0).getValue();
        float seaAltitude = (float) seaAltitudeList.get(0).getValue();

        int floor = (int) ((seaAltitude - actAltitude) / 3.5);
        Log.i("wifibar online","piano stimato: " + floor);
        return null;
    }

}
