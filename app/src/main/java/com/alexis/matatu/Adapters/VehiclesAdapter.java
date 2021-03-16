package com.alexis.matatu.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.alexis.matatu.IndividualVehicle;
import com.alexis.matatu.Fragments.VehiclesFragment;
import com.alexis.matatu.MapsActivity;
import com.alexis.matatu.Models.VehicleModel;
import com.alexis.matatu.R;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.squareup.picasso.Picasso;


public class VehiclesAdapter extends FirebaseRecyclerAdapter<VehicleModel, VehiclesAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public VehiclesAdapter(@NonNull FirebaseRecyclerOptions<VehicleModel> options, VehiclesFragment vehiclesFragment, Context context) {
        super(options);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, final int position, @NonNull VehicleModel model) {

        Picasso.with(mContext).load(model.getImage1()).transform(new PicassoCircleTransformation()).into(holder.mImg_pic);
        holder.mTv_name.setText(model.getName());
        holder.mTv_sacco.setText(model.getSacco());
        holder.mTv_capacity.setText(model.getCapacity());
        holder.mTv_plate.setText(model.getPlate());
        holder.mRatingBar.setRating(model.getRating());
        holder.mTv_no_of_stars.setText(model.getRating()+"");

        }
    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicles, parent, false);
        return new FirebaseViewHolder(view);
    }

    public static class FirebaseViewHolder extends RecyclerView.ViewHolder {


        private final TextView mTv_capacity;
        private final TextView mTv_plate;
        private final RatingBar mRatingBar;
        private final ImageView mImg_pic;
        private final TextView mTv_no_of_stars;
        private final TextView mTv_sacco;
        private final TextView mTv_name;
        private final LottieAnimationView mLocation;


        @SuppressLint("SetTextI18n")
        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mImg_pic = itemView.findViewById(R.id.imgview_vehicle_photo);
            mTv_name = itemView.findViewById(R.id.tv_vehicle_name1);
            mTv_sacco = itemView.findViewById(R.id.tv_sacco1);
            mTv_capacity = itemView.findViewById(R.id.tv_capacity1);
            mTv_plate = itemView.findViewById(R.id.tv_no_plate1);
            mRatingBar = itemView.findViewById(R.id.ratings);
            mTv_no_of_stars = itemView.findViewById(R.id.tv_no_of_stars);
            mLocation = itemView.findViewById(R.id.location);

            itemView.setOnClickListener(v -> {

                Context context = v.getContext();
                Intent i = new Intent(context, IndividualVehicle.class);
                i.putExtra("NAME_KEY", mTv_name.getText().toString());
                i.putExtra("PLATE_KEY", mTv_plate.getText().toString());
                i.putExtra("ROUTE_KEY", mTv_capacity.getText().toString());
                context.startActivity(i);
            });

            mLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, MapsActivity.class);
                    i.putExtra("NAME_KEY", mTv_name.getText().toString());
                    context.startActivity(i);

                }
            });
    }
    }
}
