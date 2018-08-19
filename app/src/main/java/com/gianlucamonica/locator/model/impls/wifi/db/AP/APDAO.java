package com.gianlucamonica.locator.model.impls.wifi.db.AP;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

@Dao
public interface APDAO {
    @Insert
    public void insert(AP... aps);

    @Update
    public void update(AP... aps);

    @Delete
    public void delete(AP ap);

    @Query("SELECT * FROM ap")
    public List<AP> getAP();

    @Query("SELECT * FROM ap WHERE id = :id")
    public AP getAPWithId(int id);

    @Query("SELECT * FROM ap WHERE ssid = :ssid")
    public AP getAPWithSsid(String ssid);
}
