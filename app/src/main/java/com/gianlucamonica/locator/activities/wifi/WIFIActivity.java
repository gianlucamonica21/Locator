package com.gianlucamonica.locator.activities.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.wifi.offlineActivity.OfflineActivity;
import com.gianlucamonica.locator.activities.wifi.onlineActivity.OnlineActivity;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.AP;
import com.gianlucamonica.locator.model.impls.wifi.db.AP.APDAO;
import com.gianlucamonica.locator.model.impls.wifi.db.DatabaseManager;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;

public class WIFIActivity extends AppCompatActivity {

    MyLocationManager myLocationManager;
    DatabaseManager databaseManager;
    Button btnOffline;
    Button btnOnline;
    String wifiInfoSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        btnOffline = (Button) findViewById(R.id.button5);
        btnOnline = (Button) findViewById(R.id.button4);
        TextView textView = (TextView) findViewById(R.id.textView2);

        myLocationManager = new MyLocationManager(AlgorithmName.WIFI_RSS_FP, this);
        MyApp.setMyLocationManagerInstance(myLocationManager);

        if(!myLocationManager.getMyPermissionsManager().isWIFIEnabled()){
            btnOffline.setEnabled(false);
            btnOnline.setEnabled(false);
            textView.setText("No connection");

        }

        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if( wifiInfo != null){

            if(wifiManager.isWifiEnabled()){
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mWifi.isConnected()) {

                    wifiInfoSSID = wifiInfo.getSSID();
                    textView.setText("You are connected to " + wifiInfoSSID);

                    databaseManager = new DatabaseManager(this);
                    APDAO apdao = databaseManager.getAppDatabase().getAPDAO();

                    AP ap = apdao.getAPWithSsid(wifiInfoSSID);
                    if (ap.getSsid() == null) {
                        Toast.makeText(this,
                                "For this connection you don't have an offline scan. Please do it before do online scan",
                                Toast.LENGTH_LONG).show();

                        btnOnline.setEnabled(false);
                    }
                }else{
                    textView.setText("No connection");
                    btnOffline.setEnabled(false);
                    btnOnline.setEnabled(false);
                }
            }
        }

    }


    public void openOfflineActivity(View view){
        Intent intent = new Intent(this, OfflineActivity.class);
        startActivity(intent);
    }

    public void openOnlineActivity(View view){
        Intent intent = new Intent(this, OnlineActivity.class);
        startActivity(intent);
    }



}
