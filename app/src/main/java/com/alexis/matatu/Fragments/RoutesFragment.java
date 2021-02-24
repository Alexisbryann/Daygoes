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

import com.alexis.matatu.Adapters.RoutesAdapter;
import com.alexis.matatu.Models.RoutesModel;
import com.alexis.matatu.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RoutesFragment extends Fragment {

    private DatabaseReference mDb;
    private ArrayList<RoutesModel> mList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RoutesAdapter mRoutesAdapter;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private TextView mAppName;
    private TextView mTv_Route;

    public RoutesFragment(){
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.routes,container,false);

//      Inflate views
        mToolbar = mView.findViewById(R.id.toolbar);
        mSearchView = mView.findViewById(R.id.search_view_routes);
        mTv_Route = mView.findViewById(R.id.tv_sacco);

//      Initialize recyclerView
        mRecyclerView = mView.findViewById(R.id.recycler_route);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("Routes");

//      query db
        FirebaseRecyclerOptions<RoutesModel> options
                = new FirebaseRecyclerOptions.Builder<RoutesModel>()
                .setQuery(mDb, RoutesModel.class)
                .build();

//      Initialize and set adapter
        mRoutesAdapter = new RoutesAdapter(options,RoutesFragment.this,getContext());
        mRecyclerView.setAdapter(mRoutesAdapter);

        return mView;
    }
    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart()
    {
        super.onStart();
        mRoutesAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        mRoutesAdapter.stopListening();
    }
}
