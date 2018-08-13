package com.gianlucamonica.locator.model.LocalizationAlgorithmInterface;

import android.app.Activity;
import android.location.Location;
import android.view.View;

import java.io.Serializable;

public interface LocalizationAlgorithmInterface {

    Object getBuildClass(Activity activity);
    <T extends View> T build(Class<T> type);
    Location locate();
    void checkPermissions();
    boolean canGetLocation();
    boolean isProviderEnabled(); // to check if permission is needed


    double getLongitude();
    double getLatitude();
}
