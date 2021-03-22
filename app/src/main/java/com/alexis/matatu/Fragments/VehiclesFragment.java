package com.alexis.matatu.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Adapters.NewVehiclesAdapter;
import com.alexis.matatu.Adapters.VehiclesAdapter;
import com.alexis.matatu.Models.NewVehiclesModel;
import com.alexis.matatu.Models.VehicleModel;
import com.alexis.matatu.Network.CheckInternetConnection;
import com.alexis.matatu.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class VehiclesFragment extends Fragment {

    private String mUserId;

    private VehiclesAdapter mMatatuAdapter;
    private RecyclerView mRecyclerView;
    private ShimmerFrameLayout mShimmerFrameLayout;
    private Spinner mSpinner;

    private DatabaseReference mDb;
    private DatabaseReference mDb1;

    private androidx.appcompat.widget.SearchView mVehicle_search;
    private String mSearchText;
    private LinearLayoutManager mLinearLayoutManager;


    public VehiclesFragment() {
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.vehicles, container, false);

        //check Internet Connection
        new CheckInternetConnection(getContext()).checkConnection();

        mShimmerFrameLayout = mView.findViewById(R.id.shimmerLayout);
        mSpinner = mView.findViewById(R.id.spinner);
        mVehicle_search = mView.findViewById(R.id.search_vehicle);

        mDb = FirebaseDatabase.getInstance().getReference();
        mDb1 = mDb.child("Vehicles");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        loadData();
        search();
        loadNew();
        loadSpinner();

        return mView;
    }

    private void search() {
        mVehicle_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setRecycler();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchText = (mVehicle_search.getQuery().toString().toLowerCase());
                if (mSearchText.isEmpty()) {
                    initialRecycler();
                    mVehicle_search.setIconified(true);
                }
                return false;
            }
        });
    }

    private void setRecycler() {
//      query db
        DatabaseReference DB = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        Query query = DB.orderByChild("name").startAt(mSearchText).endAt(mSearchText + "\uf8ff");

        FirebaseRecyclerOptions<VehicleModel> options
                = new FirebaseRecyclerOptions.Builder<VehicleModel>()
                .setQuery(query, VehicleModel.class)
                .build();

//      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this, getContext());
        mMatatuAdapter.startListening();
        mRecyclerView.setAdapter(mMatatuAdapter);
    }

    private void loadNew() {

//      Initialize recyclerview
        RecyclerView recyclerView1 = mView.findViewById(R.id.rv_new_vehicles);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager1);

//      Initialize DB
        mDb1 = FirebaseDatabase.getInstance().getReference().child("Vehicles");

//      query db
        FirebaseRecyclerOptions<NewVehiclesModel> options
                = new FirebaseRecyclerOptions.Builder<NewVehiclesModel>()
                .setQuery(mDb1.limitToLast(10), NewVehiclesModel.class)
                .build();

//      Initialize and set adapter
        NewVehiclesAdapter newVehiclesAdapter = new NewVehiclesAdapter(options, getContext());
        recyclerView1.setAdapter(newVehiclesAdapter);
        newVehiclesAdapter.startListening();
    }

    private void loadSpinner() {

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (selectedItemText.equals("Popular vehicles")) {
                    loadPopularVehicles();
                }
                if (selectedItemText.equals("My favourites")) {
                    loadFavouriteVehicles();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.spinner_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    private void loadFavouriteVehicles() {

        DatabaseReference mDb4 = FirebaseDatabase.getInstance().getReference().child("Favourited").child(mUserId);
//      query db
        Query query = mDb4.orderByChild("name");
        FirebaseRecyclerOptions<VehicleModel> options
                = new FirebaseRecyclerOptions.Builder<VehicleModel>()
                .setQuery(query, VehicleModel.class)
                .build();

//      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this, getContext());
        mRecyclerView.setAdapter(mMatatuAdapter);
        mMatatuAdapter.startListening();
    }

    private void loadPopularVehicles() {
        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicles");

//      query db
        Query query = mDb.orderByChild("rating");
        FirebaseRecyclerOptions<VehicleModel> options
                = new FirebaseRecyclerOptions.Builder<VehicleModel>()
                .setQuery(query, VehicleModel.class)
                .build();

//      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this, getContext());
        mRecyclerView.setAdapter(mMatatuAdapter);
        mMatatuAdapter.startListening();
    }

    private void loadData() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            mShimmerFrameLayout.setVisibility(View.GONE);
            mShimmerFrameLayout.stopShimmer();
            mRecyclerView.setVisibility(View.VISIBLE);
        }, 4000);

//      Initialize recyclerview
        mRecyclerView = mView.findViewById(R.id.rv_matatu_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.setVisibility(View.GONE);
        mShimmerFrameLayout.startShimmer();
        mShimmerFrameLayout.setVisibility(View.VISIBLE);

//      query db
        FirebaseRecyclerOptions<VehicleModel> options
                = new FirebaseRecyclerOptions.Builder<VehicleModel>()
                .setQuery(mDb1, VehicleModel.class)
                .build();

//      Initialize and set adapter
        mMatatuAdapter = new VehiclesAdapter(options, VehiclesFragment.this, getContext());
        mMatatuAdapter.startListening();
        mRecyclerView.setAdapter(mMatatuAdapter);
    }

    private void initialRecycler() {

//      Initialize recyclerview
        mRecyclerView = mView.findViewById(R.id.rv_matatu_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      query db
        FirebaseRecyclerOptions<VehicleModel> options
                = new FirebaseRecyclerOptions.Builder<VehicleModel>()
                .setQuery(mDb1, VehicleModel.class)
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
