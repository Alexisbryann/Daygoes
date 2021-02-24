package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import com.alexis.matatu.Adapters.IndividualRouteAdapter;
import com.alexis.matatu.Models.IndividualRouteModel;
import com.alexis.matatu.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class IndividualRoute extends AppCompatActivity {

    private DatabaseReference mDb;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private IndividualRouteAdapter mIndividualRouteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicles_routes);
        getSupportActionBar().setTitle("Vehicles on this route");

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

//      Initialize recyclerView
        mRecyclerView = findViewById(R.id.recycler_vehicles_routes);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      retrieving data using intent
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");

//      Initialize DB
        mDb =  FirebaseDatabase.getInstance().getReference().child("Vehicles");
        Query query = mDb.orderByChild("route").equalTo(name);

//      query db
        FirebaseRecyclerOptions<IndividualRouteModel> options
                = new FirebaseRecyclerOptions.Builder<IndividualRouteModel>()
                .setQuery(query, IndividualRouteModel.class)
                .build();

//      Initialize and set adapter
        mIndividualRouteAdapter = new IndividualRouteAdapter(options,this);
        mRecyclerView.setAdapter(mIndividualRouteAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();
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