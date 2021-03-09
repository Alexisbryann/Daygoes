package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alexis.matatu.Adapters.ChatAdapter;
import com.alexis.matatu.Adapters.OffersAdapter;
import com.alexis.matatu.Models.ChatModel;
import com.alexis.matatu.Models.OffersModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VehicleOffers extends AppCompatActivity {
    private Spinner mSpinner_route;
    private DatabaseReference mDb;
    private DatabaseReference mDb1;
    private String mSelectedRoute;
    private RecyclerView mRv_offers;
    private LinearLayoutManager mLinearLayoutManager;
    private OffersAdapter mOffersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_offers);
        getSupportActionBar().setTitle("OFFERS");

        mDb = FirebaseDatabase.getInstance().getReference().child("Route");

        mSpinner_route = findViewById(R.id.spinner_route);
        loadSpinnerRoute();

        mSpinner_route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedRoute = (String) parent.getItemAtPosition(position);
                loadOffers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void loadOffers() {

//      Initialize recyclerView
        mRv_offers = findViewById(R.id.rv_offers);
        mLinearLayoutManager = new LinearLayoutManager(VehicleOffers.this);
        mRv_offers.setLayoutManager(mLinearLayoutManager);

//      query db
        mDb1 = FirebaseDatabase.getInstance().getReference().child("Offers");
        FirebaseRecyclerOptions<OffersModel> options
                = new FirebaseRecyclerOptions.Builder<OffersModel>()
                .setQuery(mDb1, OffersModel.class)
                .build();

//      Initialize and set adapter
        mOffersAdapter = new OffersAdapter(options, VehicleOffers.this);
        mOffersAdapter.startListening();
        mRv_offers.setAdapter(mOffersAdapter);

    }

    private void loadSpinnerRoute() {

        mDb.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> routes = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String routeName = snapshot.getKey();
                    routes.add(routeName);
                }

                ArrayAdapter<String> routesAdapter = new ArrayAdapter<>(VehicleOffers.this, android.R.layout.simple_spinner_item, routes);
                routesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner_route.setAdapter(routesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}