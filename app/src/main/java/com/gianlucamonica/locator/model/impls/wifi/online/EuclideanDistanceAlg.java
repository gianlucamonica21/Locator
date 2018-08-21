package com.gianlucamonica.locator.model.impls.wifi.online;

import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.FingerPrint;
import java.util.List;

public class EuclideanDistanceAlg {

    private List<FingerPrint> radioMap;
    private int scannedRssi;

    public EuclideanDistanceAlg(List<FingerPrint> radioMap, int scannedRssi){
        this.radioMap = radioMap;
        this.scannedRssi = scannedRssi;
    }

    public int compute(){

        for (int i = 0; i < radioMap.size(); i++) {
            int rssiTmp = radioMap.get(i).getRssi();
            radioMap.get(i).setRssi((int)
                    Math.sqrt(Math.pow((double) rssiTmp - scannedRssi,2))
            );
        }

        int index = 0;
        for (int i = 0; i < radioMap.size() - 1; i++) {
            int maxRssi = radioMap.get(i).getRssi();
            if(radioMap.get(i+1).getRssi() < maxRssi){
                //maxRssi = radioMap.get(i+1).getRssi();
                index = i+1;
            }
        }
        return index;
    }

}
