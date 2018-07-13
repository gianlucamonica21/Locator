package com.gianlucamonica.locator.activities.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;

public class MainActivity extends AppCompatActivity {

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
}
