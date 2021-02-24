package com.alexis.matatu.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Adapters.HypeAdapter;
import com.alexis.matatu.Models.HypeModel;
import com.alexis.matatu.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HypeFragment extends Fragment { private DatabaseReference mDb;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private HypeAdapter mHypeAdapter;

    public HypeFragment(){
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hype,container,false);

//      Inflate views


//      Initialize recyclerView
        mRecyclerView = mView.findViewById(R.id.recycler_route);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("Routes");

//      query db
        FirebaseRecyclerOptions<HypeModel> options
                = new FirebaseRecyclerOptions.Builder<HypeModel>()
                .setQuery(mDb, HypeModel.class)
                .build();

//      Initialize and set adapter
        mHypeAdapter = new HypeAdapter(options,HypeFragment.this,getContext());
        mRecyclerView.setAdapter(mHypeAdapter);

        return mView;
    }
    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart()
    {
        super.onStart();
        mHypeAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        mHypeAdapter.stopListening();
    }

}
