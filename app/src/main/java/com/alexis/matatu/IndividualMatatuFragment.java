package com.alexis.matatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

public class IndividualMatatuFragment extends AppCompatActivity {
    private SliderLayout mSliderShow;
    private View mView;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_matatu);

//        inflateImageSlider();

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

//    private void inflateImageSlider() {
//
//        // Using Image Slider -----------------------------------------------------------------------
//        mSliderShow = findViewById(R.id.slider);
//
//        //populating Image slider
//        ArrayList<String> sliderImages = new ArrayList<>();
//        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/cereals.jpg?alt=media&token=b77bf812-b0f9-47d3-9339-3759fffa7034");
//        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/detergents.jpg?alt=media&token=48da5e85-aaf1-458c-8c1c-2288f4c5914f");
//        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/drinks.jpg?alt=media&token=2c98c502-8681-4504-a219-0f2b6e8dc4b2");
//        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/grocery.jpg?alt=media&token=e6590863-734e-4418-8f9d-c233ba9df63c");
//
//        for (String s : sliderImages) {
//            DefaultSliderView sliderView = new DefaultSliderView(this);
//            sliderView.image(s);
//            mSliderShow.addSlider(sliderView);
//        }
//        mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//    }
}
