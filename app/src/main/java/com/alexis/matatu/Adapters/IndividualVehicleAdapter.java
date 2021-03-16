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

import com.alexis.matatu.Models.IndividualVehicleModel;
import com.alexis.matatu.R;
import com.daimajia.slider.library.SliderLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;

public class IndividualVehicleAdapter extends FirebaseRecyclerAdapter<IndividualVehicleModel, IndividualVehicleAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public IndividualVehicleAdapter(@NonNull FirebaseRecyclerOptions<IndividualVehicleModel> options, Context context) {
        super(options);
        mContext = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull IndividualVehicleAdapter.FirebaseViewHolder holder, int position, @NonNull IndividualVehicleModel model) {
        holder.mName.setText(model.getName());
        holder.mLikes_no.setText(model.getLikes());
        holder.mFavourites_no.setText(model.getFavourites());
        holder.mRatingBar.setRating(model.getRatingBar());
    }
    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_vehicle,parent,false);
        return new FirebaseViewHolder(view);
    }
    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_Sacco;
        private final SliderLayout mSlider;
        private final ImageView mLike;
        private final ImageView mFavourite;
        private final ImageView mShare;
        private final MaterialButton mPay;
        private final RatingBar mRatingBar;
        private final TextView mLikes_no;
        private final TextView mFavourites_no;
        private final TextView mName;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_matatu_name);
            mLikes_no = itemView.findViewById(R.id.tv_likes_no);
            mFavourites_no = itemView.findViewById(R.id.tv_favourites_no);
            mTv_Sacco = itemView.findViewById(R.id.tv_sacco);
            mSlider = itemView.findViewById(R.id.slider);
            mLike = itemView.findViewById(R.id.img_like);
            mFavourite = itemView.findViewById(R.id.img_favourite);
            mShare = itemView.findViewById(R.id.img_share);
            mPay = itemView.findViewById(R.id.btn_make_post);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
        }


    }
}
