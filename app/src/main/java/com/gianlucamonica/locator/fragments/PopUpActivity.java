package com.gianlucamonica.locator.fragments;

import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.gianlucamonica.locator.R;

public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widht = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        //getWindow().setLayout((int) ((int) widht*.8),(int) ((int) height*.6));

    }
}
