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
import com.alexis.matatu.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(getSupportActionBar()).setTitle("OFFERS");

        //      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

//        mDb = FirebaseDatabase.getInstance().getReference().child("Offers");
        //      Initialize recyclerView
        mRv_offers = findViewById(R.id.rv_offers);
        mLinearLayoutManager = new LinearLayoutManager(VehicleOffers.this);
        mRv_offers.setLayoutManager(mLinearLayoutManager);

//      query db
//        String vehicle = mSpinner_route.getSelectedItem().toString();
        mDb1 = FirebaseDatabase.getInstance().getReference().child("Offers");
        FirebaseRecyclerOptions<OffersModel> options
                = new FirebaseRecyclerOptions.Builder<OffersModel>()
                .setQuery(mDb1, OffersModel.class)
                .build();

//      Initialize and set adapter
        mOffersAdapter = new OffersAdapter(options, VehicleOffers.this);
        mRv_offers.setAdapter(mOffersAdapter);
        mOffersAdapter.startListening();

//        mSpinner_route = findViewById(R.id.spinner_route);
//        loadSpinnerRoute();
//        mDb.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final List<String> routes = new ArrayList<>();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String routeName = snapshot.getKey();
//                    routes.add(routeName);
//                }
//
//                ArrayAdapter<String> routesAdapter = new ArrayAdapter<>(VehicleOffers.this, android.R.layout.simple_spinner_item, routes);
//                routesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                mSpinner_route.setAdapter(routesAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

//    private void loadOffers() {
//
////      Initialize recyclerView
//        mRv_offers = findViewById(R.id.rv_offers);
//        mLinearLayoutManager = new LinearLayoutManager(VehicleOffers.this);
//        mRv_offers.setLayoutManager(mLinearLayoutManager);
//
////      query db
//        String vehicle = mSpinner_route.getSelectedItem().toString();
//        mDb1 = FirebaseDatabase.getInstance().getReference().child("Offers").child(vehicle);
//        FirebaseRecyclerOptions<OffersModel> options
//                = new FirebaseRecyclerOptions.Builder<OffersModel>()
//                .setQuery(mDb1, OffersModel.class)
//                .build();
//
////      Initialize and set adapter
//        mOffersAdapter = new OffersAdapter(options, VehicleOffers.this);
//
//        mRv_offers.setAdapter(mOffersAdapter);
////        mOffersAdapter.startListening();
//
//
//
//    }

//    private void loadSpinnerRoute() {
//
//        mDb.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final List<String> routes = new ArrayList<>();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String routeName = snapshot.getKey();
//                    routes.add(routeName);
//                }
//
//                ArrayAdapter<String> routesAdapter = new ArrayAdapter<>(VehicleOffers.this, android.R.layout.simple_spinner_item, routes);
//                routesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                mSpinner_route.setAdapter(routesAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}