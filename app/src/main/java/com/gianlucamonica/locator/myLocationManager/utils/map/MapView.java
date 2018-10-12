package com.gianlucamonica.locator.myLocationManager.utils.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.gianlucamonica.locator.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;

import org.json.JSONObject;

import java.util.ArrayList;

public class MapView extends View {

    private ArrayList<Grid> rects; // rects to draw which compounds the map
    private String estimateGridName;

    // scale factors for drawing map
    int scaleFactor = 100;
    int add = 165;

    // 2a versione
    private int height;
    private int width;
    private int gridSize;

    private ArrayList<IndoorParams> indoorParams;

    /**
     * @param context
     */
    public MapView(Context context,String estimateGridName,ArrayList<IndoorParams> indoorParams){
        super(context);

        this.indoorParams = indoorParams;
        this.estimateGridName = estimateGridName;

        for (int i = 0; i < this.indoorParams.size(); i++){
            switch (indoorParams.get(i).getName()){
                case BUILDING:
                    Building building = (Building) indoorParams.get(i).getParamObject();
                    this.height = building.getHeight();
                    this.width = building.getWidht();
                    break;
                case SIZE:
                    int size = (int) indoorParams.get(i).getParamObject();
                    this.gridSize = size;
                    break;
            }
        }

        ArrayList<Grid> grids = new ArrayList<>();

        for(int i = 0; i < width/gridSize; i++){
            for(int j = 0; j < height/gridSize; j++){
                grids.add(new Grid(
                        new Coordinate(i*gridSize,j*gridSize),
                        new Coordinate((i+1)*gridSize,(j+1)*gridSize),
                        String.valueOf((j*(width/gridSize))+i)
                ));
            }
        }

        Log.i("grids",grids.toString());

        rects = grids;

    }


    /**
     * @param rects
     * @param canvas
     */
    public void drawMap(ArrayList<Grid> rects, Canvas canvas, String estimateGridName){
        Log.i("size rects", String.valueOf(rects.size()));

        Paint underlineText = new Paint();
        underlineText.setColor(Color.BLACK);
        underlineText.setTextAlign(Paint.Align.CENTER);
        underlineText.setTextSize(40);

        /*canvas.drawText("At the end", 500,  500, underlineText);
        canvas.drawText("tap one more time", 500,  540, underlineText);
        canvas.drawText("in order to", 500,  580, underlineText);
        canvas.drawText("finish the scan", 500,  620, underlineText);*/

        for(int i = 0; i < rects.size(); i++){

            Paint myPaint = new Paint();
            myPaint.setStyle(Paint.Style.FILL);

            if ( estimateGridName != null && rects.get(i).getName().equals(estimateGridName)){
                myPaint.setColor(Color.YELLOW);
            }else{
                myPaint.setColor(Color.parseColor("#CD5C5C"));
            }
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
            if ( estimateGridName != null && rects.get(i).getName().equals(estimateGridName)){
                textPaint.setColor(Color.BLACK);
            }else{
                textPaint.setColor(Color.WHITE);
            }
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
        super.onDraw(canvas);

        drawMap(rects,canvas,estimateGridName);
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
