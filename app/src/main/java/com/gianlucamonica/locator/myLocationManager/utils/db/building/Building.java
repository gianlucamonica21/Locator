package com.gianlucamonica.locator.myLocationManager.utils.db.building;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "building",indices = {@Index(value =
        {"name"}, unique = true)})
public class Building implements Serializable{

    @PrimaryKey(autoGenerate = true)
    int id;
    @NonNull
    String name;
    @NonNull
    int height;
    @NonNull
    int widht;
    @NonNull
    double SWLat;
    @NonNull
    double SWLng;
    @NonNull
    double NELat;
    @NonNull
    double NELng;


    public Building(String name, int height, int widht, double SWLat, double SWLng, double NELat, double NELng){
        this.name = name;
        this.height = height;
        this.widht = widht;
        this.SWLat = SWLat;
        this.SWLng = SWLng;
        this.NELat = NELat;
        this.NELng = NELng;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public int getHeight() {
        return height;
    }

    public void setHeight(@NonNull int height) {
        this.height = height;
    }

    @NonNull
    public int getWidht() {
        return widht;
    }

    public void setWidht(@NonNull int widht) {
        this.widht = widht;
    }

    @NonNull
    public double getSWLat() {
        return SWLat;
    }

    public void setSWLat(@NonNull double SWLat) {
        this.SWLat = SWLat;
    }

    @NonNull
    public double getSWLng() {
        return SWLng;
    }

    public void setSWLng(@NonNull double SWLng) {
        this.SWLng = SWLng;
    }

    @NonNull
    public double getNELat() {
        return NELat;
    }

    public void setNELat(@NonNull double NELat) {
        this.NELat = NELat;
    }

    @NonNull
    public double getNELng() {
        return NELng;
    }

    public void setNELng(@NonNull double NELng) {
        this.NELng = NELng;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", widht=" + widht +
                ", SWLat=" + SWLat +
                ", SWLng=" + SWLng +
                ", NELat=" + NELat +
                ", NELng=" + NELng +
                '}';
    }
}
