package com.gianlucamonica.locator.activities.wifi.onlineActivity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.AP;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.APDAO;
import com.gianlucamonica.locator.model.impls.wifi.db.DatabaseManager;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.MyApp;

public class OnlineActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        myLocationManager = MyApp.getMyLocationManagerInstance();
    }

    public void locate(View view){
        myLocationManager.locate();
    }
}
