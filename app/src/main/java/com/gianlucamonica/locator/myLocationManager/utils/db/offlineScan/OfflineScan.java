package com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;

@Entity(tableName = "offlineScan",
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
        onDelete = ForeignKey.CASCADE)}
        )
public class OfflineScan {

    @PrimaryKey(autoGenerate = true)

    int id;

    @NonNull
    int idBuilding;

    @NonNull
    int idAlgorithm;

    @NonNull
    int idGrid;

    @NonNull
    int gridSize;

    @NonNull
    double value;

    public OfflineScan(int idBuilding, int idAlgorithm, int idGrid, int gridSize,double value){
        this.idBuilding = idBuilding;
        this.idAlgorithm = idAlgorithm;
        this.idGrid = idGrid;
        this.gridSize = gridSize;
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
    public int getIdGrid() {
        return idGrid;
    }

    public void setIdGrid(@NonNull int idGrid) {
        this.idGrid = idGrid;
    }

    @NonNull
    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(@NonNull int gridSize) {
        this.gridSize = gridSize;
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
                ", idBuilding=" + idBuilding +
                ", idAlgorithm=" + idAlgorithm +
                ", idGrid=" + idGrid +
                ", gridSize=" + gridSize +
                ", value=" + value +
                '}';
    }
}
