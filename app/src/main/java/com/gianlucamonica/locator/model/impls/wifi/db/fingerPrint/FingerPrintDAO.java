package com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db.AP.AP;

import java.util.List;

@Dao
public interface FingerPrintDAO {
    @Insert
    public void insert(FingerPrint ... fingerPrints);

    @Update
    public void update(FingerPrint ... fingerPrints);

    @Delete
    public void delete(FingerPrint ... fingerPrints);

    @Query("SELECT * FROM fingerPrint")
    public List<FingerPrint> getFingerPrints();

    @Query("SELECT * FROM fingerPrint WHERE apSsid = :apSsid")
    public FingerPrint getFingerPrintWithAPSsid(String apSsid);
}
