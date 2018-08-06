package com.gianlucamonica.locator.activities.wifi.mapBuilder;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
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
        gridView = (GridView) this.activity.findViewById(R.id.gridView1);
        final ProvaLettereDAO letteraDAO = this.appDatabase.getLetteraDAO();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.activity,
                android.R.layout.simple_list_item_1, numbers);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Lettera newL = new Lettera();
                newL.setName((String) ((TextView) v).getText().toString());

                letteraDAO.insert(newL);
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
