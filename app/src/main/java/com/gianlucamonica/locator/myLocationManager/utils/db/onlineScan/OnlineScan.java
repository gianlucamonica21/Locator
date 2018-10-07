package com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;

@Entity(tableName = "onlineScan",
        foreignKeys = {@ForeignKey(
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
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value =
                {"idBuilding","idAlgorithm","gridSize"}, unique = true)}
)
public class OnlineScan {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idBuilding;

    @NonNull
    int idAlgorithm;

    @NonNull
    int idEstimateGrid;

    @NonNull
    int gridSize;

    public OnlineScan(int idBuilding, int idAlgorithm, int idEstimateGrid, int gridSize){
        this.idBuilding = idBuilding;
        this.idAlgorithm = idAlgorithm;
        this.idEstimateGrid = idEstimateGrid;
        this.gridSize = gridSize;
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
    public int getIdEstimateGrid() {
        return idEstimateGrid;
    }

    public void setIdEstimateGrid(@NonNull int idEstimateGrid) {
        this.idEstimateGrid = idEstimateGrid;
    }

    @NonNull
    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(@NonNull int gridSize) {
        this.gridSize = gridSize;
    }

    @Override
    public String toString() {
        return "OnlineScan{" +
                "id=" + id +
                ", idBuilding=" + idBuilding +
                ", idAlgorithm=" + idAlgorithm +
                ", idEstimateGrid=" + idEstimateGrid +
                ", gridSize=" + gridSize +
                '}';
    }
}
