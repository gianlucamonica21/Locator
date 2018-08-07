package com.gianlucamonica.locator.activities.wifi.mapBuilder;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.wifi.WIFIActivity;
import com.gianlucamonica.locator.utils.MyApp;
import com.gianlucamonica.locator.utils.db.AppDatabase;
import com.gianlucamonica.locator.utils.db.Lettera;
import com.gianlucamonica.locator.utils.db.ProvaLettereDAO;

public class WIFIMapBuilder extends AppCompatActivity{

    private AppDatabase appDatabase;
    public Activity activity;
    GridView gridView;
    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    public WIFIMapBuilder(Activity activity){
        this.activity = activity;
    }

    public void build(){
        setupDB();
        final ProvaLettereDAO letteraDAO = this.appDatabase.getLetteraDAO();

        gridView = (GridView) this.activity.findViewById(R.id.gridView1);
        gridView.setNumColumns(4);
        gridView.setColumnWidth(3);
        gridView.setBackgroundColor(Color.WHITE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.activity,
                android.R.layout.simple_list_item_1, numbers){
            public View getView(int position, View convertView, ViewGroup parent) {
                // Return the GridView current item as a View
                View view = super.getView(position, convertView, parent);

                // Convert the view as a TextView widget
                TextView tv = (TextView) view;

                // set the TextView text color (GridView item color)
                tv.setBackgroundColor(Color.LTGRAY);

                // Set the layout parameters for TextView widget
                RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                        GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);

                // Get the TextView LayoutParams
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

                // Set the width of TextView widget (item of GridView)
                params.width = 250;

                // Set the TextView layout parameters
                tv.setLayoutParams(params);

                // Display TextView text in center position
                tv.setGravity(Gravity.CENTER);

                // Set the TextView text font family and text size
                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

                // Set the TextView text (GridView item text)
                //tv.setText(plantsList.get(position));

                // Set the TextView background color
                tv.setBackgroundColor(Color.parseColor("#FFFF4F25"));
                return tv;
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //todo
                //wifi RSS scan and insert in DB
                Lettera newL = new Lettera();
                newL.setName((String) ((TextView) v).getText().toString());
                //letteraDAO.insert(newL);


                v.setVisibility(View.GONE);
                Toast.makeText(MyApp.getContext(),
                        "inserting " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupDB(){
        //setting db
        this.appDatabase = Room.databaseBuilder(this.activity, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        String currentDBPath = this.activity.getDatabasePath("db-contacts").getAbsolutePath();
        Log.i("dbPath:",currentDBPath);
        Stetho.initializeWithDefaults(this.activity);
    }

}
