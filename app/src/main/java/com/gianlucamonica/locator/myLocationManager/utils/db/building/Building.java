package com.gianlucamonica.locator.myLocationManager.utils.db.building;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "building",primaryKeys = {"id"})
public class Building {
    @NonNull
    int id;
    @NonNull
    String name;
    @NonNull
    int height;
    @NonNull
    int widht;

    double SOCoo;
    double NECoo;

    public Building(int id, String name, int height, int widht, double SOCoo, double NECoo){
        this.id = id;
        this.name = name;
        this.height = height;
        this.widht = widht;
        this.SOCoo = SOCoo;
        this.NECoo = NECoo;
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

    public double getSOCoo() {
        return SOCoo;
    }

    public void setSOCoo(double SOCoo) {
        this.SOCoo = SOCoo;
    }

    public double getNECoo() {
        return NECoo;
    }

    public void setNECoo(double NECoo) {
        this.NECoo = NECoo;
    }

    @Override
    public String
    toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", widht=" + widht +
                ", SOCoo=" + SOCoo +
                ", NECoo=" + NECoo +
                '}';
    }
}
