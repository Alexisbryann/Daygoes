package com.alexis.daygoes;

import android.os.Bundle;
import android.transition.Explode;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.SceneAdapter;
import com.alexis.daygoes.Adapters.VehiclesAdapter;
import com.alexis.daygoes.Fragments.VehiclesFragment;
import com.alexis.daygoes.Models.SceneModel;
import com.alexis.daygoes.Models.VehicleModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TheScene extends AppCompatActivity {

    private SearchView mSearchScene;
    private String mSearchText;
    private RecyclerView mRv_scene;
    private DatabaseReference mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_scene);
        getSupportActionBar().setTitle("The Scene");
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.background1));

        mSearchScene = findViewById(R.id.search_view_posts);

        //      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mDb = FirebaseDatabase.getInstance().getReference().child("/ChatGroups");


//      Initialize recyclerView
        mRv_scene = findViewById(R.id.rv_scene);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv_scene.setLayoutManager(linearLayoutManager);

        initialRecycler();
        search();
    }

    private void search() {
        mSearchScene.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setRecycler();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchText = (mSearchScene.getQuery().toString().toLowerCase());
                if (mSearchText.isEmpty()) {
                    initialRecycler();
                    mSearchScene.setIconified(true);
                }
                return false;
            }
        });
    }

    private void setRecycler() {
//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("/ChatGroups");
        Query query = mDb.orderByChild("chatGroup").startAt(mSearchText).endAt(mSearchText + "\uf8ff");

//      query db
        FirebaseRecyclerOptions<SceneModel> options
                = new FirebaseRecyclerOptions.Builder<SceneModel>()
                .setQuery(query, SceneModel.class)
                .build();

//      Initialize and set adapter
        SceneAdapter sceneAdapter = new SceneAdapter(options, TheScene.this);
        mRv_scene.setAdapter(sceneAdapter);
        sceneAdapter.startListening();
    }

    private void initialRecycler() {

//      Initialize recyclerview
        mRv_scene = findViewById(R.id.rv_scene);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv_scene.setLayoutManager(linearLayoutManager);

//      query db
        FirebaseRecyclerOptions<SceneModel> options
                = new FirebaseRecyclerOptions.Builder<SceneModel>()
                .setQuery(mDb, SceneModel.class)
                .build();

//      Initialize and set adapter
        SceneAdapter sceneAdapter = new SceneAdapter(options, TheScene.this);
        mRv_scene.setAdapter(sceneAdapter);
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