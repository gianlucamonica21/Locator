package com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;

@Entity(tableName = "scanSummary",
        foreignKeys = {
        @ForeignKey(
                entity = Building.class,
                childColumns = "idBuilding",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Algorithm.class,
                childColumns = "idAlgorithm",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(
                entity = OnlineScan.class,
                childColumns = "idScan",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(
                entity = OfflineScan.class,
                childColumns = "idScan",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)}
)
public class ScanSummary {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idScan;

    @NonNull
    int idBuilding;

    @NonNull
    int idAlgorithm;

    @NonNull
    int gridSize;

    @NonNull
    String type;

    public ScanSummary(int idScan,int idBuilding, int idAlgorithm, int gridSize,String type){
        this.idScan = idScan;
        this.idBuilding = idBuilding;
        this.idAlgorithm = idAlgorithm;
        this.gridSize = gridSize;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(@NonNull int idBuilding) {
        this.idBuilding = idBuilding;
    }

    @NonNull
    public int getIdAlgorithm() {
        return idAlgorithm;
    }

    public void setIdAlgorithm(@NonNull int idAlgorithm) {
        this.idAlgorithm = idAlgorithm;
    }

    @NonNull
    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(@NonNull int gridSize) {
        this.gridSize = gridSize;
    }

    @NonNull
    public int getIdScan() {
        return idScan;
    }

    public void setIdScan(@NonNull int idScan) {
        this.idScan = idScan;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OnlineScan{" +
                "id=" + id +
                ", idscan=" + idScan +
                ", idBuilding=" + idBuilding +
                ", idAlgorithm=" + idAlgorithm +
                ", gridSize=" + gridSize +
                '}';
    }
}
