package com.gianlucamonica.locator.model.indoorAlgorithmInterface;

import android.location.Location;

public interface IndoorAlgorithmInterface {

    public Class getBuildClass();
    public void build();
    public Location locate();

}
