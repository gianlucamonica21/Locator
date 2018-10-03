package com.gianlucamonica.locator.myLocationManager.utils.db.algorithm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;

@Entity(tableName = "algorithm",indices = {@Index(value =
        {"name"}, unique = true)})
public class Algorithm {

    @PrimaryKey(autoGenerate = true)
    int id;
    @NonNull
    String name;
    @NonNull
    Boolean phases;

    public Algorithm(String name,Boolean phases){
        this.name = name;
        this.phases = phases;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
