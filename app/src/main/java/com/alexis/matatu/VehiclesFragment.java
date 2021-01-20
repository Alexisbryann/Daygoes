package com.alexis.matatu;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Adapters.VehiclesAdapter;
import com.alexis.matatu.Models.MatatuModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehiclesFragment extends Fragment {

    private VehiclesAdapter mMatatuAdapter;
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
    private TextView mTv_no_of_stars;
    private RatingBar mRatings;
    private ShimmerFrameLayout mShimmerFrameLayout;
    /*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA*/

    public VehiclesFragment(){
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.vehicles,container,false);

        mShimmerFrameLayout = mView.findViewById(R.id.shimmerLayout);

        mToolbar = mView.findViewById(R.id.toolbar);
        mAppName = mView.findViewById(R.id.tv_app_name);
        mTv_name = mView.findViewById(R.id.tv_vehicle_name1);
        mTv_plate = mView.findViewById(R.id.tv_no_plate1);
        mTv_route = mView.findViewById(R.id.tv_sacco1);
        mImg_vehicle = mView.findViewById(R.id.imgview_vehicle_photo);

//      Initialize recyclerview
        mRecyclerView = mView.findViewById(R.id.rv_matatu_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.setVisibility(View.GONE);
        mShimmerFrameLayout.startShimmer();
        mShimmerFrameLayout.setVisibility(View.VISIBLE);

//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicle details");

//      query db
        FirebaseRecyclerOptions<MatatuModel> options
                = new FirebaseRecyclerOptions.Builder<MatatuModel>()
                .setQuery(mDb, MatatuModel.class)
                .build();

        //      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this,getContext());
        mRecyclerView.setAdapter(mMatatuAdapter);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mShimmerFrameLayout.setVisibility(View.GONE);
                mShimmerFrameLayout.stopShimmer();
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }, 2000);

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
