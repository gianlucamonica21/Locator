package com.gianlucamonica.locator.myLocationManager.impls.wifi.online;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.impls.EuclideanDistanceAlg;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class WifiOnlineManager {

    private ArrayList<WifiFingerPrint> wifiFingerPrints;
    private Activity activity;
    private EuclideanDistanceAlg euclideanDistanceAlg;
    private DatabaseManager databaseManager;
    private MapView mapView;

    public WifiOnlineManager(Activity activity){
        this.activity = activity;
        this.wifiFingerPrints = new ArrayList<>();
        databaseManager = new DatabaseManager(activity);
    }

    public WifiFingerPrint locate(){

        int rssiValue = wifiScan(); // getting live wifi rssi

        //todo impl multiple aps managing
        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if( wifiInfo != null) {

            List<WifiFingerPrint> wifiFingerPrintsDB = getFingerPrintsFromDb(wifiInfo.getSSID());
            if (wifiFingerPrintsDB.size() > 0) {

                euclideanDistanceAlg = new EuclideanDistanceAlg(wifiFingerPrintsDB, rssiValue);
                int index = euclideanDistanceAlg.compute(AlgorithmName.WIFI_RSS_FP);

                Toast.makeText(MyApp.getContext(),
                        "Sei nel riquadro " + wifiFingerPrintsDB.get(index).getGridName(),
                        Toast.LENGTH_SHORT).show();

                return  wifiFingerPrintsDB.get(index);
            } else {
                Toast.makeText(MyApp.getContext(),
                        "Non ci sono informazioni in db",
                        Toast.LENGTH_SHORT).show();
            }
        }

        return  null;

    }


    public int wifiScan(){
        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int rssiValue = wifiInfo.getRssi();

        Log.i("wifiInfo", String.valueOf(rssiValue));
        Toast.makeText(MyApp.getContext(), "Scanning live rss  " + rssiValue, Toast.LENGTH_SHORT).show();
        return rssiValue;
    }

    public List<WifiFingerPrint> getFingerPrintsFromDb(String ssid){
        /*WifiFingerPrintDAO wifiFingerPrintDAO = databaseManager.getAppDatabase().getFingerPrintDAO();
        List<WifiFingerPrint> wifiFingerPrints = wifiFingerPrintDAO.getFingerPrintWithAPSsid(ssid);
        return wifiFingerPrints;*/
        return null;
    }
}
