package com.gianlucamonica.locator.activities.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.stetho.Stetho;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.db.AppDatabase;
import com.gianlucamonica.locator.utils.db.Contact;
import com.gianlucamonica.locator.utils.db.ContactDAO;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class   MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener listener;
    private Location mLocation;
    private Context mContext;
    private AppDatabase database;
    WifiManager wifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setUpDB();

        /**
         * getting WIFI rss and manage on click on UI
         */
        this.wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        ConstraintLayout root = (ConstraintLayout) findViewById(R.id.mainActivity);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity: ","you are clicking");
                int numberOfLevels = 5;
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
                wifiManager.getScanResults();
                Log.i("MainActivity level: ", String.valueOf(wifiManager.getScanResults()));
            }
        });
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

        ContactDAO contactDAO = database.getContactDAO();

        //Inserting a contact
        Contact contact = new Contact();
        contact.setFirstName("G");
        contact.setLastName("M");
        contact.setPhoneNumber("1");

        contactDAO.insert(contact);
        final List<Contact> contacts2 = contactDAO.getContacts();

        for(int i = 0; i < contacts2.size();i++){
            try {
                String name = contacts2.get(i).getFirstName();
                Log.i("name of contact",name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





}
