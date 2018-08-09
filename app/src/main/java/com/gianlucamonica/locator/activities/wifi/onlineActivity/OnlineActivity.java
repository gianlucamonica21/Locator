package com.gianlucamonica.locator.activities.wifi.onlineActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gianlucamonica.locator.R;

public class OnlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
    }

    //todo scan wifi rss
    //todo confronto tramite algoritmo WIFI x con dati in DB e ritorno posizione
}
