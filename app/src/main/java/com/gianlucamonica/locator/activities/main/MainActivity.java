package com.gianlucamonica.locator.activities.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.fragments.algorithm.AlgorithmFragment;
import com.gianlucamonica.locator.fragments.building.BuildingFragment;
import com.gianlucamonica.locator.fragments.buttons.ButtonsFragment;
import com.gianlucamonica.locator.fragments.floor.FloorFragment;
import com.gianlucamonica.locator.fragments.param.MagnParamFragment;
import com.gianlucamonica.locator.fragments.scan.ScanFragment;
import com.gianlucamonica.locator.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamsUtils;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locator.myLocationManager.utils.db.scanSummary.ScanSummary;

import java.util.ArrayList;
import java.util.List;

import static com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName.MAGNETIC_FP;
import static com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName.WIFI_BAR_RSS_FP;
import static com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName.WIFI_RSS_FP;

public class MainActivity extends AppCompatActivity implements
        BuildingFragment.BuildingListener, // building
        AlgorithmFragment.OnFragmentInteractionListener, // algorithm
        ButtonsFragment.OnFragmentInteractionListener, // buttons
        FloorFragment.OnFragmentInteractionListener,// flor
        MagnParamFragment.OnFragmentInteractionListener, // magn param
        ScanFragment.OnFragmentInteractionListener{ // scan

    private ArrayList<IndoorParams> indoorParams; // contenitore indoor algorithm infos
    private IndoorParamsUtils indoorParamsUtils;

    private Algorithm chosenAlgorithm;
    private Building chosenBuilding;
    private BuildingFloor chosenFloor;
    private int chosenSize;
    private Config chosenConfig;

    private LocationMiddleware locationMiddleware;
    private DatabaseManager databaseManager;

    private FragmentTransaction ft;
    private boolean INDOOR_LOC;

    private ImageView locImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.setActivity(this);

        indoorParams = new ArrayList<>();
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        databaseManager= new DatabaseManager();

        locImg = findViewById(R.id.locImg);

        locationMiddleware = new LocationMiddleware(indoorParams); // lui capisce se siamo indoor o outdoor
        MyApp.setLocationMiddlewareInstance(locationMiddleware);

        this.INDOOR_LOC = locationMiddleware.isINDOOR_LOC();

        setLocImg();

        Log.i("main","indoor loc " + this.INDOOR_LOC );

        initFragments();

        initDBTesting();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("onFragmentInt",uri.toString());
    }

    @Override
    public void onFragmentInteraction(Object object, IndoorParamName tag) {

        enableFragments();

        if(object != null) {
            Log.i("onFragmentInt", object.toString());
        }
        switch (tag){
            case BUILDING:
                chosenBuilding = (Building) object;
                indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenBuilding); // populate indoor params

                FloorFragment floorFragment= (FloorFragment)
                        getSupportFragmentManager().findFragmentById(R.id.floorLayout);
                floorFragment.setFloorByBuilding(chosenBuilding);
                break;
            case FLOOR:
                chosenFloor = (BuildingFloor) object;
                indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenFloor); // populate indoor params
                break;
            case ALGORITHM:
                chosenAlgorithm = (Algorithm) object;
                indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenAlgorithm); // populate indoor params

                // caricare fragment differente a seconda di chosenAlgorithm
                if( chosenAlgorithm.getName().equals(String.valueOf(MAGNETIC_FP)) ||
                        chosenAlgorithm.getName().equals(String.valueOf(WIFI_RSS_FP)) ||
                        chosenAlgorithm.getName().equals(String.valueOf(WIFI_BAR_RSS_FP))){

                    locationMiddleware.istantiate(AlgorithmName.valueOf(chosenAlgorithm.getName()));
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.add(R.id.paramLayout, new MagnParamFragment(), new MagnParamFragment().getTag());
                    //ft2.commit();
                }
                break;
            case CONFIG:
                chosenConfig = (Config) object;
                Log.i("config main ", String.valueOf(chosenConfig));
                indoorParamsUtils.updateIndoorParams(indoorParams,tag,chosenConfig); // populate indoor params
                break;
            default:
        }

        Log.i("indoorParams",indoorParams.toString());

        // ********* passo parametri indoor a button frag *****************
        if(INDOOR_LOC){
            ButtonsFragment buttonsFragment = (ButtonsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
            buttonsFragment.loadIndoorParams(indoorParams);
        }
        // ****************************************************************

        // ********* load info in dynamic fragment in paramLayout *********
        if(INDOOR_LOC){
            if(chosenAlgorithm != null){
                if(chosenAlgorithm.getName().equals(String.valueOf(AlgorithmName.MAGNETIC_FP)) ||
                        chosenAlgorithm.getName().equals(String.valueOf(AlgorithmName.WIFI_RSS_FP)) ||
                        chosenAlgorithm.getName().equals(String.valueOf(WIFI_BAR_RSS_FP))){

                    MagnParamFragment magnParamFragment = (MagnParamFragment)
                            getSupportFragmentManager().findFragmentById(R.id.paramLayout);
                    magnParamFragment.loadIndoorParams(indoorParams);
                }
            }
        }
        // ****************************************************************

        // ********* managing scan and locate buttons *********
        if(INDOOR_LOC){
            ButtonsFragment buttonsFragment = (ButtonsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
            if ( chosenConfig == null){
                Log.i("main act","sono qui");
                buttonsFragment.manageScanButton(false);
                buttonsFragment.manageLocateButton(false);
            }else{
                buttonsFragment.manageScanButton(true);
                List<ScanSummary> scanSummaries = databaseManager.getAppDatabase().getScanSummaryDAO().
                        getScanSummaryByBuildingAlgorithm(chosenBuilding.getId(),chosenAlgorithm.getId(),chosenConfig.getId());
                if(scanSummaries.size() > 0){
                    buttonsFragment.manageLocateButton(true);
                }
            }
        }
        // ****************************************************************


    }

    public void initFragments(){
        // adding fragments
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.algorithmLayout, new AlgorithmFragment(), new AlgorithmFragment().getTag());
        ft.replace(R.id.buildingLayout, new BuildingFragment(), new BuildingFragment().getTag());
        ft.replace(R.id.floorLayout, new FloorFragment(), new FloorFragment().getTag());
        ft.replace(R.id.buttonsLayout, new ButtonsFragment(), new ButtonsFragment().getTag());
        ft.replace(R.id.paramLayout, new MagnParamFragment(), new MagnParamFragment().getTag());
        //ft.replace(R.id.scanLayout, new ScanFragment(), new ScanFragment().getTag());
        ft.addToBackStack(null);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

    }

    public void enableFragments(){
        // enabling or not according to INDOOR_LOC
        BuildingFragment buildingFragment = (BuildingFragment)
                getSupportFragmentManager().findFragmentById(R.id.buildingLayout);
        FloorFragment floorFragment = (FloorFragment)
                getSupportFragmentManager().findFragmentById(R.id.floorLayout);
        AlgorithmFragment algorithmFragment = (AlgorithmFragment)
                getSupportFragmentManager().findFragmentById(R.id.algorithmLayout);
        MagnParamFragment magnParamFragment = (MagnParamFragment)
                getSupportFragmentManager().findFragmentById(R.id.paramLayout);
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);

        boolean enable = false;
        if(INDOOR_LOC){
            enable = true;
        }

        buildingFragment.enableUI(enable);
        floorFragment.enableUI(enable);
        algorithmFragment.enableUI(enable);
        magnParamFragment.enableUI(enable);
        buttonsFragment.enableUI(enable);
    }

    public void initDBTesting(){
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
            databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(AlgorithmName.WIFI_BAR_RSS_FP),true));
        }
        /*if(databaseManager.getAppDatabase().getConfigDAO().getAllConfigs().size() == 0){
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",1)
            );
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",2)
            );
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",3)
            );
        }*/
        // inserting onlineScan
        //databaseManager.getAppDatabase().getOnlineScanDAO().insert(new OnlineScan(2,0));
        // inserting offlineScan
        Log.i("offlineScan",databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScans().toString());
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(2,1,2.0));
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(1,2,3.0));
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(1,3,4.0));
        // inserting Scan Summary
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,7,1,1,"offline"));
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,-1,1,2,"online"));
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

    public void setLocImg(){
        if(INDOOR_LOC){
            locImg.setImageResource(R.drawable.indoor);
        }else{
            locImg.setImageResource(R.drawable.outdoor);
        }
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

    @Override
    public void onBackPressed() {
        finish();
    }

}
