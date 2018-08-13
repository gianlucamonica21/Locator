package com.gianlucamonica.locator.activities.main;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.utils.db.examples.AppDatabase;
import com.gianlucamonica.locator.utils.db.examples.Contact;
import com.gianlucamonica.locator.utils.db.examples.ContactDAO;

import java.util.List;

public class   MainActivity extends Activity {

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
