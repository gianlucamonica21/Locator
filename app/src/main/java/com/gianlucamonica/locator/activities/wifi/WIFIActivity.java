package com.gianlucamonica.locator.activities.wifi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.wifi.offlineActivity.OfflineActivity;
import com.gianlucamonica.locator.activities.wifi.onlineActivity.OnlineActivity;

public class WIFIActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
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
