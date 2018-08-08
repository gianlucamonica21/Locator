package com.gianlucamonica.locator.activities.wifi;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.wifi.mapBuilder.MapView;
import com.gianlucamonica.locator.activities.wifi.mapBuilder.WIFIMapBuilder;
import com.gianlucamonica.locator.model.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.db.AppDatabase;
import com.gianlucamonica.locator.utils.db.ContactDAO;
import com.gianlucamonica.locator.utils.db.Lettera;
import com.gianlucamonica.locator.utils.db.ProvaLettereDAO;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WIFIActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_wifi);
        //setContentView(R.layout.fragment_offline_stage);

        myLocationManager = new MyLocationManager(AlgorithmName.WIFI, this);
        MapView v = (MapView) myLocationManager.build(MapView.class);
        setContentView(v);

    }

    public void openOfflineFragment(View view){
        setContentView(R.layout.fragment_offline_stage);
    }


}
