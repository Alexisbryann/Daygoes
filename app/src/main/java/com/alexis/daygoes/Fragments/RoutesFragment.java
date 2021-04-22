package com.alexis.daygoes.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.RoutesAdapter;
import com.alexis.daygoes.MainActivity;
import com.alexis.daygoes.Models.RoutesModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class RoutesFragment extends Fragment {

    private DatabaseReference mDb;
    private ArrayList<RoutesModel> mList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RoutesAdapter mRoutesAdapter;

    private TextView mAppName;
    private TextView mTv_Route;
    private androidx.appcompat.widget.SearchView mRoutes_search;
    private String mSearchText;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private androidx.appcompat.widget.Toolbar mToolbar1;


    public RoutesFragment() {
    }

    private View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.routes, container, false);

//      check Internet Connection
        new CheckInternetConnection(getContext()).checkConnection();

//      Inflate views
        mToolbar = mView.findViewById(R.id.toolbar);
        mToolbar1 = mView.findViewById(R.id.toolbar1);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar1);
        mTv_Route = mView.findViewById(R.id.tv_sacco);
        mRoutes_search = mView.findViewById(R.id.search_view_routes);


        initialRecycler();
        search();

        return mView;
    }

    private void initialRecycler() {
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
        mRoutesAdapter = new RoutesAdapter(options, RoutesFragment.this, getContext());
        mRecyclerView.setAdapter(mRoutesAdapter);
        mRoutesAdapter.startListening();
    }


    private void search() {
        mRoutes_search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setRecycler();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchText = mRoutes_search.getQuery().toString().toLowerCase();
                if (mSearchText.isEmpty()) {
                    initialRecycler();

                }
                return false;
            }
        });
    }

    private void setRecycler() {
//      query db

        DatabaseReference DB = FirebaseDatabase.getInstance().getReference().child("Routes");
        Query query = DB.orderByChild("route").startAt(mSearchText).endAt(mSearchText + "\uf8ff");

        FirebaseRecyclerOptions<RoutesModel> options
                = new FirebaseRecyclerOptions.Builder<RoutesModel>()
                .setQuery(query, RoutesModel.class)
                .build();

//      Initialize and set adapter
        mRoutesAdapter = new RoutesAdapter(options, RoutesFragment.this, getContext());
        mRoutesAdapter.startListening();
        mRecyclerView.setAdapter(mRoutesAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        initialRecycler();
    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart() {
        super.onStart();
        initialRecycler();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
        mRoutesAdapter.stopListening();
        initialRecycler();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).unlockDrawer();

    }
}
