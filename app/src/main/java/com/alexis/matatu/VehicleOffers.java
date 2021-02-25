package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VehicleOffers extends AppCompatActivity {
    private Spinner mSpinner1;
    private Spinner mSpinner2;
    private DatabaseReference mDb;
    private DatabaseReference mDb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_offers);

        getSupportActionBar().setTitle("OFFERS");

        mDb = FirebaseDatabase.getInstance().getReference().child("Routes");
        mDb1 = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        mSpinner1 = findViewById(R.id.spinner_route);
        mSpinner2 = findViewById(R.id.spinner_vehicle);

        loadSpinner1();
        loadSpinner2();
    }

    private void loadSpinner1() {

        mDb.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> routes = new ArrayList<String>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String routeName = snapshot.getKey();
                    routes.add(routeName);
                }

                ArrayAdapter<String> routesAdapter = new ArrayAdapter<String>(VehicleOffers.this, android.R.layout.simple_spinner_item, routes);
                routesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner1.setAdapter(routesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadSpinner2() {

//        String vehicle = mSpinner1.getSelectedItem().toString();

        mDb1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> vehicles = new ArrayList<String>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String vehicleName = snapshot.getKey();
                    vehicles.add(vehicleName);
                }

                ArrayAdapter<String> vehiclesAdapter = new ArrayAdapter<String>(VehicleOffers.this, android.R.layout.simple_spinner_item, vehicles);
                vehiclesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner2.setAdapter(vehiclesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}