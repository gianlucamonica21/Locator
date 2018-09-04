package com.gianlucamonica.locator.activities.magnetic.online;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.MyApp;

public class OnlineMagneticActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_magnetic);
        myLocationManager = MyApp.getMyLocationManagerInstance();
    }

    public void locate(View view){
        myLocationManager.locate();
    }
}
