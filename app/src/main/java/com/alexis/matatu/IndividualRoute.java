package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class IndividualRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicles_routes);



        //retrieving data using intent
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");


    }
}