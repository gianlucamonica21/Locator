package com.gianlucamonica.locator.myLocationManager.impls.magnetic;

import android.app.Activity;
import android.view.View;

import com.gianlucamonica.locator.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.myLocationManager.impls.magnetic.offline.MagneticOfflineManager;
import com.gianlucamonica.locator.myLocationManager.impls.magnetic.online.MagneticOnlineManager;
import com.gianlucamonica.locator.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;

import java.util.ArrayList;

public class MagneticFieldAlgorithm implements LocalizationAlgorithmInterface {

    private MagneticOfflineManager magneticOfflineManager;
    private MagneticOnlineManager magneticOnlineManager;
    private Activity activity;

    private ArrayList<IndoorParams> indoorParams;

    public MagneticFieldAlgorithm(Activity activity, ArrayList<IndoorParams> indoorParams){
        this.activity = activity;
        this.indoorParams = indoorParams;
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return null;
    }

    @Override
    public <T extends View> T build(Class<T> type)  {
        this.magneticOfflineManager = new MagneticOfflineManager(this.activity,indoorParams);
        return magneticOfflineManager.build(type);
    }

    @Override
    public MagneticFingerPrint locate() {
        magneticOnlineManager = new MagneticOnlineManager(activity);
        return magneticOnlineManager.locate();
    }

    @Override
    public void checkPermissions() {

    }


}
