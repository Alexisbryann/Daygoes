package com.alexis.daygoes.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexis.daygoes.IndividualVehicle;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ImageFragment extends Fragment {
private SliderLayout mSliderShow;
private DatabaseReference mDb;
private String mName;
private String mImage1;
private String mImage2;
private String mImage3;
private String mImage4;
private String mImage5;
private String mUserId;

public ImageFragment() {
}

private View mView;


@Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    mView = inflater.inflate(R.layout.activity_images, container, false);

//      check Internet Connection
    new CheckInternetConnection(getContext()).checkConnection();


    inflateImageSlider();

    return mView;
}

private void inflateImageSlider() {

    // Using Image Slider -----------------------------------------------------------------------
    mSliderShow = mView.findViewById(R.id.slider);

    Intent i = requireActivity().getIntent();
    mName = i.getStringExtra("NAME_KEY");

    FirebaseAuth auth = FirebaseAuth.getInstance();
    mUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
    mDb = FirebaseDatabase.getInstance().getReference().child("Vehicles");
    mDb.child(mName).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ignored : dataSnapshot.getChildren()) {

                mImage1 = dataSnapshot.child("image1").getValue(String.class);
                mImage2 = dataSnapshot.child("image2").getValue(String.class);
                mImage3 = dataSnapshot.child("image3").getValue(String.class);
                mImage4 = dataSnapshot.child("image4").getValue(String.class);
                mImage5 = dataSnapshot.child("image5").getValue(String.class);

                ArrayList<String> sliderImages = new ArrayList<>();

                sliderImages.add(mImage1);
                sliderImages.add(mImage2);
                sliderImages.add(mImage3);
                sliderImages.add(mImage4);
                sliderImages.add(mImage5);

                for (String s : sliderImages) {
                    DefaultSliderView sliderView = new DefaultSliderView(getContext());
                    sliderView.image(s);
                    mSliderShow.addSlider(sliderView);
                }
                mSliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
                mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mSliderShow.setCustomAnimation(new DescriptionAnimation());

            }
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
