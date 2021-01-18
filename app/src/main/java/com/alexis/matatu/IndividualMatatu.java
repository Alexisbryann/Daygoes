package com.alexis.matatu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class IndividualMatatu extends AppCompatActivity {
    private SliderLayout mSliderShow;
    private View mView;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;
    private TextView mTv_goes;
    private SliderLayout mSlider;
    private ImageView mLike;
    private ImageView mFavourite;
    private ImageView mShare;
    private ImageView mDislike;
    private MaterialButton mPay;
    private RatingBar mRatingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_matatu);

        inflateImageSlider();

//        inflate the layout for this fragment

        mTv_goes= findViewById(R.id.tv_goes);
        mTv_name= findViewById(R.id.tv_matatu_name);
        mTv_plate=findViewById(R.id.tv_plate);
        mTv_route=findViewById(R.id.tv_route);
        mSlider=findViewById(R.id.slider);
        mLike=findViewById(R.id.img_like);
        mFavourite=findViewById(R.id.img_favourite);
        mShare=findViewById(R.id.img_share);
        mDislike=findViewById(R.id.img_dislike);
        mPay=findViewById(R.id.btn_gift);
        mRatingBar=findViewById(R.id.ratingBar);

        //retrieving data using intent
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");
        String plate = i.getStringExtra("PLATE_KEY");
        String route = i.getStringExtra("ROUTE_KEY");

        mTv_name.setText(name);
        mTv_plate.setText(plate);
        mTv_route.setText(route);

        iconInitialize();
    }
    @Override
    protected void onResume() {
        iconInitialize();
        super.onResume();
    }
    private void iconInitialize() {
        mLike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                mLike.setColorFilter(Color.rgb(0, 100, 0));
                mDislike.setColorFilter(Color.rgb(255, 255, 255));
                Toast toast1 = Toast.makeText(IndividualMatatu.this, "Liked", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
        mDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDislike.setColorFilter(Color.rgb(255, 0, 0));
                mLike.setColorFilter(Color.rgb(255, 255, 255));
                Toast toast = Toast.makeText(IndividualMatatu.this,"Disliked",Toast.LENGTH_LONG);
                toast.show();
            }
        });

        mFavourite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                mFavourite.setColorFilter(Color.rgb(255, 191, 0));
                Toast toast = Toast.makeText(IndividualMatatu.this,"Vehicle made favourite",Toast.LENGTH_LONG);
                toast.show();
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(IndividualMatatu.this,"Share",Toast.LENGTH_LONG);
                toast.show();
            }
        });
        mRatingBar.getRating();
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        mSliderShow = findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya1.jpg?alt=media&token=8bdc129a-587b-40c9-bf07-135fc45a26af");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya2.jpg?alt=media&token=f41c9793-2214-4e89-a2d5-8bf47772d57b");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya3.jpg?alt=media&token=365909ab-0474-4a6b-9a78-dc8179d2add7");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya4.jpg?alt=media&token=db170175-c382-47c5-a226-4ea425e6c028");

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
            mSliderShow.addSlider(sliderView);
        }
        mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
    }
}
