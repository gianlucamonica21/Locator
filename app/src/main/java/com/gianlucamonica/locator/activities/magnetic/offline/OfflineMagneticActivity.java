package com.gianlucamonica.locator.activities.magnetic.offline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.model.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrintDAO;
import com.gianlucamonica.locator.utils.db.DatabaseManager;
import com.gianlucamonica.locator.model.impls.wifi.offline.MapView;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.MyApp;

public class OfflineMagneticActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(this);
        myLocationManager = MyApp.getMyLocationManagerInstance();

        setContentView(R.layout.activity_offline_magnetic);
        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

        // setting the map view
        MapView v = (MapView) myLocationManager.build(MapView.class);
        mLinearLayout.addView(v);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("redoscan","redoscan");
                Toast.makeText(MyApp.getContext(),
                        "Deleting entries",
                        Toast.LENGTH_SHORT).show();
                deleteFP();

                // refreshing the mapview
                MapView mapView = (MapView) myLocationManager.build(MapView.class);
                mLinearLayout.addView(mapView);
            }
        });
    }

    public void deleteFP(){
        MagneticFingerPrintDAO magneticFingerPrintDAO = databaseManager.getAppDatabase().getMagneticFingerPrintDAO();
        magneticFingerPrintDAO.deleteAll();
    }
}
