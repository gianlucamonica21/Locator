package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.mapBuilder;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
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
    private WifiInfo wifiInfo;
    public Activity activity;
    public MapView mV;



    public WIFIMapBuilder(Activity activity){
        this.activity = activity;
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
    }

    public <T extends View> T build(Class<T> type){

        setupDB();


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
                        float aX = ((rects.get(i).getA().getX()*mV.getScaleFactor())+ mV.getAdd());
                        float bX = ((rects.get(i).getB().getX()*mV.getScaleFactor())+ mV.getAdd());
                        float bY = ((rects.get(i).getB().getY()*mV.getScaleFactor())+ mV.getAdd());
                        float aY = ((rects.get(i).getA().getY()*mV.getScaleFactor())+ mV.getAdd());

                        if( x >= aX && x <= bX){
                            if( y <= bY && y >= aY){
                                Toast.makeText(MyApp.getContext(), "Scanning in  " + rects.get(i).getName(), Toast.LENGTH_SHORT).show();
                                //todo scan wifi rss
                                wifiInfo = wifiManager.getConnectionInfo();
                                int rssiValue = wifiInfo.getRssi();
                                Log.i("wifiInfo", String.valueOf(rssiValue));

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        //Toast.makeText(MyApp.getContext(), "Scan finished", Toast.LENGTH_SHORT).show();
                                    }
                                }, 1000);

                                //todo inserisco in db
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
