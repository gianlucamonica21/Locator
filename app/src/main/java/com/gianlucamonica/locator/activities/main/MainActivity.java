package com.gianlucamonica.locator.activities.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.fragments.AlgorithmFragment;
import com.gianlucamonica.locator.fragments.BuildingFragment;
import com.gianlucamonica.locator.fragments.ButtonsFragment;
import com.gianlucamonica.locator.fragments.FloorFragment;
import com.gianlucamonica.locator.fragments.MagnParamFragment;
import com.gianlucamonica.locator.fragments.ScanFragment;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;

import java.util.ArrayList;
import java.util.List;

import static com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName.MAGNETIC_FP;

public class MainActivity extends AppCompatActivity implements
        BuildingFragment.BuildingListener, // building
        AlgorithmFragment.OnFragmentInteractionListener, // algorithm
        ButtonsFragment.OnFragmentInteractionListener, // buttons
        FloorFragment.OnFragmentInteractionListener,// flor
        MagnParamFragment.OnFragmentInteractionListener, // magn param
        ScanFragment.OnFragmentInteractionListener{ // scan

    private ArrayList<IndoorParams> indoorParams; // contenitore indoor algorithm infos
    private Algorithm chosenAlgorithm;
    private Building chosenBuilding;
    private int chosenSize;
    private DatabaseManager databaseManager;
    private IndoorParamsUtils indoorParamsUtils;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indoorParams = new ArrayList<>();
        databaseManager = new DatabaseManager(this);
        indoorParamsUtils = new IndoorParamsUtils();
        // adding fragments
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.algorithmLayout, new AlgorithmFragment(), new AlgorithmFragment().getTag());
        ft.replace(R.id.buildingLayout, new BuildingFragment(), new BuildingFragment().getTag());
        ft.replace(R.id.floorLayout, new FloorFragment(), new FloorFragment().getTag());
        ft.replace(R.id.buttonsLayout, new ButtonsFragment(), new ButtonsFragment().getTag());
        //ft.replace(R.id.scanLayout, new ScanFragment(), new ScanFragment().getTag());
        ft.addToBackStack(null);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        DatabaseManager databaseManager= new DatabaseManager(this);
        /** OPERAZIONI DB DI TESTING*/
        // deleting building
        //databaseManager.getAppDatabase().getBuildingDAO().deleteById(11);
        // inserting building
        if(databaseManager.getAppDatabase().getBuildingDAO().getBuildings().size() == 0)
            databaseManager.getAppDatabase().getBuildingDAO().insert(new Building("Unione",5,5,123,123,123,123));
        // inserting algorithms
        if(databaseManager.getAppDatabase().getAlgorithmDAO().getAlgorithms().size() == 0){
            databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(MAGNETIC_FP),true));
            databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(AlgorithmName.WIFI_RSS_FP),true));
        }
        if(databaseManager.getAppDatabase().getConfigDAO().getAllConfigs().size() == 0){
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",1)
            );
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",2)
            );
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",3)
            );
        }
        // inserting onlineScan
        //databaseManager.getAppDatabase().getOnlineScanDAO().insert(new OnlineScan(2,0));
        // inserting offlineScan
        Log.i("offlineScan",databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScans().toString());
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(2,1,2.0));
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(1,2,3.0));
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(1,3,4.0));
        // inserting Scan Summary
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,1,1,"offline"));
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,1,1,"online"));
        //Log.i("deleting scan","");
        //databaseManager.getAppDatabase().getScanSummaryDAO().deleteByBuildingAlgorithmSize(1,1,1,"offline");
        //databaseManager.getAppDatabase().getScanSummaryDAO().deleteByBuildingAlgorithmSize(1,1,1,"online");
        // config isert
        /*databaseManager.getAppDatabase().getConfigDAO().insert(
                new Config(1,"sqSize",1)
        );
        databaseManager.getAppDatabase().getConfigDAO().insert(
                new Config(2,"sqSize",1)
        );*/

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("onFragmentInt",uri.toString());
    }

    @Override
    public void onFragmentInteraction(Object object, IndoorParamName tag) {
        Log.i("onFragmentInt",object.toString());
        if(tag == IndoorParamName.BUILDING){
            chosenBuilding = (Building) object;
            updateIndoorParams(tag, chosenBuilding); // populate indoor params

            FloorFragment floorFragment= (FloorFragment)
                    getSupportFragmentManager().findFragmentById(R.id.floorLayout);
            floorFragment.setFloorByBuilding(chosenBuilding);
        }
        if(tag == IndoorParamName.ALGORITHM){
            chosenAlgorithm = (Algorithm) object;
            updateIndoorParams(tag, chosenAlgorithm); // populate indoor params
            // caricare fragment differente a seconda di chosenAlgorithm
            if( indoorParamsUtils.getAlgorithm(indoorParams).getName().equals(String.valueOf(MAGNETIC_FP))){
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                Log.i("alg scelto",indoorParamsUtils.getAlgorithm(indoorParams).getName());
                ft2.add(R.id.paramLayout, new MagnParamFragment(), new MagnParamFragment().getTag());
                ft2.commit();
            }else{
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.paramLayout);
                if( fragment != null)
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }

        }
        if(tag == IndoorParamName.SIZE){
            chosenSize = (int) object;
            updateIndoorParams(tag, chosenSize); // populate indoor params
        }

        Log.i("indoorParams",indoorParams.toString());

        // Get ScanFragment, to get the scans already present in DB
        /*ScanFragment scanFragment = (ScanFragment)
                getSupportFragmentManager().findFragmentById(R.id.scanLayout);
        scanFragment.updateScansList(indoorParams);*/

        // get buttons fragment, passing indoor params to buttons frag
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
        buttonsFragment.loadIndoorParams(indoorParams);

        // load info in dynamic fragment in paramLayout
        if(chosenAlgorithm != null){
            if(chosenAlgorithm.getName().equals(MAGNETIC_FP)){
                MagnParamFragment magnParamFragment = (MagnParamFragment) getSupportFragmentManager().findFragmentById(R.id.paramLayout);
                magnParamFragment.loadIndoorParams(indoorParams);
            }
        }

        if ( chosenSize <= 0){
            buttonsFragment.manageScanButton(false);
        }else{
            buttonsFragment.manageScanButton(true);
        }

    }

    public void updateIndoorParams(IndoorParamName tag, Object object){
        for (int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName() == tag){
                indoorParams.set(i,new IndoorParams(tag,object));
                return;
            }
        }
        indoorParams.add(new IndoorParams(tag,object));
    }

    @Override
    public void manageSpinner(boolean enable) {
        BuildingFragment buildingFragment = (BuildingFragment)
                getSupportFragmentManager().findFragmentById(R.id.buildingLayout);
        buildingFragment.manageSpinner(enable);
    }

    @Override
    public void onFragmentInteraction(Boolean isOfflineScan) {
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
        buttonsFragment.manageLocateButton(isOfflineScan);
    }

}
