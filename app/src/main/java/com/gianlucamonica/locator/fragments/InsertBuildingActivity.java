package com.gianlucamonica.locator.fragments;

import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.BuildingDAO;

import java.util.ArrayList;

public class InsertBuildingActivity extends AppCompatActivity {

    Button insertButton;
    EditText nameEditText;
    EditText heightEditText;
    EditText widthEditText;

    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_building);

        insertButton = (Button) findViewById(R.id.insertButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        widthEditText = (EditText) findViewById(R.id.widthEditText);

        databaseManager = new DatabaseManager(this);

        final ArrayList<EditText> editTextList = new ArrayList<>();
        editTextList.add(nameEditText);
        editTextList.add(heightEditText);
        editTextList.add(widthEditText);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;

                for (int i = 0; i < editTextList.size(); i++){
                    if(editTextList.get(i).getText().toString().equals("")){
                        valid = false;
                    }
                }

                if(valid){
                    BuildingDAO buildingDAO = databaseManager.getAppDatabase().getBuildingDAO();
                    buildingDAO.insert(new Building(
                            nameEditText.getText().toString(),
                            Integer.parseInt(heightEditText.getText().toString()),
                            Integer.parseInt(widthEditText.getText().toString()),
                            0,
                            0
                    ));
                }
                else {
                    Toast.makeText(MyApp.getContext(),"All fields are required!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
