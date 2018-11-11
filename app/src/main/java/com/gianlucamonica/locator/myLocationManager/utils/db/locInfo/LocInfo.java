package com.gianlucamonica.locator.myLocationManager.utils.db.locInfo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locator.myLocationManager.utils.db.utils.DateConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "locInfo")
@TypeConverters(DateConverter.class)
public class LocInfo {

    @PrimaryKey
    int id;

    @NonNull
    Boolean indoorLoc;

    public LocInfo(@NonNull Boolean indoorLoc) {
        this.indoorLoc = indoorLoc;
    }

    @NonNull
    public Boolean getIndoorLoc() {
        return indoorLoc;
    }

    public void setIndoorLoc(@NonNull Boolean indoorLoc) {
        this.indoorLoc = indoorLoc;
    }

    @Override
    public String toString() {
        return "LocInfo{" +
                "indoorLoc=" + indoorLoc +
                '}';
    }
}
