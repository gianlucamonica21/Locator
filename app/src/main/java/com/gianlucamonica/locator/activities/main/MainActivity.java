package com.gianlucamonica.locator.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.magnetic.MagneticActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.fragments.AlgChooseFragment;
import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;

import java.util.ArrayList;

public class   MainActivity extends AppCompatActivity implements AlgChooseFragment.OnFragmentInteractionListener {

    private ListView algsListView;
    private ArrayList<String> algsList;
    private LocationMiddleware locationMiddleware;

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

        // commentato per prova di utilizzo location middleware
        /*algsListView.setAdapter(arrayAdapter);
        algsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clicked = arrayAdapter.getItem(position);
                openActivity(view,clicked);
            }

        });*/

        // adding alg choose fragment

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.your_placeholder, new AlgChooseFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    public void init(View view){
        locationMiddleware = new LocationMiddleware(this);
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
