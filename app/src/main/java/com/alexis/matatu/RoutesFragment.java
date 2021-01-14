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
import androidx.recyclerview.widget.GridLayoutManager;
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
    private GridLayoutManager mGridLayoutManager;

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

        mTv_from = mView.findViewById(R.id.tv_route);
//        mTv_destination = mView.findViewById(R.id.tv_destination);


        mList = new ArrayList<>();
        mList.add(new RoutesModel("Rongai to CBD"));
        mList.add(new RoutesModel("Eastleigh to station"));
        mList.add(new RoutesModel("Westlands to Fire station"));
        mList.add(new RoutesModel("Kikuyu to Khoja"));
        mList.add(new RoutesModel("Railways to Rongai"));
        mList.add(new RoutesModel("Ngong to Agrho house"));
        mList.add(new RoutesModel("Ngong to Ambassadeur"));
        mList.add(new RoutesModel("Allsops to Imenti house"));
        mList.add(new RoutesModel("Kasarani to CBD"));
        mList.add(new RoutesModel("CBD to Nyayo estate"));
        mList.add(new RoutesModel("CBD to Githurai 44"));
        mList.add(new RoutesModel("Rongai to CBD"));
        mList.add(new RoutesModel("Eastleigh to Bus station"));
        mList.add(new RoutesModel("Westlands to Fire station"));
        mList.add(new RoutesModel("Kikuyu to Khoja"));
        mList.add(new RoutesModel("Railways to Rongai"));
        mList.add(new RoutesModel("Ngong to Agrho house"));
        mList.add(new RoutesModel("Ngong to Ambassadeur"));
        mList.add(new RoutesModel("Allsops to Imenti house"));
        mList.add(new RoutesModel("Kasarani to CBD"));
        mList.add(new RoutesModel("CBD to Nyayo estate"));
        mList.add(new RoutesModel("CBD to Githurai 44"));



        mRecyclerView = mView.findViewById(R.id.recycler_route);
//        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mGridLayoutManager = new GridLayoutManager(getContext(),3);
        mRoutesAdapter = new RoutesAdapter(getContext(),mList);
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mRoutesAdapter);

        return mView;
    }
}
