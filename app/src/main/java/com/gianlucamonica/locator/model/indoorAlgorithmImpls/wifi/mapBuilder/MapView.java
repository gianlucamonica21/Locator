package com.gianlucamonica.locator.model.indoorAlgorithmImpls.wifi.mapBuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.gianlucamonica.locator.utils.map.JSONReader;
import com.gianlucamonica.locator.utils.map.JSONToGridConverter;
import com.gianlucamonica.locator.utils.map.Grid;

import org.json.JSONObject;

import java.util.ArrayList;

public class MapView extends View {

    private ArrayList<Grid> rects; // rects to draw which compounds the map
    // JSON manager stuff
    JSONReader jsonReader;
    JSONObject config;
    JSONToGridConverter jsonToGridConverter;
    // scale factors for drawing map
    int scaleFactor = 250;
    int add = 40;

    /**
     * @param context
     */
    public MapView(Context context){
        super(context);

        jsonReader = new JSONReader("mapConfig.json");
        jsonToGridConverter = new JSONToGridConverter();

        config = jsonReader.getConfig();
        rects = jsonToGridConverter.convert(config);
    }

    /**
     * @param rects
     * @param canvas
     */
    public void drawMap(ArrayList<Grid> rects, Canvas canvas){
        Log.i("size rects", String.valueOf(rects.size()));

        for(int i = 0; i < rects.size(); i++){

            Paint myPaint = new Paint();
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setColor(Color.WHITE);
            myPaint.setColor(Color.parseColor("#CD5C5C"));
            myPaint.setStrokeWidth(10);
            //rects.get(i).mult(scaleFactor,add);
            Log.i("rects",rects.get(i).toString());
            Rect r = new Rect(
                    ((rects.get(i).getA().getX()* scaleFactor)+add),
                    ((rects.get(i).getA().getY()* scaleFactor)+add),
                    ((rects.get(i).getB().getX()* scaleFactor)+add),
                    ((rects.get(i).getB().getY()* scaleFactor)+add));

            canvas.drawRect(r, myPaint);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setColor(Color.BLACK);
            canvas.drawRect(r, myPaint);

            Paint textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(65);

            float x = ( ((rects.get(i).getA().getX()* scaleFactor)+add) + ((rects.get(i).getB().getX()* scaleFactor)+add) )/2;
            float y = ( ((rects.get(i).getA().getY()* scaleFactor)+add) + ((rects.get(i).getB().getY()* scaleFactor)+add) )/2;

            canvas.drawText(rects.get(i).getName(), x  , y + 15, textPaint);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        //super.onDraw(canvas);

        drawMap(rects,canvas);
    }

    /**
     * @return rects
     */
    public ArrayList<Grid> getRects() {
        return rects;
    }

    /**
     * @param rects
     */
    public void setRects(ArrayList<Grid> rects) {
        this.rects = rects;
    }

    /**
     * @return scale factor scaleFactor
     */
    public int getScaleFactor() {
        return scaleFactor;
    }

    /**
     * @param scaleFactor
     */
    public void setScaleFactor(int scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    /**
     * @return factor add
     */
    public int getAdd() {
        return add;
    }

    /**
     * @param add
     */
    public void setAdd(int add) {
        this.add = add;
    }
}
