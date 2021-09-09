package com.alexis.daygoes;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.IndividualRouteAdapter;
import com.alexis.daygoes.Adapters.NewVehiclesAdapter;
import com.alexis.daygoes.Adapters.SceneAdapter;
import com.alexis.daygoes.Adapters.VehiclesAdapter;
import com.alexis.daygoes.Fragments.VehiclesFragment;
import com.alexis.daygoes.Models.IndividualRouteModel;
import com.alexis.daygoes.Models.SceneModel;
import com.alexis.daygoes.Models.VehicleModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class IndividualRoute extends AppCompatActivity {

    private IndividualRouteAdapter mIndividualRouteAdapter;
    private String mRoute;
    private SearchView mSearch_vehicle_route;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private String mSearch_text;
    private DatabaseReference mDb;
    private DatabaseReference mDb1;
    private VehiclesAdapter mMatatuAdapter;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();

        setContentView(R.layout.vehicles_routes);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Vehicles on this route "+mRoute);

//        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.background1));



        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

//      Initialize recyclerView
        mRecyclerView = findViewById(R.id.recycler_vehicles_routes);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSearch_vehicle_route = findViewById(R.id.search_vehicle_route);

        mDb = FirebaseDatabase.getInstance().getReference();
        mDb1 = mDb.child("Vehicles");
        FirebaseAuth auth = FirebaseAuth.getInstance();

//      retrieving data using intent
        Intent i = getIntent();
//        mRoute = i.getStringExtra("ROUTE_KEYY");
        String name = i.getStringExtra("NAME_KEY");

//      Initialize DB
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        mQuery = db.orderByChild("route").equalTo(name);

//      query db
        FirebaseRecyclerOptions<IndividualRouteModel> options
                = new FirebaseRecyclerOptions.Builder<IndividualRouteModel>()
                .setQuery(mQuery, IndividualRouteModel.class)
                .build();

//      Initialize and set adapter
        mIndividualRouteAdapter = new IndividualRouteAdapter(options,this);
        mRecyclerView.setAdapter(mIndividualRouteAdapter);
        search();
    }

    private void search() {
        mSearch_vehicle_route.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    setRecycler();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mSearch_text = (mSearch_vehicle_route.getQuery().toString().toLowerCase());
                    if (mSearch_text.isEmpty()) {
                        initialRecycler();
                        mSearch_vehicle_route.setIconified(true);
                    }
                    return false;
                }
            });

    }

    private void initialRecycler() {
        //      Initialize recyclerview
        mRecyclerView = findViewById(R.id.recycler_vehicles_routes);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      query db
        FirebaseRecyclerOptions<IndividualRouteModel> options
                = new FirebaseRecyclerOptions.Builder<IndividualRouteModel>()
                .setQuery(mQuery, IndividualRouteModel.class)
                .build();

//      Initialize and set adapter
        mIndividualRouteAdapter = new IndividualRouteAdapter(options,this);
        mRecyclerView.setAdapter(mIndividualRouteAdapter);
        mIndividualRouteAdapter.startListening();
    }

    private void setRecycler() {
        //      Initialize DB
        DatabaseReference DB = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        Query query = DB.orderByChild("name").startAt(mSearch_text).endAt(mSearch_text + "\uf8ff");
//      query db
        FirebaseRecyclerOptions<IndividualRouteModel> options
                = new FirebaseRecyclerOptions.Builder<IndividualRouteModel>()
                .setQuery(query, IndividualRouteModel.class)
                .build();

//      Initialize and set adapter
        mIndividualRouteAdapter = new IndividualRouteAdapter(options,this);
        mRecyclerView.setAdapter(mIndividualRouteAdapter);
        mIndividualRouteAdapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();
    }

    public void setAnimation() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        explode.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart()
    {
        super.onStart();
        mIndividualRouteAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        mIndividualRouteAdapter.stopListening();
    }
}