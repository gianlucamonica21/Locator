package com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.db.utils.DateConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "offlineScan",
        indices = {@Index(value = {"idScan", "idGrid","idWifiAP"},
                unique = true)}, // vincoli di unicità
        foreignKeys = {
        @ForeignKey( // chiave esterna
            entity = ScanSummary.class,
            childColumns = "idScan",
            parentColumns = "id",
            onUpdate = CASCADE,
            onDelete = CASCADE)}
        )
@TypeConverters(DateConverter.class)
public class OfflineScan {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idScan;

    @NonNull
    int idGrid;

    @NonNull
    int idWifiAP;

    @NonNull
    double value;

    @NonNull
    Date timeStamp;

    @NonNull
    double latitude;

    @NonNull
    double longitude;


    public OfflineScan(@NonNull int idScan, @NonNull int idGrid, int idWifiAP, @NonNull double value, Date timeStamp, double latitude, double longitude) {
        this.idScan = idScan;
        this.idGrid = idGrid;
        this.idWifiAP = idWifiAP;
        this.value = value;
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getIdScan() {
        return idScan;
    }

    public void setIdScan(@NonNull int idScan) {
        this.idScan = idScan;
    }

    @NonNull
    public int getIdGrid() {
        return idGrid;
    }

    public void setIdGrid(@NonNull int idGrid) {
        this.idGrid = idGrid;
    }

    @NonNull
    public int getIdWifiAP() {
        return idWifiAP;
    }

    public void setIdWifiAP(@NonNull int idWifiAP) {
        this.idWifiAP = idWifiAP;
    }

    @NonNull
    public double getValue() {
        return value;
    }

    public void setValue(@NonNull double value) {
        this.value = value;
    }

    @NonNull
    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@NonNull Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @NonNull
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "OfflineScan{" +
                "id=" + id +
                ", idScan=" + idScan +
                ", idGrid=" + idGrid +
                ", idWifiAP=" + idWifiAP +
                ", value=" + value +
                ", timeStamp=" + timeStamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
