package com.gianlucamonica.locator.myLocationManager.utils.db.algConfig;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Dao
public abstract class ConfigDAO {

    @Insert
    public abstract void insert(Config... configs);

    @Update
    public void update(Config ... configs){};

    @Delete
    public void delete(Config ... configs){};

    @Query("DELETE FROM config")
    public void deleteAll(){};

    @Query("SELECT * FROM config")
    public abstract List<Config> getAllConfigs();

    @Query("SELECT * FROM config WHERE idAlgorithm =:idAlgorithm")
    public abstract List<Config> getConfigByIdAlgorithm(int idAlgorithm);

    @Query("SELECT * FROM config WHERE idAlgorithm =:idAlgorithm AND id = :id")
    public abstract List<Config> getConfigByIdAlgorithm(int idAlgorithm,int id);

}
