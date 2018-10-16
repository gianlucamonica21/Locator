package com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;

@Entity(tableName = "onlineScan",
        foreignKeys = {
        @ForeignKey(
                entity = ScanSummary.class,
                childColumns = "idScan",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)}
)
public class OnlineScan {

    @PrimaryKey
    int id;

    @NonNull
    int idScan;

    @NonNull
    int idEstimateGrid;


    public OnlineScan(int idScan, int idEstimateGrid){
        this.idScan = idScan;
        this.idEstimateGrid = idEstimateGrid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getIdEstimateGrid() {
        return idEstimateGrid;
    }

    public void setIdEstimateGrid(@NonNull int idEstimateGrid) {
        this.idEstimateGrid = idEstimateGrid;
    }

    @Override
    public String toString() {
        return "OnlineScan{" +
                "id=" + id +
                ", idEstimateGrid=" + idEstimateGrid +
                '}';
    }
}
