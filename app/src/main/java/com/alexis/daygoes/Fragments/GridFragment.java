package com.alexis.daygoes.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexis.daygoes.Adapters.CoursesGVAdapter;
import com.alexis.daygoes.Models.ImgModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GridFragment extends Fragment {

private GridView coursesGV;
private ArrayList<ImgModel> dataModalArrayList;
private DatabaseReference db;
private String mName;
private ImgModel mImage1;
private ImgModel mImage2;
private ImgModel mImage3;
private ImgModel mImage4;
private ImgModel mImage5;
private View mView;
private CoursesGVAdapter mAdapter;

public GridFragment() {
}


@Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    mView = inflater.inflate(R.layout.fragment_grid, container, false);

    new CheckInternetConnection(getContext()).checkConnection();

    loadDataGridView();

    return mView;
}

private void loadDataGridView() {

    coursesGV = mView.findViewById(R.id.idGVCourses);

    db = FirebaseDatabase.getInstance().getReference().child("Vehicles");

    Intent i = requireActivity().getIntent();
    mName = i.getStringExtra("NAME_KEY");
    db.child(mName).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ignored : dataSnapshot.getChildren()) {

                mImage1 = dataSnapshot.child("image1").getValue(ImgModel.class);
                mImage2 = dataSnapshot.child("image2").getValue(ImgModel.class);
                mImage3 = dataSnapshot.child("image3").getValue(ImgModel.class);
                mImage4 = dataSnapshot.child("image4").getValue(ImgModel.class);
                mImage5 = dataSnapshot.child("image5").getValue(ImgModel.class);

                dataModalArrayList = new ArrayList<ImgModel>();

                dataModalArrayList.add(mImage1);
                dataModalArrayList.add(mImage2);
                dataModalArrayList.add(mImage3);
                dataModalArrayList.add(mImage4);
                dataModalArrayList.add(mImage5);
            }
            mAdapter = new CoursesGVAdapter(requireContext(), dataModalArrayList);
            coursesGV.setAdapter(mAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}


@Override
public void onResume() {
    super.onResume();
}

// Function to tell the app to start getting data from database on starting of the activity
@Override
public void onStart() {
    super.onStart();
}

// Function to tell the app to stop getting data from database on stopping of the activity
@Override
public void onStop() {
    super.onStop();
}

@Override
public void onDestroyView() {
    super.onDestroyView();
}
}
