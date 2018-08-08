package com.gianlucamonica.locator.utils;

public class Coordinate {

    private int x;
    private int y;


    public Coordinate(int x,int y) {
        this.x = x;
        this.y = y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }
}
