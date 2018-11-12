package com.gianlucamonica.locator.myLocationManager.utils.db.currentGPSPosition;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.location.Location;

import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;

import java.util.List;

@Dao
public abstract class CurrentGPSPositionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CurrentGPSPosition... currentGPSPositions);

    @Update
    public void update(CurrentGPSPosition ... currentGPSPositions){};

    @Delete
    public void delete(CurrentGPSPosition ... currentGPSPositions){};

    @Query("DELETE FROM currentGPSPosition")
    public void deleteAll(){};

    @Query("SELECT * FROM currentGPSPosition")
    public abstract CurrentGPSPosition getCurrentGPSPositions();


}
