package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.alexis.matatu.Adapters.IndividualRouteAdapter;
import com.alexis.matatu.Adapters.MatatuAdapter;
import com.alexis.matatu.Models.IndividualRouteModel;
import com.alexis.matatu.Models.MatatuModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class IndividualRoute extends AppCompatActivity {

    private DatabaseReference mDb;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private IndividualRouteAdapter mIndividualRouteAdapter;
    /*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicles_routes);

//      Initialize recyclerView
        mRecyclerView = findViewById(R.id.recycler_vehicles_routes);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      retrieving data using intent
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");

//      Initialize DB
        mDb =  FirebaseDatabase.getInstance().getReference().child("Vehicle details");
        Query query = mDb.orderByChild("Route").equalTo(name);

//      query db
        FirebaseRecyclerOptions<IndividualRouteModel> options
                = new FirebaseRecyclerOptions.Builder<IndividualRouteModel>()
                .setQuery(query, IndividualRouteModel.class)
                .build();
//      Initialize and set adapter
        mIndividualRouteAdapter = new IndividualRouteAdapter(options,this);
        mRecyclerView.setAdapter(mIndividualRouteAdapter);

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