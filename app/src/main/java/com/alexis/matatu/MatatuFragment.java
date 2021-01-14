package com.alexis.matatu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Adapters.MatatuAdapter;
import com.alexis.matatu.Models.MatatuModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatatuFragment extends Fragment {

    private MatatuAdapter mMatatuAdapter;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private TextView mAppName;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mDb;
    private DatabaseReference childReference;
    private ImageView mImg_vehicle;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;

    /*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA*/

    public MatatuFragment(){
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.vehicles,container,false);

        mToolbar = mView.findViewById(R.id.toolbar);
        mSearchView = mView.findViewById(R.id.search_view_vehicle);
        mAppName = mView.findViewById(R.id.tv_app_name);

        mTv_name = mView.findViewById(R.id.tv_vehicle_name1);
        mTv_plate = mView.findViewById(R.id.tv_no_plate1);
        mTv_route = mView.findViewById(R.id.tv_route1);

        mImg_vehicle = mView.findViewById(R.id.imgview_vehicle_photo);

//        Initialize recyclerview
        mRecyclerView = mView.findViewById(R.id.rv_matatu_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


//        Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicle details");

//        query db
        FirebaseRecyclerOptions<MatatuModel> options
                = new FirebaseRecyclerOptions.Builder<MatatuModel>()
                .setQuery(mDb, MatatuModel.class)
                .build();

        mMatatuAdapter = new MatatuAdapter(options,MatatuFragment.this,getContext());
        mRecyclerView.setAdapter(mMatatuAdapter);

        return mView;
    }
    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart()
    {
        super.onStart();
        mMatatuAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        mMatatuAdapter.stopListening();
    }
}
