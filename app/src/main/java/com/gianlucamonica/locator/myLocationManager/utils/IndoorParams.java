package com.gianlucamonica.locator.myLocationManager.utils;

import java.io.Serializable;

public class IndoorParams implements Serializable {

    private String name;
    private Object paramObject;

    public IndoorParams(String name, Object paramObject) {
        this.name = name;
        this.paramObject = paramObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getParamObject() {
        return paramObject;
    }

    public void setParamObject(Object paramObject) {
        this.paramObject = paramObject;
    }

    @Override
    public String toString() {
        return "IndoorParams{" +
                "name='" + name + '\'' +
                ", paramObject=" + paramObject +
                '}';
    }
}
