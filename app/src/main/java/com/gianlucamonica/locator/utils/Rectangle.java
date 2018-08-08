package com.gianlucamonica.locator.utils;

public class Rectangle {

    Coordinate a;
    Coordinate b;
    String name;

    public Rectangle (Coordinate a, Coordinate b, String name){
        this.a = a;
        this.b = b;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getA() {
        return a;
    }

    public void setA(Coordinate a) {
        this.a = a;
    }

    public Coordinate getB() {
        return b;
    }

    public void setB(Coordinate b) {
        this.b = b;
    }
}
