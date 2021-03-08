package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.alexis.matatu.Adapters.SceneAdapter;
import com.alexis.matatu.Models.SceneModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TheScene extends AppCompatActivity {

    private RecyclerView mRv_scene;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mDb;
    private SceneAdapter mSceneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_scene);
        getSupportActionBar().setTitle("The Scene");

//      Inflate views


//      Initialize recyclerView
        mRv_scene = findViewById(R.id.rv_scene);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRv_scene.setLayoutManager(mLinearLayoutManager);


//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("ChatGroups");


//      query db
        FirebaseRecyclerOptions<SceneModel> options
                = new FirebaseRecyclerOptions.Builder<SceneModel>()
                .setQuery(mDb, SceneModel.class)
                .build();

//      Initialize and set adapter
        mSceneAdapter = new SceneAdapter(options, TheScene.this);
        mRv_scene.setAdapter(mSceneAdapter);
        mSceneAdapter.startListening();
    }
}