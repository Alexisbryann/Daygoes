package com.alexis.matatu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Adapters.SceneAdapter;
import com.alexis.matatu.Models.SceneModel;
import com.alexis.matatu.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TheScene extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_scene);
        getSupportActionBar().setTitle("The Scene");

        //      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

//      Initialize recyclerView
        RecyclerView rv_scene = findViewById(R.id.rv_scene);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_scene.setLayoutManager(linearLayoutManager);


//      Initialize DB
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("ChatGroups");


//      query db
        FirebaseRecyclerOptions<SceneModel> options
                = new FirebaseRecyclerOptions.Builder<SceneModel>()
                .setQuery(db, SceneModel.class)
                .build();

//      Initialize and set adapter
        SceneAdapter sceneAdapter = new SceneAdapter(options, TheScene.this);
        rv_scene.setAdapter(sceneAdapter);
        sceneAdapter.startListening();
    }
}