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

import com.alexis.matatu.Adapters.MatatuAdapter;
import com.alexis.matatu.Models.MatatuModel;

import java.util.ArrayList;

public class MatatuFragment extends Fragment {

    private ArrayList<MatatuModel> mList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MatatuAdapter mMatatuAdapter;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private TextView mAppName;

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

        mList = new ArrayList<>();
        mList.add(new MatatuModel(R.drawable.nganya1,"Saint Raphael","Ngong - CBD","32 pax","KDA 250F",4));
        mList.add(new MatatuModel(R.drawable.nganya2,"Matata","Rongai - Railways","28 pax","KCF 650R",5));
        mList.add(new MatatuModel(R.drawable.nganya3,"Phantom","Rongai - Agip house","24 pax","KCP 250G",5));
        mList.add(new MatatuModel(R.drawable.nganya4,"Kingdom","Eastleigh - Bus station","32 pax","KDA 500",2));
        mList.add(new MatatuModel(R.drawable.nganya5,"Winner","Eastleigh - Bus station","30 pax","KCS 896",1));
        mList.add(new MatatuModel(R.drawable.nganya6,"Lorem Ipsum est","Westlands - CBD","28 pax","KAA 851",0));
        mList.add(new MatatuModel(R.drawable.nganya1,"Lorem Ipsum neque","Embakasi - CBD","14 pax","KBC 456",4));


        mRecyclerView = mView.findViewById(R.id.rv_matatu_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mMatatuAdapter = new MatatuAdapter(getContext(),mList);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMatatuAdapter);

        return mView;
    }
}
