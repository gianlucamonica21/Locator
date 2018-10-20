package com.gianlucamonica.locator.myLocationManager.utils;

import com.gianlucamonica.locator.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locator.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;

import java.util.ArrayList;

public class IndoorParamsUtils {

    public Algorithm getAlgorithm(ArrayList<IndoorParams> indoorParams){
        for(int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName().equals(IndoorParamName.ALGORITHM)){
                return (Algorithm) indoorParams.get(i).getParamObject();
            }
        }
        return null;
    }

    public Building getBuilding(ArrayList<IndoorParams> indoorParams){
        for(int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName().equals(IndoorParamName.BUILDING)){
                return (Building) indoorParams.get(i).getParamObject();
            }
        }
        return null;
    }

    public BuildingFloor getFloor(ArrayList<IndoorParams> indoorParams){
        for(int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName().equals(IndoorParamName.FLOOR)){
                return (BuildingFloor) indoorParams.get(i).getParamObject();
            }
        }
        return null;
    }

    public int getSize(ArrayList<IndoorParams> indoorParams){
        for(int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName().equals(IndoorParamName.SIZE)){
                return (int) indoorParams.get(i).getParamObject();
            }
        }
        return -1;
    }

    public Config getConfig(ArrayList<IndoorParams> indoorParams){
        for(int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName().equals(IndoorParamName.CONFIG)){
                return (Config) indoorParams.get(i).getParamObject();
            }
        }
        return null;
    }

    public void updateIndoorParams(ArrayList<IndoorParams> indoorParams, IndoorParamName tag, Object object){
        for (int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName() == tag){
                indoorParams.set(i,new IndoorParams(tag,object));
                return;
            }
        }
        indoorParams.add(new IndoorParams(tag,object));
    }

}
