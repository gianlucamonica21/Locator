package com.gianlucamonica.locator.model.impls.wifi.online;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locator.utils.db.DatabaseManager;
import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.FingerPrint;
import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.FingerPrintDAO;
import com.gianlucamonica.locator.model.impls.wifi.offline.MapView;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class WifiOnlineManager {

    private ArrayList<FingerPrint> fingerPrints;
    private Activity activity;
    private EuclideanDistanceAlg euclideanDistanceAlg;
    private DatabaseManager databaseManager;
    private MapView mapView;

    public WifiOnlineManager(Activity activity){
        this.activity = activity;
        this.fingerPrints = new ArrayList<>();
        databaseManager = new DatabaseManager(activity);
    }

    public void locate(){

        int rssiValue = wifiScan(); // getting live wifi rssi

        //todo impl multiple aps managing
        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if( wifiInfo != null) {

            List<FingerPrint> fingerPrintsDB = getFingerPrintsFromDb(wifiInfo.getSSID());
            if (fingerPrintsDB.size() > 0) {

                euclideanDistanceAlg = new EuclideanDistanceAlg(fingerPrintsDB, rssiValue);
                int index = euclideanDistanceAlg.compute(AlgorithmName.WIFI_RSS_FP);

                Toast.makeText(MyApp.getContext(),
                        "Sei nel riquadro " + fingerPrintsDB.get(index).getGridName(),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyApp.getContext(),
                        "Non ci sono informazioni in db",
                        Toast.LENGTH_SHORT).show();
            }
        }
        //mapView = new MapView(activity,fingerPrintsDB.get(index).getGridName());
        //activity.setContentView(mapView);

    }


    public int wifiScan(){

        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int rssiValue = wifiInfo.getRssi();

        Log.i("wifiInfo", String.valueOf(rssiValue));
        Toast.makeText(MyApp.getContext(), "Scanning live rss  " + rssiValue, Toast.LENGTH_SHORT).show();
        return rssiValue;
    }

    public List<FingerPrint> getFingerPrintsFromDb(String ssid){

        FingerPrintDAO fingerPrintDAO = databaseManager.getAppDatabase().getFingerPrintDAO();
        List<FingerPrint> fingerPrints = fingerPrintDAO.getFingerPrintWithAPSsid(ssid);
        return fingerPrints;
    }
}
