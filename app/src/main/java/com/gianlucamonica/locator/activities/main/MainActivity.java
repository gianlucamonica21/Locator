package com.gianlucamonica.locator.activities.main;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.db.AppDatabase;

public class   MainActivity extends Activity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGPSActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, GPSActivity.class);
        startActivity(intent);
    }

    public void openWIFIActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, WIFIActivity.class);
        startActivity(intent);
    }

    public void setUpDB(){
        this.database = Room.databaseBuilder(this, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        String currentDBPath = getDatabasePath("db-contacts").getAbsolutePath();
        Log.i("dbPath:",currentDBPath);
        Stetho.initializeWithDefaults(this);
    }


}
