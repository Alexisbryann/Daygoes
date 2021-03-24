package com.alexis.daygoes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.OffersAdapter;
import com.alexis.daygoes.Models.OffersModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class VehicleOffers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_offers);
        Objects.requireNonNull(getSupportActionBar()).setTitle("OFFERS");

        //      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        //      Initialize recyclerView
        RecyclerView rv_offers = findViewById(R.id.rv_offers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VehicleOffers.this);
        rv_offers.setLayoutManager(linearLayoutManager);

//      query db
//        String vehicle = mSpinner_route.getSelectedItem().toString();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Offers");
        FirebaseRecyclerOptions<OffersModel> options
                = new FirebaseRecyclerOptions.Builder<OffersModel>()
                .setQuery(db1, OffersModel.class)
                .build();

//      Initialize and set adapter
        OffersAdapter offersAdapter = new OffersAdapter(options, VehicleOffers.this);
        rv_offers.setAdapter(offersAdapter);
        offersAdapter.startListening();

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