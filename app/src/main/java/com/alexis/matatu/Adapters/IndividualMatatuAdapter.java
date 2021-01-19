package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
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

public class IndividualMatatuAdapter extends FirebaseRecyclerAdapter<IndividualMatatuModel,IndividualMatatuAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public IndividualMatatuAdapter(@NonNull FirebaseRecyclerOptions<IndividualMatatuModel> options, Context context) {
        super(options);
        mContext = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull IndividualMatatuAdapter.FirebaseViewHolder holder, int position, @NonNull IndividualMatatuModel model) {

    }
    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.Individual_vehicle,parent,false);
        return new FirebaseViewHolder(view);
    }
    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_goes;
        private final TextView mTv_name;
        private final TextView mTv_plate;
        private final TextView mTv_route;
        private final SliderLayout mSlider;
        private final ImageView mLike;
        private final ImageView mFavourite;
        private final ImageView mShare;
        private final ImageView mDislike;
        private final MaterialButton mPay;
        private final RatingBar mRatingBar;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mTv_goes = itemView.findViewById(R.id.tv_goes);
            mTv_name = itemView.findViewById(R.id.tv_matatu_name);
            mTv_plate = itemView.findViewById(R.id.tv_plate);
            mTv_route = itemView.findViewById(R.id.tv_route);
            mSlider = itemView.findViewById(R.id.slider);
            mLike = itemView.findViewById(R.id.img_like);
            mFavourite = itemView.findViewById(R.id.img_favourite);
            mShare = itemView.findViewById(R.id.img_share);
            mDislike = itemView.findViewById(R.id.img_dislike);
            mPay = itemView.findViewById(R.id.btn_pay);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
        }

    }
}
