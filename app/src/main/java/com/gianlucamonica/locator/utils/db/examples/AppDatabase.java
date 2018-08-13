package com.gianlucamonica.locator.utils.db.examples;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Contact.class,Lettera.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
    public abstract ProvaLettereDAO getLetteraDAO();

}