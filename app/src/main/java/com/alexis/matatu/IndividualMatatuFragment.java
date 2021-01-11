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

public class IndividualMatatuFragment extends Fragment {
    private SliderLayout mSliderShow;
    private View mView;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;

    public IndividualMatatuFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.individual_matatu,container,false);

        inflateImageSlider();

//        inflate the layout for this fragment
        mTv_name = mView.findViewById(R.id.tv_matatu_name);
        mTv_plate = mView.findViewById(R.id.tv_plate);
        mTv_route = mView.findViewById(R.id.tv_route);

        //retrieving data using bundle
        Bundle bundle=getArguments();

        mTv_name.setText(String.valueOf(bundle.getString("NAME_KEY")));
        mTv_plate.setText(String.valueOf(bundle.getString("PLATE_KEY")));
        mTv_route.setText(String.valueOf(bundle.getString("ROUTE_KEY")));


        return mView;
    }

    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        mSliderShow = mView.findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/cereals.jpg?alt=media&token=b77bf812-b0f9-47d3-9339-3759fffa7034");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/detergents.jpg?alt=media&token=48da5e85-aaf1-458c-8c1c-2288f4c5914f");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/drinks.jpg?alt=media&token=2c98c502-8681-4504-a219-0f2b6e8dc4b2");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/e-shop-9c40d.appspot.com/o/grocery.jpg?alt=media&token=e6590863-734e-4418-8f9d-c233ba9df63c");

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            sliderView.image(s);
            mSliderShow.addSlider(sliderView);
        }
        mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
    }
}
