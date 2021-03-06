package com.gianlucamonica.locator.myLocationManager.impls.wifiBar.online;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locator.myLocationManager.impls.onlinePhaseAlgs.EuclidDistanceMultipleAPs;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.utils.AP_RSS;
import com.gianlucamonica.locator.myLocationManager.impls.wifiBar.utils.AltitudeComputer;
import com.gianlucamonica.locator.myLocationManager.impls.wifiBar.utils.SeaAltitudeComputer;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.currentGPSPosition.CurrentGPSPosition;
import com.gianlucamonica.locator.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class WifiBarOnlineManager{

    private DatabaseManager databaseManager;
    private AltitudeComputer altitudeComputer;
    private SeaAltitudeComputer seaAltitudeComputer;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;
    private EuclidDistanceMultipleAPs euclidDistanceMultipleAPs;

    private Algorithm algorithm;
    private Building building;
    private Config config;

    boolean scanSummaryInserted = false;

    public WifiBarOnlineManager(ArrayList<IndoorParams> indoorParams) {
        databaseManager = new DatabaseManager();
        altitudeComputer = new AltitudeComputer();
        seaAltitudeComputer = new SeaAltitudeComputer();
        this.indoorParams = indoorParams;
        this.indoorParams = indoorParams;
        this.indoorParamsUtils = new IndoorParamsUtils();
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        building = (Building) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.BUILDING);
        config = (Config) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.CONFIG);
    }

    public OnlineScan locate(){

        try{

            List<LiveMeasurements> actAltitudeList = databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(3,"act_altitude");
            List<LiveMeasurements> seaAltitudeList = databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(3,"sea_altitude");
            List<LiveMeasurements> pressureValues = databaseManager.getAppDatabase().getLiveMeasurementsDAO().
                    getLiveMeasurements(3,"barometer");

            float actAltitude = (float) actAltitudeList.get(0).getValue();
            float seaAltitude = (float) seaAltitudeList.get(0).getValue();

            float avg = 0;
            float variance = 0;
            try {
                avg = computeAvg(pressureValues);
                variance = computeVariance(avg,pressureValues);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i("wifibar online","avg: " + avg);
            Log.i("wifibar online","variance: " + variance);

            if( variance > 0.5){

            }else{

            }

            Log.i("wifibar online","sea altitude:" + seaAltitude);
            Log.i("wifibar online","act altitude:" + actAltitude);
            double num = seaAltitude + actAltitude;
            Log.i("wifibar online", "numerator: " + num);
            int floor = (int) ((num) / 5);
            Log.i("wifibar online","piano stimato: " + floor);

            WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if( wifiInfo != null) {
                Log.i("wifi online manager", "wom " + String.valueOf(building.getId() + " " + algorithm.getId() + " " + config.getId()));

                List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getMyDAO().
                        getOfflineScanWifiBar(building.getId(),floor,algorithm.getId(), config.getId(),"offline");
                if (offlineScans.size() > 0) {

                    // get id floor from db
                    int idFloor = databaseManager.getAppDatabase().getBuildingFloorDAO().
                            getBuildingsFloorsByName(String.valueOf(floor),building.getId());

                    Log.i("wifi online manager","id floor " + idFloor + " associato a " + floor);

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
                    OnlineScan onlineScan = new OnlineScan(offlineScans.get(0).getIdScan(),index,0,new Date(),
                            currentGPSPositions.getLatitude(),currentGPSPositions.getLongitude() );

                    if(!scanSummaryInserted){
                        databaseManager.getAppDatabase().getScanSummaryDAO().insert(
                                new ScanSummary(building.getId(),idFloor,algorithm.getId(),config.getId(),-1,"online")
                        );

                        scanSummaryInserted = true;
                    }

                    return onlineScan;
                } else {
                    Toast.makeText(MyApp.getContext(),
                            "Non ci sono informazioni in db",
                            Toast.LENGTH_SHORT).show();
                }
            }

            return  null;

        }catch (Exception e){

        }


        // pesco valori RSS del piano @floor calcolato
        return null;
    }

    public float computeAvg(List<LiveMeasurements> pressureValues) throws Exception{

        float sum = 0;
        for (int i = 0; i < pressureValues.size(); i++)
            sum += pressureValues.get(i).getValue();

        return sum / pressureValues.size();
    }

    public float computeVariance(float avg,List<LiveMeasurements> pressureValues) throws Exception{

        float sum = 0;
        for (int i = 0; i < pressureValues.size(); i++)
            sum += Math.pow(pressureValues.get(i).getValue(),2) - Math.pow(avg,2);

        return sum / pressureValues.size();
    }

}
