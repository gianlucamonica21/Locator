package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.mapBuilder;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.map.Grid;
import com.gianlucamonica.locator.utils.db.examples.AppDatabase;

import java.util.ArrayList;

public class WIFIMapBuilder extends AppCompatActivity{

    private AppDatabase appDatabase;
    private WifiManager wifiManager;
    public Activity activity;
    public MapView mV;


    public WIFIMapBuilder(Activity activity){
        this.activity = activity;
    }

    public  <T extends View> T build(Class<T> type){
        setupDB();

        this.wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mV = new MapView(this.activity);
        mV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    float x = event.getX();
                    float y = event.getY();
                    Log.i("TOUCHED ", x + " " + y);

                    ArrayList<Grid> rects = mV.getRects();
                    for(int i = 0; i < rects.size(); i = i + 1){
                        if( x >= ((rects.get(i).getA().getX()*mV.getScaleFactor())+ mV.getAdd()) && x <= ((rects.get(i).getB().getX()*mV.getScaleFactor())+ mV.getAdd())){
                            if( y <= ((rects.get(i).getB().getY()*mV.getScaleFactor())+ mV.getAdd()) && y >= ((rects.get(i).getA().getY()*mV.getScaleFactor())+ mV.getAdd())  ){
                                Toast.makeText(MyApp.getContext(), "Scanning in  " + rects.get(i).getName(), Toast.LENGTH_SHORT).show();
                                //todo scan wifi rss
                                /*int numberOfLevels = 5;
                                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
                                wifiManager.getScanResults();
                                Log.i("MainActivity level: ", String.valueOf(wifiManager.getScanResults()));*/
                                //todo inserisco in db
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Toast.makeText(MyApp.getContext(), "Scan finished", Toast.LENGTH_SHORT).show();
                                    }
                                }, 1000);
                            }
                        }
                    }
                }
                return false;
            }
        });

        return type.cast(mV);

    }


    public void setupDB(){
        //setting db
        this.appDatabase = Room.databaseBuilder(this.activity, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        String currentDBPath = this.activity.getDatabasePath("db-contacts").getAbsolutePath();
        Log.i("dbPath:",currentDBPath);
        Stetho.initializeWithDefaults(this.activity);
    }

}
