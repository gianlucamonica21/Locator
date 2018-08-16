package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.offline;

import android.app.Activity;
import android.arch.persistence.room.Room;
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
import com.gianlucamonica.locator.utils.db.AP.AP;
import com.gianlucamonica.locator.utils.db.AP.APDAO;
import com.gianlucamonica.locator.utils.map.Coordinate;
import com.gianlucamonica.locator.utils.map.Grid;
import com.gianlucamonica.locator.utils.db.examples.AppDatabase;

import java.util.ArrayList;

public class WifiOfflineManager extends AppCompatActivity{

    private AppDatabase appDatabase;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    public Activity activity;
    public MapView mV;

    private AP ap;
    private ArrayList<FingerPrint> fingerPrints;

    public WifiOfflineManager(Activity activity){
        this.activity = activity;
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        ap = new AP();
        int id = 0;
        if( info != null){
            id = info.getNetworkId();
            String mac = info.getMacAddress();
            String ssid = info.getSSID();
            Log.i("mac addr", mac);
            Log.i("netw id", String.valueOf(id));
            Log.i("ssid", ssid);
            ap.setId(id);
            ap.setMac(mac);
            ap.setSsid(ssid);
        }
        setupDB();

        APDAO apdao = appDatabase.getAPDAO();
        if(apdao.getAPWithId(id)==null)
            apdao.insert(ap);
    }

    public <T extends View> T build(Class<T> type){

        fingerPrints = new ArrayList<>();

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
                                //todo scan wifi rss
                                wifiInfo = wifiManager.getConnectionInfo();
                                int rssiValue = wifiInfo.getRssi();
                                Log.i("wifiInfo", String.valueOf(rssiValue));
                                Toast.makeText(MyApp.getContext(), "Scanning in  " + rects.get(i).getName() + "  rss  " + rssiValue, Toast.LENGTH_SHORT).show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        //Toast.makeText(MyApp.getContext(), "Scan finished", Toast.LENGTH_SHORT).show();
                                    }
                                }, 1000);

                                //todo inserisco in db
                                fingerPrints.add(new FingerPrint(
                                        ap,
                                        new Grid(new Coordinate(rects.get(i).getA().getX(),
                                                rects.get(i).getB().getX()),
                                                new Coordinate(rects.get(i).getA().getY(),
                                                        rects.get(i).getB().getY()),
                                                rects.get(i).getName()),
                                        rssiValue));
                                for(int k = 0; k < fingerPrints.size(); k++)
                                    Log.i("fingerPrints", fingerPrints.get(k).toString());

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
        this.appDatabase = Room.databaseBuilder(this.activity, AppDatabase.class, "wifiAlg")
                .allowMainThreadQueries()//Allows room to do operation on main thread
                .fallbackToDestructiveMigration()
                .build();
        String currentDBPath = this.activity.getDatabasePath("wifiAlg").getAbsolutePath();
        Log.i("dbPath:",currentDBPath);
        Stetho.initializeWithDefaults(this.activity);
    }

}
