package com.gianlucamonica.locator.utils.db.examples;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locator.utils.db.AP.AP;
import com.gianlucamonica.locator.utils.db.AP.APDAO;

@Database(entities = {AP.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract APDAO getAPDAO();

}