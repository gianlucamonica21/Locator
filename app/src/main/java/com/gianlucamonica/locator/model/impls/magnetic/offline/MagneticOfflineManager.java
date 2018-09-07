package com.gianlucamonica.locator.model.impls.magnetic.offline;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gianlucamonica.locator.model.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.model.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrintDAO;
import com.gianlucamonica.locator.utils.db.DatabaseManager;
import com.gianlucamonica.locator.utils.map.MapView;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.map.Grid;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class MagneticOfflineManager implements SensorEventListener {

    private MapView mV; // map building
    private Activity activity;
    private SensorManager sensorManager; // magnetic sensor's stuffs
    private DatabaseManager databaseManager;

    private int scanNumber = 0; // m. field scan counter
    private ArrayList<Double> magnitudes;
    private ArrayList<String> zones;
    private ArrayList<MagneticFingerPrint> magneticFingerPrints;

    public MagneticOfflineManager(Activity activity){
        this.activity = activity;
        sensorManager = (SensorManager) MyApp.getContext().getSystemService(SENSOR_SERVICE);
        magnitudes = new ArrayList<>();
        zones = new ArrayList<>();
        magneticFingerPrints = new ArrayList<>();
        databaseManager = new DatabaseManager(activity);
    }

    public <T extends View> T build(Class<T> type){

        mV = new MapView(this.activity,null);
        mV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //todo get magnetic value
                collectMagneticFieldValueByUI(event);
                return false;
            }
        });

        return type.cast(mV);

    }

    public void collectMagneticFieldValueByUI(MotionEvent event){
        MagneticFingerPrintDAO magneticFingerPrintDAO = databaseManager.getAppDatabase().getMagneticFingerPrintDAO();

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
                    for (int i = 0; i < zones.size(); i++){
                        magneticFingerPrints.add(new MagneticFingerPrint(zones.get(i),magnitudes.get(i)));
                    }
                }
                Log.i("magnFParray",magneticFingerPrints.toString());
                // inserting in db
                for (int i = 0; i < magneticFingerPrints.size(); i++){
                    List<MagneticFingerPrint> result = magneticFingerPrintDAO.getFingerPrintWithGridName(
                            magneticFingerPrints.get(i).getGridName());

                    if(result.size() == 0){
                        Log.i("Scan finished","inserting in db");
                        magneticFingerPrintDAO.insert(magneticFingerPrints.get(i));
                    }
                }
            }else{
                for(int i = 0; i < rects.size(); i = i + 1){
                    float aX = ((rects.get(i).getA().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bX = ((rects.get(i).getB().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bY = ((rects.get(i).getB().getY()*mV.getScaleFactor())+ mV.getAdd());
                    float aY = ((rects.get(i).getA().getY()*mV.getScaleFactor())+ mV.getAdd());
                    String gridName = rects.get(i).getName();

                    if( x >= aX && x <= bX){
                        if( y <= bY && y >= aY){
                            //get magnetic field value
                            scanNumber = 0;
                            sensorManager.registerListener(this,
                                    sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                                    SensorManager.SENSOR_DELAY_NORMAL);
                            zones.add(rects.get(i).getName());

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
                double magnitude = Math.sqrt((magX * magX) + (magY * magY) + (magZ * magZ));
                scanNumber++;
                magnitudes.add(magnitude);
                // set value on the screen
                Toast.makeText(activity, "magnitude value " + magnitude, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
