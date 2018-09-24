package com.gianlucamonica.locator.myLocationManager.locAlgInterface;

import android.app.Activity;
import android.location.Location;
import android.view.View;

public interface LocalizationAlgorithmInterface {

    Object getBuildClass(Activity activity);

    <T extends View> T build(Class<T> type);

    <T> T locate();

    void checkPermissions();

}
