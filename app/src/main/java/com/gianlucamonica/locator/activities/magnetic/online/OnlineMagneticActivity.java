package com.gianlucamonica.locator.activities.magnetic.online;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.model.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.map.MapView;

public class OnlineMagneticActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_magnetic);
        myLocationManager = MyApp.getMyLocationManagerInstance();
    }

    public void locate(View view){
        MagneticFingerPrint computedLocation = myLocationManager.locate();
        if( computedLocation != null){
            Toast.makeText(this,"your position: " + computedLocation.toString(),Toast.LENGTH_SHORT).show();
            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

            // setting the map view
            MapView mapView = new MapView(this,computedLocation.getGridName());
            mLinearLayout.addView(mapView);
        }

    }
}
