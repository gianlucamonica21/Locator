package com.gianlucamonica.locator.model.LocAlgInterface;

import android.app.Activity;
import android.location.Location;
import android.view.View;

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
