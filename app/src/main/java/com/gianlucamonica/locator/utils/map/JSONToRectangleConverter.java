package com.gianlucamonica.locator.utils.map;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONToRectangleConverter {

    public JSONToRectangleConverter() {
    }

    public ArrayList<Rectangle> convert(JSONObject config){

        ArrayList<Coordinate> coo = new ArrayList<Coordinate>();
        ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
        ArrayList<String> rectsName = new ArrayList<String>();

        Iterator<String> iter = config.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONArray value = (JSONArray) config.get(key);
                rectsName.add(key.toString());

                coo.add(new Coordinate((int) value.get(0),(int) value.get(1)));
                coo.add(new Coordinate((int) value.get(2),(int) value.get(3)));
                rects.add(new Rectangle(
                        new Coordinate((int) value.get(0),(int) value.get(1)),
                        new Coordinate((int) value.get(2),(int) value.get(3)),
                        key.toString()));
            } catch (JSONException e) {
                // Something went wrong!
            }
        }
        for (int i = 0; i < coo.size(); i = i + 2) {
            //rects.add(new Rectangle(coo.get(i),coo.get(i+1),"c"));
        }
        Log.i("Rectangle conv", rects.toString());
        return rects;
    }
}
