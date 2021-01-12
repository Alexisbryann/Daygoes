package com.alexis.matatu.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Models.IndividualMatatuModel;
import com.alexis.matatu.R;
import com.daimajia.slider.library.SliderLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;

public class IndividualMatatuAdapter extends FirebaseRecyclerAdapter<IndividualMatatuModel,IndividualMatatuAdapter.ViewHolder> {

    public IndividualMatatuAdapter(@NonNull FirebaseRecyclerOptions<IndividualMatatuModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IndividualMatatuAdapter.ViewHolder holder, int position, @NonNull IndividualMatatuModel model) {

    }

    @NonNull
    @Override
    public IndividualMatatuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView tv_goes = itemView.findViewById(R.id.tv_goes);
            TextView tv_name = itemView.findViewById(R.id.tv_matatu_name);
            TextView tv_plate = itemView.findViewById(R.id.tv_plate);
            TextView tv_route = itemView.findViewById(R.id.tv_route);
            SliderLayout slider = itemView.findViewById(R.id.slider);
            ImageView like = itemView.findViewById(R.id.img_like);
            ImageView favourite = itemView.findViewById(R.id.img_favourite);
            ImageView share = itemView.findViewById(R.id.img_share);
            ImageView dislike = itemView.findViewById(R.id.img_dislike);
            MaterialButton pay = itemView.findViewById(R.id.btn_payWithPoints);
            RatingBar ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
