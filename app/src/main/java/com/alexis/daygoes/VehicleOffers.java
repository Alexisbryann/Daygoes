package com.alexis.daygoes;

import android.os.Bundle;
import android.transition.Explode;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.ActionBar;
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
        setAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_offers);
        Objects.requireNonNull(getSupportActionBar()).setTitle("OFFERS");
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.background1));

        //      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        //      Initialize recyclerView
        RecyclerView rv_offers = findViewById(R.id.rv_offers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VehicleOffers.this);
        rv_offers.setLayoutManager(linearLayoutManager);

//      query db
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Offers");
        FirebaseRecyclerOptions<OffersModel> options
                = new FirebaseRecyclerOptions.Builder<OffersModel>()
                .setQuery(db1, OffersModel.class)
                .build();

//      Initialize and set adapter
        OffersAdapter offersAdapter = new OffersAdapter(options, VehicleOffers.this);
        rv_offers.setAdapter(offersAdapter);
        offersAdapter.startListening();

    }

    private void setAnimation() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        explode.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }

}