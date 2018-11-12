package com.gianlucamonica.locator.myLocationManager.utils.db.currentGPSPosition;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import javax.annotation.Nonnegative;

@Entity(tableName = "currentGPSPosition")
public class CurrentGPSPosition {

    @PrimaryKey
    int id;

    @NonNull
    double latitude;

    @NonNull
    double longitude;

    public CurrentGPSPosition(@NonNull double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CurrentGPSPosition{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
