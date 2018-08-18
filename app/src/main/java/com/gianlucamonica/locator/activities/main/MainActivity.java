package com.gianlucamonica.locator.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;

public class   MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGPSActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, GPSActivity.class);
        startActivity(intent);
    }

    public void openWIFIActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, WIFIActivity.class);
        startActivity(intent);
    }


}
