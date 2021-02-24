package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.alexis.matatu.Adapters.ChatAdapter;
import com.alexis.matatu.Adapters.StopsAdapter;
import com.alexis.matatu.Models.ChatModel;
import com.alexis.matatu.Models.StopsModel;
import com.alexis.matatu.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Stops extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mDb;
    private StopsAdapter mStopsAdapter;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stops);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        //      Initialize recyclerview
        mRecyclerView = findViewById(R.id.rv_stops);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");

//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("Routes").child(mName);

//      query db
        FirebaseRecyclerOptions<StopsModel> options
                = new FirebaseRecyclerOptions.Builder<StopsModel>()
                .setQuery(mDb, StopsModel.class)
                .build();

        //      Initialize and set adapter
        mStopsAdapter = new StopsAdapter(options, Stops.this,this);
        mRecyclerView.setAdapter(mStopsAdapter);


    }
}