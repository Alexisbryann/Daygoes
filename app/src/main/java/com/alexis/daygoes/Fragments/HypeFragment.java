package com.alexis.daygoes.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.HypeAdapter;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.R;
import com.alexis.daygoes.TheScene;
import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.alexis.daygoes.VehicleOffers;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class HypeFragment extends Fragment {
    private DatabaseReference mDb;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private HypeAdapter mHypeAdapter;
    private ImageView mImgLogo;
    private Button mOffers;
    private Button mScene;

    public HypeFragment() {
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hype, container, false);

        //check Internet Connection
        new CheckInternetConnection(getContext()).checkConnection();

        mImgLogo = mView.findViewById(R.id.img_logo);
        Picasso.with(getContext()).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);

//      Inflate views
        mOffers = mView.findViewById(R.id.btn_offers);
        mScene = mView.findViewById(R.id.btn_scene);

        mOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offers();
            }
        });
        mScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene();
            }
        });

        return mView;
    }

    private void scene() {
        Intent intent = new Intent(getContext(), TheScene.class);
        startActivity(intent);
    }

    private void offers() {
        Intent intent = new Intent(getContext(), VehicleOffers.class);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

}
