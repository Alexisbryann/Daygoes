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

import com.alexis.matatu.Adapters.RoutesAdapter;
import com.alexis.matatu.Models.RoutesModel;

import java.util.ArrayList;

public class RoutesFragment extends Fragment {

    private ArrayList<RoutesModel> mList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RoutesAdapter mRoutesAdapter;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private TextView mAppName;
    private TextView mTv_from;
    private TextView mTv_destination;

    public RoutesFragment(){
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.routes,container,false);

        mToolbar = mView.findViewById(R.id.toolbar);
        mSearchView = mView.findViewById(R.id.search_view_routes);
        mAppName = mView.findViewById(R.id.tv_app_name);

        mTv_from = mView.findViewById(R.id.tv_from);
        mTv_destination = mView.findViewById(R.id.tv_destination);


        mList = new ArrayList<>();
        mList.add(new RoutesModel("Rongai","CBD"));
        mList.add(new RoutesModel("Eastleigh","Bus station"));
        mList.add(new RoutesModel("Westlands","Fire station"));
        mList.add(new RoutesModel("Kikuyu","Khoja"));
        mList.add(new RoutesModel("Railways","Rongai"));
        mList.add(new RoutesModel("Ngong","Agrho house"));
        mList.add(new RoutesModel("Ngong","Ambassadeur"));
        mList.add(new RoutesModel("Allsops","Imenti house"));
        mList.add(new RoutesModel("Kasarani","CBD"));
        mList.add(new RoutesModel("CBD","Nyayo estate"));
        mList.add(new RoutesModel("CBD","Githurai 44"));
        mList.add(new RoutesModel("Rongai","CBD"));
        mList.add(new RoutesModel("Eastleigh","Bus station"));
        mList.add(new RoutesModel("Westlands","Fire station"));
        mList.add(new RoutesModel("Kikuyu","Khoja"));
        mList.add(new RoutesModel("Railways","Rongai"));
        mList.add(new RoutesModel("Ngong","Agrho house"));
        mList.add(new RoutesModel("Ngong","Ambassadeur"));
        mList.add(new RoutesModel("Allsops","Imenti house"));
        mList.add(new RoutesModel("Kasarani","CBD"));
        mList.add(new RoutesModel("CBD","Nyayo estate"));
        mList.add(new RoutesModel("CBD","Githurai 44"));



        mRecyclerView = mView.findViewById(R.id.recycler_route);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRoutesAdapter = new RoutesAdapter(getContext(),mList);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRoutesAdapter);

        return mView;
    }
}
