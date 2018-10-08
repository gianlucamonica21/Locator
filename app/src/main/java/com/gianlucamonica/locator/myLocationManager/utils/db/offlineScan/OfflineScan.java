package com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;

@Entity(tableName = "offlineScan",
        foreignKeys = {
        @ForeignKey(
            entity = ScanSummary.class,
            childColumns = "id",
            parentColumns = "id",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)}
        )
public class OfflineScan {

    @PrimaryKey
    int id;

    @NonNull
    int idGrid;

    @NonNull
    double value;

    public OfflineScan(int id, int idGrid, double value){
        this.id = id;
        this.idGrid = idGrid;
        this.value = value;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getIdGrid() {
        return idGrid;
    }

    public void setIdGrid(@NonNull int idGrid) {
        this.idGrid = idGrid;
    }


    @NonNull
    public double getValue() {
        return value;
    }

    public void setValue(@NonNull double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OfflineScan{" +
                "id=" + id +
                ", idGrid=" + idGrid +
                ", value=" + value +
                '}';
    }
}
