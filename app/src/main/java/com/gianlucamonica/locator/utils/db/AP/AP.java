package com.gianlucamonica.locator.utils.db.AP;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.utils.map.Coordinate;

@Entity(tableName = "ap")
public class AP {

    @PrimaryKey
    @NonNull
    int id;
    @PrimaryKey
    @NonNull
    String mac;
    @NonNull
    String ssid;

    Coordinate c;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getMac() {
        return mac;
    }

    public void setMac(@NonNull String mac) {
        this.mac = mac;
    }

    @NonNull
    public String getSsid() {
        return ssid;
    }

    public void setSsid(@NonNull String ssid) {
        this.ssid = ssid;
    }

    public Coordinate getC() {
        return c;
    }

    public void setC(Coordinate c) {
        this.c = c;
    }
}
