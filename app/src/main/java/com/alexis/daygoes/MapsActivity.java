package com.alexis.daygoes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.airbnb.lottie.model.content.GradientColor;
import com.alexis.daygoes.Network.CheckInternetConnection;
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
    private Button mBtnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");

        mBtnBook = findViewById(R.id.btn_book_now);

        mLocation = FirebaseDatabase.getInstance().getReference().child("Location").child(mName);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);



    }
    private void setAnimation() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        explode.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocation.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Double Latitude = snapshot.child("latitude").getValue(Double.class);
                Double Longitude = snapshot.child("longitude").getValue(Double.class);

                if (snapshot.exists()) {
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

                    mBtnBook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bookRide();
                        }
                    });
                }
                else {
                    Toast.makeText(MapsActivity.this, "This Vehicle is currently offline", Toast.LENGTH_LONG).show();
                    mBtnBook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MapsActivity.this, "Sorry,this Vehicle is currently offline.", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void bookRide() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Let this driver know that you are waiting to board their vehicle?")
                .setTitle("SHOW INTEREST");

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(R.color.colorAccentGreen);
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(R.color.colorAccentRed);
    }

}