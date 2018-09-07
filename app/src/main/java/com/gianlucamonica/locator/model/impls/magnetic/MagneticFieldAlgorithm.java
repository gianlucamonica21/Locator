package com.gianlucamonica.locator.model.impls.magnetic;

import android.app.Activity;
import android.location.Location;
import android.view.View;

import com.gianlucamonica.locator.model.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locator.model.impls.magnetic.offline.MagneticOfflineManager;
import com.gianlucamonica.locator.model.impls.magnetic.online.MagneticOnlineManager;
import com.gianlucamonica.locator.model.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locator.model.locAlgInterface.LocalizationAlgorithmInterface;

public class MagneticFieldAlgorithm implements LocalizationAlgorithmInterface {

    private MagneticOfflineManager magneticOfflineManager;
    private MagneticOnlineManager magneticOnlineManager;
    private Activity activity;

    public MagneticFieldAlgorithm(Activity activity){
        this.activity = activity;
    }

    @Override
    public Object getBuildClass(Activity activity) {
        return null;
    }

    @Override
    public <T extends View> T build(Class<T> type)  {
        this.magneticOfflineManager = new MagneticOfflineManager(this.activity);
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
