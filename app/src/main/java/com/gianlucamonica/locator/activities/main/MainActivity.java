package com.gianlucamonica.locator.activities.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.magnetic.MagneticActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.fragments.AlgorithmFragment;
import com.gianlucamonica.locator.fragments.BuildingFragment;
import com.gianlucamonica.locator.fragments.ButtonsFragment;
import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BuildingFragment.OnFragmentInteractionListener,
        AlgorithmFragment.OnFragmentInteractionListener,
        ButtonsFragment.OnFragmentInteractionListener {

    private ListView algsListView;
    private ArrayList<String> algsList;
    private LocationMiddleware locationMiddleware;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // adding building and algorithm fragments
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.algorithmLayout, new AlgorithmFragment(), new AlgorithmFragment().getTag());
        ft.replace(R.id.buildingLayout, new BuildingFragment(), new BuildingFragment().getTag());
        ft.replace(R.id.buttonsLayout, new ButtonsFragment(), new ButtonsFragment().getTag());
        ft.addToBackStack(null);
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
