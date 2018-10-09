package com.gianlucamonica.locator.activities.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.gps.GPSActivity;
import com.gianlucamonica.locator.activities.magnetic.MagneticActivity;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.fragments.AlgorithmFragment;
import com.gianlucamonica.locator.fragments.BuildingFragment;
import com.gianlucamonica.locator.fragments.ButtonsFragment;
import com.gianlucamonica.locator.fragments.ParamFragment;
import com.gianlucamonica.locator.fragments.ScanFragment;
import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BuildingFragment.OnFragmentInteractionListener,
        AlgorithmFragment.OnFragmentInteractionListener,
        ButtonsFragment.OnFragmentInteractionListener,
        ParamFragment.OnFragmentInteractionListener,
        ScanFragment.OnFragmentInteractionListener{

    private LocationMiddleware locationMiddleware;
    private Algorithm chosenAlgorithm;
    private Building chosenBuilding;
    private int chosenSize;

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
        ft.replace(R.id.paramLayout, new ParamFragment(), new ParamFragment().getTag());
        ft.replace(R.id.scanLayout, new ScanFragment(), new ScanFragment().getTag());
        ft.addToBackStack(null);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        DatabaseManager databaseManager= new DatabaseManager(this);
        // inserting algorithms
        //databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(AlgorithmName.MAGNETIC_FP),true));
        //databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(AlgorithmName.WIFI_RSS_FP),true));
        // inserting onlineScan
        //databaseManager.getAppDatabase().getOnlineScanDAO().insert(new OnlineScan(2,0));
        //databaseManager.getAppDatabase().getOnlineScanDAO().insert(new OnlineScan(3,0));
        // inserting offlineScan
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(3,1,2.0));
        // inserting Scan Summary
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,1,1,"offline"));
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,1,1,"online"));
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
        Log.i("onFragmentInt",uri.toString());
    }

    @Override
    public void onFragmentInteraction(Object object, String tag) {
        Log.i("onFragmentInt",object.toString());
        if(tag == "building"){
            chosenBuilding = (Building) object;
        }
        if(tag == "algorithm"){
            chosenAlgorithm = (Algorithm) object;
        }
        //todo add size managment
        if(tag == "size"){
            chosenSize = (int) object;
        }

        // Get ScanFragment
        ScanFragment scanFragment = (ScanFragment)
                getSupportFragmentManager().findFragmentById(R.id.scanLayout);
        scanFragment.updateScansList(chosenBuilding,chosenAlgorithm,chosenSize);

        // get buttons fragment
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
        buttonsFragment.loadScanInfo(chosenBuilding,chosenAlgorithm,chosenSize);

    }

    @Override
    public void onFragmentInteraction(Boolean isOfflineScan) {
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
        buttonsFragment.manageButtons(isOfflineScan);
    }

}
