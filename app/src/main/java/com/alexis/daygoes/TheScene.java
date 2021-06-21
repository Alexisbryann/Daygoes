package com.alexis.daygoes;

import android.os.Bundle;
import android.transition.Explode;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.SceneAdapter;
import com.alexis.daygoes.Models.SceneModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TheScene extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_scene);
        getSupportActionBar().setTitle("The Scene");
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.background1));

        //      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

//      Initialize recyclerView
        RecyclerView rv_scene = findViewById(R.id.rv_scene);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_scene.setLayoutManager(linearLayoutManager);


//      Initialize DB
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("/ChatGroups");

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
    private void setAnimation() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        explode.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }
}