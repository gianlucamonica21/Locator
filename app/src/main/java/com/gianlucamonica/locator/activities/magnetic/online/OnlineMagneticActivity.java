package com.gianlucamonica.locator.activities.magnetic.online;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.map.MapView;

public class OnlineMagneticActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_magnetic);
        myLocationManager = MyApp.getMyLocationManagerInstance();
    }

    public OnlineScan locate(View view){
        OnlineScan computedLocation = myLocationManager.locate();
        if( computedLocation != null){
            Toast.makeText(this,"your position: " + computedLocation.toString(),Toast.LENGTH_SHORT).show();
            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

            // setting the map view
            //MapView mapView = new MapView(this,computedLocation.getGridName());
            //mLinearLayout.addView(mapView);
            return computedLocation;
        }
        return null;
    }
}
