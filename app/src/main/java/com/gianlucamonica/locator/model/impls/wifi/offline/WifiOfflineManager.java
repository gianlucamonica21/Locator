package com.gianlucamonica.locator.model.impls.wifi.offline;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gianlucamonica.locator.model.impls.wifi.db.AP.AP;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.APDAO;
import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.WifiFingerPrintDAO;
import com.gianlucamonica.locator.utils.db.DatabaseManager;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.map.Grid;
import com.gianlucamonica.locator.utils.map.MapView;

import java.util.ArrayList;

public class WifiOfflineManager extends AppCompatActivity{

    private DatabaseManager databaseManager;

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;

    public Activity activity;

    public MapView mV;
    private AP ap;
    private ArrayList<WifiFingerPrint> wifiFingerPrints;

    public WifiOfflineManager(Activity activity){
        this.activity = activity;
        this.wifiFingerPrints = new ArrayList<>();
        scanAPs();
    }

    public <T extends View> T build(Class<T> type){

        mV = new MapView(this.activity,null);
        mV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                collectRssiByUI(event);
                return false;
            }
        });

        return type.cast(mV);

    }

    public void scanAPs(){
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        ap = new AP();
        int id = 0;
        //todo impl multiple aps managing
        if( wifiInfo != null){
            id = wifiInfo.getNetworkId();
            String mac = wifiInfo.getMacAddress();
            String ssid = wifiInfo.getSSID();
            Log.i("mac addr", mac);
            Log.i("netw id", String.valueOf(id));
            Log.i("ssid", ssid);
            ap.setId(id);
            ap.setMac(mac);
            ap.setSsid(ssid);
        }

        databaseManager = new DatabaseManager(activity);
        APDAO apdao = databaseManager.getAppDatabase().getAPDAO();
        if(apdao.getAPWithId(id)==null)
            apdao.insert(ap);

        WifiFingerPrintDAO wifiFingerPrintDAO = databaseManager.getAppDatabase().getFingerPrintDAO();
        wifiFingerPrintDAO.deleteByAPSsid(ap.getSsid());

    }

    public int wifiScan(String gridName){
        wifiInfo = wifiManager.getConnectionInfo();
        int rssiValue = wifiInfo.getRssi();
        Log.i("wifiInfo", String.valueOf(rssiValue));
        Toast.makeText(MyApp.getContext(), "Scanning in  " + gridName + "  rss  " + rssiValue, Toast.LENGTH_SHORT).show();
        return rssiValue;
    }

    public void buildRssiMap(int rssiValue, ArrayList<Grid> rects,int i){

        String gridName = rects.get(i).getName();
        wifiFingerPrints.add(new WifiFingerPrint(
                ap.getSsid(),
                rects.get(i).getName(),
                rssiValue));
        for(int k = 0; k < wifiFingerPrints.size(); k++)
            Log.i("wifiFingerPrints", wifiFingerPrints.get(k).toString());

        WifiFingerPrintDAO wifiFingerPrintDAO = databaseManager.getAppDatabase().getFingerPrintDAO();
        if(wifiFingerPrintDAO.getFingerPrintWithAPSsidAndGridName(ap.getSsid(),gridName)==null){
            wifiFingerPrintDAO.insert(new WifiFingerPrint(
                ap.getSsid(),
                rects.get(i).getName(),
                rssiValue));

        }

    }

    public void collectRssiByUI(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            Log.i("TOUCHED ", x + " " + y);

            ArrayList<Grid> rects = mV.getRects();

            if(rects.size() == 0){
                Toast.makeText(MyApp.getContext(),"scan is finished",Toast.LENGTH_SHORT).show();
                MyApp.getWifiOfflineManagerInstance().setWifiFingerPrints(wifiFingerPrints);
            }else{
                for(int i = 0; i < rects.size(); i = i + 1){
                    float aX = ((rects.get(i).getA().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bX = ((rects.get(i).getB().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bY = ((rects.get(i).getB().getY()*mV.getScaleFactor())+ mV.getAdd());
                    float aY = ((rects.get(i).getA().getY()*mV.getScaleFactor())+ mV.getAdd());
                    String gridName = rects.get(i).getName();

                    if( x >= aX && x <= bX){
                        if( y <= bY && y >= aY){
                            //scan wifi rss
                            int rssiValue = wifiScan(gridName);
                            //inserisco in db
                            buildRssiMap(rssiValue,rects,i);
                            rects.remove(i);
                        }
                    }
                }
            }
            mV.invalidate();
        }
    }

    public ArrayList<WifiFingerPrint> getWifiFingerPrints() {
        return wifiFingerPrints;
    }

    public void setWifiFingerPrints(ArrayList<WifiFingerPrint> wifiFingerPrints) {
        this.wifiFingerPrints = wifiFingerPrints;
    }
}
