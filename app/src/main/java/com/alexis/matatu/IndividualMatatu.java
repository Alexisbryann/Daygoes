package com.alexis.matatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class IndividualMatatu extends AppCompatActivity {
    private SliderLayout mSliderShow;
    private View mView;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_matatu);

        inflateImageSlider();

//        inflate the layout for this fragment
        mTv_name = findViewById(R.id.tv_matatu_name);
        mTv_plate = findViewById(R.id.tv_plate);
        mTv_route = findViewById(R.id.tv_route);
        TextView tv_goes = findViewById(R.id.tv_goes);

        //retrieving data using intent
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");
        String plate = i.getStringExtra("PLATE_KEY");
        String route = i.getStringExtra("ROUTE_KEY");

        mTv_name.setText(name);
        mTv_plate.setText(plate);
        mTv_route.setText(route);


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
