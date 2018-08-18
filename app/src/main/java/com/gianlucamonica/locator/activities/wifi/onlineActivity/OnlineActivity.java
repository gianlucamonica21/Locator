package com.gianlucamonica.locator.activities.wifi.onlineActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.MyApp;

public class OnlineActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        myLocationManager = MyApp.getMyLocationManagerInstance();

    }

    public void locate(View view){
        myLocationManager.locate();
    }

    //todo scan wifi rss
    //todo confronto tramite algoritmo WIFI x con dati in DB e ritorno posizione
}
