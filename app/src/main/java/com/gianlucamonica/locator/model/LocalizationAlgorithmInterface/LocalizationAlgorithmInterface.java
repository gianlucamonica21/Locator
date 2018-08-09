package com.gianlucamonica.locator.model.LocalizationAlgorithmInterface;

import android.app.Activity;
import android.location.Location;
import android.view.View;

public interface LocalizationAlgorithmInterface {

    public Object getBuildClass(Activity activity);
    public  <T extends View> T build(Class<T> type);
    public Location locate();
    public boolean canGetLocation();
    public boolean isProviderEnabled(); // to check if permission is needed

    void showSettingsAlert();

    double getLongitude();

    double getLatitude();
}
