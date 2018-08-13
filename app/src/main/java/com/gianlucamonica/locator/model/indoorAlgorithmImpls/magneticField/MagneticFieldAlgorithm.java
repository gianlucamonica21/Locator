package com.gianlucamonica.locator.model.indoorAlgorithmImpls.magneticField;

import android.app.Activity;
import android.location.Location;
import android.view.View;

import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;

public class MagneticFieldAlgorithm implements LocalizationAlgorithmInterface {


    @Override
    public Object getBuildClass(Activity activity) {
        return null;
    }

    @Override
    public <T extends View> T build(Class<T> type)  {
        return null;
    }

    @Override
    public Location locate() {
        return null;
    }

    @Override
    public void checkPermissions() {

    }

    @Override
    public boolean canGetLocation() {
        return false;
    }

    @Override
    public boolean isProviderEnabled() {
        return false;
    }

    @Override
    public double getLongitude() {
        return 0;
    }

    @Override
    public double getLatitude() {
        return 0;
    }
}
