package com.gianlucamonica.locator.utils.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProvaLettereDAO {
    @Insert
    public void insert(Lettera... letteras);

    @Update
    public void update(Lettera... letteras);

    @Delete
    public void delete(Lettera lettera);

    @Query("SELECT * FROM lettera")
    public List<Lettera> getLetteras();

    @Query("SELECT * FROM lettera WHERE name = :lettera")
    public Lettera getLetteraWithName(String lettera);
}
