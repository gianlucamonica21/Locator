package com.gianlucamonica.locator.activities.magnetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.magnetic.offline.OfflineMagneticActivity;
import com.gianlucamonica.locator.activities.magnetic.online.OnlineMagneticActivity;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;

public class MagneticActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic);

        myLocationManager = new MyLocationManager(AlgorithmName.MAGNETIC_FP, this);
        MyApp.setMyLocationManagerInstance(myLocationManager);

    }

    public void openOfflineActivity(View v){
        Intent intent = new Intent(this, OfflineMagneticActivity.class);
        startActivity(intent);
    }

    public void openOnlineActivity(View v){
        Intent intent = new Intent(this, OnlineMagneticActivity.class);
        startActivity(intent);
    }
}
