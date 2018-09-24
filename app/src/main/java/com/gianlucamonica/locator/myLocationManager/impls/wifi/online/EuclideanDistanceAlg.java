package com.gianlucamonica.locator.myLocationManager.impls.wifi.online;

import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;

import java.util.List;

public class EuclideanDistanceAlg {

    private List<WifiFingerPrint> wifiRadioMap;
    private List<MagneticFingerPrint> magneticRadioMap;
    private int scannedRssi;
    private double magnitude;

    public EuclideanDistanceAlg(List<WifiFingerPrint> wifiRadioMap, int scannedRssi){
        this.wifiRadioMap = wifiRadioMap;
        this.scannedRssi = scannedRssi;
    }

    public EuclideanDistanceAlg(List<MagneticFingerPrint> magneticRadioMap, double magnitude){
        this.magneticRadioMap = magneticRadioMap;
        this.magnitude = magnitude;
    }

    public int compute(AlgorithmName algorithmName){

        switch (algorithmName) {
            case WIFI_RSS_FP:
                return computeWifi();

            case MAGNETIC_FP:
                return computeMagn();
        }

        return -1;
    }

    public int computeWifi(){

        for (int i = 0; i < wifiRadioMap.size(); i++) {
            int rssiTmp = wifiRadioMap.get(i).getRssi();
            wifiRadioMap.get(i).setRssi((int)
                    Math.sqrt(Math.pow((double) rssiTmp - scannedRssi,2))
            );
        }

        int index = 0;
        int minRssi = wifiRadioMap.get(0).getRssi();
        for (int i = 0; i < wifiRadioMap.size() - 1; i++) {
            if( wifiRadioMap.get(i).getRssi() < minRssi){
                minRssi = wifiRadioMap.get(i+1).getRssi();
                index = i+1;
            }
        }
        return index;
    }

    public int computeMagn(){

        for (int i = 0; i < magneticRadioMap.size(); i++) {
            double magnTmp = magneticRadioMap.get(i).getMagnitude();
            magneticRadioMap.get(i).setMagnitude(
                    Math.sqrt(Math.pow((double) magnTmp - magnitude,2))
            );
        }

        int index = 0;
        double minMagn = magneticRadioMap.get(0).getMagnitude();
        for (int i = 0; i < magneticRadioMap.size() - 1; i++) {
            if( magneticRadioMap.get(i+1).getMagnitude() < minMagn ){
                minMagn = magneticRadioMap.get(i+1).getMagnitude();
                index = i + 1;
            }
        }
        return index;
    }

}
