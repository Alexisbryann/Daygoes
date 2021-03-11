package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alexis.matatu.Adapters.NewVehiclesAdapter;
import com.alexis.matatu.Models.NewVehiclesModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NewVehicles extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDb;
    private String mUid;
    private NewVehiclesAdapter mNewVehiclesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_vehicles);

//        Initialize recyclerview
//        mRecyclerView = findViewById(R.id.rv_new_vehicles);
        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicles");


//      query db

        FirebaseRecyclerOptions<NewVehiclesModel> options
                = new FirebaseRecyclerOptions.Builder<NewVehiclesModel>()
                .setQuery(mDb, NewVehiclesModel.class)
                .build();

        //      Initialize and set adapter
//        mNewVehiclesAdapter = new NewVehiclesAdapter(options,this, mImageList);
//        mRecyclerView.setAdapter(mNewVehiclesAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mNewVehiclesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNewVehiclesAdapter.stopListening();
    }
}