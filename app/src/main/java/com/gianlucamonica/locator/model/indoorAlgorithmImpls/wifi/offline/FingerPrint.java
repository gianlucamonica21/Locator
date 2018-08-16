package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.offline;

import com.gianlucamonica.locator.utils.db.AP.AP;
import com.gianlucamonica.locator.utils.map.Grid;

public class FingerPrint {

    private AP ap;
    private Grid grid;
    private int rssi;

    public FingerPrint(){}

    public FingerPrint(AP ap, Grid grid, int rssi){
        this.ap = ap;
        this.grid = grid;
        this.rssi = rssi;
    }

    public AP getAp() {
        return ap;
    }

    public void setAp(AP ap) {
        this.ap = ap;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "fingerPrint{" +
                "ap=" + ap +
                ", grid=" + grid +
                ", rssi=" + rssi +
                '}';
    }
}
