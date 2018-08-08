package com.gianlucamonica.locator.activities.wifi.mapBuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gianlucamonica.locator.utils.Coordinate;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.Rectangle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MapView extends View{

    int seg = 400;
    JSONObject config;
    private Rectangle[] rects = new Rectangle[]{
            new Rectangle(new Coordinate(10,10), new Coordinate(10 + seg, 10 + seg),"U-L"),
            new Rectangle(new Coordinate(10 + seg, 10 + seg), new Coordinate(10 + (seg * 2), 10 + (seg * 2)),"D-R"),
            new Rectangle(new Coordinate(10, 10 + seg), new Coordinate(10 + (seg), 10 + (seg *2)),"D-L")
    };

    public MapView(Context context){
        super(context);
        String s = readFromFile(MyApp.getContext());
        try {
            config = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("LETTO ", s);
    }

    public void drawMap(Rectangle[] rects, Canvas canvas){

        for(int i = 0; i < rects.length; i++){
            Paint myPaint = new Paint();
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setColor(Color.WHITE);
            myPaint.setColor(Color.parseColor("#CD5C5C"));
            myPaint.setStrokeWidth(10);
            Rect r = new Rect(rects[i].getA().getX(), rects[i].getA().getY(), rects[i].getB().getX(), rects[i].getB().getY());
            canvas.drawRect(r, myPaint);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setColor(Color.BLACK);
            canvas.drawRect(r, myPaint);
        }

    }

    private String readFromFile(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("mapConfig.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        drawMap(rects,canvas);

    }

    public Rectangle[] getRects() {
        return rects;
    }

    public void setRects(Rectangle[] rects) {
        this.rects = rects;
    }


}
