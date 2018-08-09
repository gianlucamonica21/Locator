package com.gianlucamonica.locator.activities.wifi.mapBuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.utils.map.Coordinate;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.map.JSONReader;
import com.gianlucamonica.locator.utils.map.JSONToRectangleConverter;
import com.gianlucamonica.locator.utils.map.Rectangle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapView extends View{

    int seg = 250;
    int add = 10;
    JSONReader jsonReader;
    JSONObject config;
    JSONToRectangleConverter jsonToRectangleConverter;
    private ArrayList<Rectangle> rects;


    public MapView(Context context){
        super(context);
        jsonReader = new JSONReader("mapConfig.json");
        jsonToRectangleConverter = new JSONToRectangleConverter();

        config = jsonReader.getConfig();
        rects = jsonToRectangleConverter.convert(config);
    }

    public void drawMap(ArrayList<Rectangle> rects, Canvas canvas){
        Log.i("size rects", String.valueOf(rects.size()));
         for(int i = 0; i < rects.size(); i++){
            Paint myPaint = new Paint();
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setColor(Color.WHITE);
            myPaint.setColor(Color.parseColor("#CD5C5C"));
            myPaint.setStrokeWidth(10);
            //rects.get(i).mult(seg,add);
            Log.i("rects",rects.get(i).toString());
            Rect r = new Rect(
                    ((rects.get(i).getA().getX()*seg)+add),
                    ((rects.get(i).getA().getY()*seg)+add),
                    ((rects.get(i).getB().getX()*seg)+add),
                    ((rects.get(i).getB().getY()*seg)+add));
            canvas.drawRect(r, myPaint);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setColor(Color.BLACK);
            canvas.drawRect(r, myPaint);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        //super.onDraw(canvas);

        drawMap(rects,canvas);
    }

    public ArrayList<Rectangle> getRects() {
        return rects;
    }

    public void setRects(ArrayList<Rectangle> rects) {
        this.rects = rects;
    }

    public int getSeg() {
        return seg;
    }

    public void setSeg(int seg) {
        this.seg = seg;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }
}
