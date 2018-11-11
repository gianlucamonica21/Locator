package com.gianlucamonica.locator.myLocationManager.impls.onlinePhaseAlgs;

import android.os.Build;
import android.util.Log;

import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;

import java.util.ArrayList;
import java.util.List;

public class EuclideanDistanceAlg {

    private List<OfflineScan> offlineScans;
    private double magnitude;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;

    int buildingHeight = 0;
    int buildingWidth = 0;
    int size = 0;

    public EuclideanDistanceAlg(List<OfflineScan> offlineScans, double magnitude, ArrayList<IndoorParams> indoorParams){
        this.offlineScans = offlineScans;
        this.magnitude = magnitude;
        this.indoorParams = indoorParams;
        this.indoorParamsUtils = new IndoorParamsUtils();
    }

    public int compute(AlgorithmName algorithmName){

        switch (algorithmName) {
            case MAGNETIC_FP:
                return computeMagn();
        }

        return -1;
    }



    public int computeMagn(){

        Log.i("euclidean","offline scans before" + offlineScans.toString());
        for (int i = 0; i < offlineScans.size(); i++) {
            double magnTmp = offlineScans.get(i).getValue();
            Log.i("euclidean","static: "+String.valueOf(magnTmp));
            Log.i("euclidean","live: " + magnitude);
            offlineScans.get(i).setValue(
                    Math.pow((double) magnitude - magnTmp,2)
            );
        }

        Log.i("euclidean","offline scans after" + offlineScans.toString());

        int index = 0;
        double minMagn = offlineScans.get(0).getValue();
        for (int i = 0; i < offlineScans.size() - 1; i++) {
            if( offlineScans.get(i+1).getValue() < minMagn ){
                minMagn = offlineScans.get(i+1).getValue();
                index = i + 1;
            }
        }

        Log.i("euclidean","index estim pos " + index);
        Log.i("euclidean","gridname estim pos " + offlineScans.get(index).getIdGrid());
        return offlineScans.get(index).getIdGrid();
    }

}
