package com.alexis.matatu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.alexis.matatu.Network.CheckInternetConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String mName;
    private DatabaseReference mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");

        mLocation = FirebaseDatabase.getInstance().getReference().child("Location").child(mName);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocation.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Double Latitude = snapshot.child("latitude").getValue(Double.class);
                Double Longitude = snapshot.child("longitude").getValue(Double.class);


                LatLng location = new LatLng(Latitude, Longitude);

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(mName));
                int height = 30;
                int width = 30;
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.trolleybus);
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
                marker.setIcon(smallMarkerIcon);
                marker.showInfoWindow();

                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}