package com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

@Entity(tableName = "wifiFingerPrint",
        primaryKeys = {"apSsid","gridName"})
public class FingerPrint {

    @NonNull
    private String apSsid;
    @NonNull
    private String gridName;
    @NonNull
    private int rssi;

    @Ignore
    public FingerPrint(){}

    public FingerPrint(String apSsid, String gridName, int rssi){
        this.apSsid = apSsid;
        this.gridName = gridName;
        this.rssi = rssi;
    }

    @NonNull
    public String getApSsid() {
        return apSsid;
    }

    public void setApSsid(@NonNull String apSsid) {
        this.apSsid = apSsid;
    }

    @NonNull
    public String getGridName() {
        return gridName;
    }

    public void setGridName(@NonNull String gridName) {
        this.gridName = gridName;
    }

    @NonNull
    public int getRssi() {
        return rssi;
    }

    public void setRssi(@NonNull int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "FingerPrint{" +
                "apSsid='" + apSsid + '\'' +
                ", gridName='" + gridName + '\'' +
                ", rssi=" + rssi +
                '}';
    }
}
