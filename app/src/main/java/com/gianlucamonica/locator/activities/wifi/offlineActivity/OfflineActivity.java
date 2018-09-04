package com.gianlucamonica.locator.activities.wifi.offlineActivity;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.utils.db.DatabaseManager;
import com.gianlucamonica.locator.model.impls.wifi.db.fingerPrint.FingerPrintDAO;
import com.gianlucamonica.locator.model.impls.wifi.offline.MapView;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.MyApp;

public class OfflineActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLocationManager = MyApp.getMyLocationManagerInstance();
        databaseManager = new DatabaseManager(this);

        setContentView(R.layout.activity_offline);
        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.infoText);

        // setting the map view
        MapView v = (MapView) myLocationManager.build(MapView.class);
        mLinearLayout.addView(v);

        Button button = (Button) findViewById(R.id.button7);
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
        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if(wifiInfo != null){
            FingerPrintDAO fingerPrintDAO = databaseManager.getAppDatabase().getFingerPrintDAO();
            fingerPrintDAO.deleteByAPSsid(wifiInfo.getSSID());
        }
    }

}
