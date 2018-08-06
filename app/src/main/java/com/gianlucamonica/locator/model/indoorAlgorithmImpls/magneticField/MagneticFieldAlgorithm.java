package com.gianlucamonica.locator.model.indoorAlgorithmImpls.magneticField;

import android.app.Activity;
import android.location.Location;

import com.gianlucamonica.locator.model.LocalizationAlgorithmInterface.LocalizationAlgorithmInterface;

public class MagneticFieldAlgorithm implements LocalizationAlgorithmInterface {


    @Override
    public Object getBuildClass(Activity activity) {
        return null;
    }

    @Override
    public void build() {

    }

    @Override
    public Location locate() {
        return null;
    }

    @Override
    public boolean canGetLocation() {
        return false;
    }

    @Override
    public boolean isProviderEnabled() {
        return false;
    }
}
