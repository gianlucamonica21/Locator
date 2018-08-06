package com.gianlucamonica.locator.model.LocalizationAlgorithmInterface;

import android.app.Activity;
import android.location.Location;

public interface LocalizationAlgorithmInterface {

    public Object getBuildClass(Activity activity);
    public void build();
    public Location locate();
    public boolean canGetLocation();
    public boolean isProviderEnabled(); // to check if permission is needed

}
