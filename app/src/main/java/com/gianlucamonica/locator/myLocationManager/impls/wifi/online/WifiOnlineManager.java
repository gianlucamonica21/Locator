package com.gianlucamonica.locator.myLocationManager.impls.wifi.online;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.impls.onlinePhaseAlgs.EuclidDistanceMultipleAPs;
import com.gianlucamonica.locator.myLocationManager.impls.onlinePhaseAlgs.EuclideanDistanceAlg;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.utils.AP_RSS;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locator.myLocationManager.utils.db.currentGPSPosition.CurrentGPSPosition;
import com.gianlucamonica.locator.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class WifiOnlineManager {

    private EuclideanDistanceAlg euclideanDistanceAlg;
    private EuclidDistanceMultipleAPs euclidDistanceMultipleAPs;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;
    private DatabaseManager databaseManager;
    private Algorithm algorithm;
    private Building building;
    private BuildingFloor buildingFloor;
    private Config config;
    private WifiManager wifiManager;
    private int idWifiNetwork;
    private ArrayList<AP_RSS> ap_rsses;
    private OnlineScan onlineScan;
    private int idFloor;
    private MapView mapView;

    public WifiOnlineManager(ArrayList<IndoorParams> indoorParams){
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        ap_rsses = new ArrayList<>();
        this.indoorParams = indoorParams;
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        building = (Building) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.BUILDING);
        buildingFloor = (BuildingFloor) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.FLOOR);
        config = (Config) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.CONFIG);

        if(buildingFloor == null){
            idFloor = -1;
        }
        else{
            idFloor = buildingFloor.getId();
        }


        onlineScan  = null;
    }

    public OnlineScan locate(){

       WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if( wifiInfo != null) {
            Log.i("wifi online manager", "wom " + String.valueOf(building.getId() + " " + idFloor + " " + algorithm.getId() + " " + config.getId()));

            List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getMyDAO().
                    getOfflineScan(building.getId(),idFloor,algorithm.getId(),config.getId(),"offline");

            Log.i("wifi online man","offline scans : \n" + offlineScans.toString());
            if (offlineScans.size() > 0) {

                List<LiveMeasurements> liveMeasurements =
                        databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(2,"wifi_rss");
                ArrayList<AP_RSS> ap_rsses = new ArrayList<>();
                for(int i = 0; i < liveMeasurements.size(); i++){
                    int idAP = liveMeasurements.get(i).getIdAP();
                    int value = (int) liveMeasurements.get(i).getValue();
                    ap_rsses.add(new AP_RSS(idAP,value));
                }
                Log.i("ap_rss","ap_rss " + ap_rsses.toString());
                euclidDistanceMultipleAPs = new EuclidDistanceMultipleAPs(offlineScans,ap_rsses);
                int index = euclidDistanceMultipleAPs.compute();

                Toast.makeText(MyApp.getContext(), "scanning...", Toast.LENGTH_SHORT).show();

                CurrentGPSPosition currentGPSPositions = databaseManager.getAppDatabase().getCurrentGPSPositionsDAO().getCurrentGPSPositions();

                OnlineScan onlineScan = new OnlineScan(offlineScans.get(0).getIdScan(),index,0,new Date(),currentGPSPositions.getLatitude() , currentGPSPositions.getLongitude());
                return onlineScan;
            } else {
                Toast.makeText(MyApp.getContext(),
                        "Non ci sono informazioni in db",
                        Toast.LENGTH_SHORT).show();
            }
        }

        return  null;

    }


}
