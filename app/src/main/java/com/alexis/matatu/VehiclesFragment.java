package com.alexis.matatu;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

    private RecyclerView mRecyclerView;
    private DatabaseReference mDb;
    private ShimmerFrameLayout mShimmerFrameLayout;
    private Spinner mSpinner;
    /*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA*/

    public VehiclesFragment() {
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.vehicles, container, false);
        mShimmerFrameLayout = mView.findViewById(R.id.shimmerLayout);
        mSpinner = mView.findViewById(R.id.spinner);
        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        initializeRecyclerAndShimmer();
        shimmerThread();
        loadData();
        loadSpinner();


        return mView;
    }

    private void loadSpinner() {

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (selectedItemText.equals("Popular vehicles")) {
                    loadPopularVehicles();
                }if (selectedItemText.equals("Favourite vehicles")){
                    loadFavouriteVehicles();
                }if (selectedItemText.equals("All vehicles")){
                    loadData();
                    Toast.makeText(getContext(),"Showing All vehicles",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    private void loadFavouriteVehicles() {
        //      query db
        FirebaseRecyclerOptions<MatatuModel> options
                = new FirebaseRecyclerOptions.Builder<MatatuModel>()
                .setQuery(mDb, MatatuModel.class)
                .build();

        //      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this, getContext());
        mRecyclerView.setAdapter(mMatatuAdapter);
        mMatatuAdapter.startListening();
        Toast.makeText(getContext(),"Showing your favourite vehicles",Toast.LENGTH_LONG).show();

    }

    private void loadPopularVehicles() {
        //      query db
        FirebaseRecyclerOptions<MatatuModel> options
                = new FirebaseRecyclerOptions.Builder<MatatuModel>()
                .setQuery(mDb, MatatuModel.class)
                .build();
        //      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this, getContext());
        mRecyclerView.setAdapter(mMatatuAdapter);
        mMatatuAdapter.startListening();
        Toast.makeText(getContext(),"Showing popular vehicles",Toast.LENGTH_LONG).show();
    }

    private void shimmerThread() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            mShimmerFrameLayout.setVisibility(View.GONE);
            mShimmerFrameLayout.stopShimmer();
            mRecyclerView.setVisibility(View.VISIBLE);
        }, 6000);
    }

    private void initializeRecyclerAndShimmer() {
        //      Initialize recyclerview
        mRecyclerView = mView.findViewById(R.id.rv_matatu_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setVisibility(View.GONE);
        mShimmerFrameLayout.startShimmer();
        mShimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    private void loadData() {
        //      query db
        FirebaseRecyclerOptions<MatatuModel> options
                = new FirebaseRecyclerOptions.Builder<MatatuModel>()
                .setQuery(mDb, MatatuModel.class)
                .build();

        //      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this, getContext());
        mMatatuAdapter.startListening();
        mRecyclerView.setAdapter(mMatatuAdapter);
    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart() {
        super.onStart();
        mMatatuAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
        mMatatuAdapter.stopListening();

    }
}
