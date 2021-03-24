package com.alexis.daygoes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.IndividualRouteAdapter;
import com.alexis.daygoes.Models.IndividualRouteModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class IndividualRoute extends AppCompatActivity {

    private IndividualRouteAdapter mIndividualRouteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicles_routes);
        getSupportActionBar().setTitle("Vehicles on this route");

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

//      Initialize recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_vehicles_routes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//      retrieving data using intent
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");

//      Initialize DB
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        Query query = db.orderByChild("route").equalTo(name);

//      query db
        FirebaseRecyclerOptions<IndividualRouteModel> options
                = new FirebaseRecyclerOptions.Builder<IndividualRouteModel>()
                .setQuery(query, IndividualRouteModel.class)
                .build();

//      Initialize and set adapter
        mIndividualRouteAdapter = new IndividualRouteAdapter(options,this);
        recyclerView.setAdapter(mIndividualRouteAdapter);

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