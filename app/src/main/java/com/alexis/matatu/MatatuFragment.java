package com.alexis.matatu;

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

public class MatatuFragment extends Fragment {

    public MatatuFragment(){
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.matatu_list,container,false);

        Toolbar toolbar= mView.findViewById(R.id.toolbar);
        SearchView searchView = mView.findViewById(R.id.search_view_vehicle);
        TextView appName = mView.findViewById(R.id.tv_app_name);
        RecyclerView recyclerView = mView.findViewById(R.id.rv_matatu_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        return mView;
    }
}
