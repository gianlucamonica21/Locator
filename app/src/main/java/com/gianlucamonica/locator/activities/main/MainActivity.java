package com.gianlucamonica.locator.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.magnetic.MagneticActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.utils.AlgorithmName;
import com.gianlucamonica.locator.utils.MyApp;

import java.util.ArrayList;

public class   MainActivity extends Activity {

    private ListView algsListView;
    private ArrayList<String> algsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        algsListView = (ListView) findViewById(R.id._algsList);
        algsList = new ArrayList<String>();
        algsList.add(String.valueOf(AlgorithmName.GPS));
        algsList.add(String.valueOf(AlgorithmName.WIFI_RSS_FP));
        algsList.add(String.valueOf(AlgorithmName.MAGNETIC_FP));

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                algsList );

        algsListView.setAdapter(arrayAdapter);
        algsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clicked = arrayAdapter.getItem(position);
                openActivity(view,clicked);
            }

        });
    }

    public void openActivity(View view, String algoName){

        switch (algoName){
            case "GPS":
                Intent intentGPS = new Intent(MainActivity.this, GPSActivity.class);
                startActivity(intentGPS);
            break;
            case "WIFI_RSS_FP":
                Intent intentWIFI = new Intent(MainActivity.this, WIFIActivity.class);
                startActivity(intentWIFI);
            break;
            case "MAGNETIC_FP":
                Intent intentMagnetic = new Intent(MainActivity.this, MagneticActivity.class);
                startActivity(intentMagnetic);
            break;

        }
    }



}
