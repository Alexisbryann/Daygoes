package com.alexis.daygoes.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
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
import com.alexis.daygoes.IndividualRouteVehicle;
import com.alexis.daygoes.IndividualVehicle;
import com.alexis.daygoes.MapsActivity;
import com.alexis.daygoes.Models.IndividualRouteModel;
import com.alexis.daygoes.R;
import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class IndividualRouteAdapter extends FirebaseRecyclerAdapter<IndividualRouteModel,IndividualRouteAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public IndividualRouteAdapter(@NonNull FirebaseRecyclerOptions<IndividualRouteModel> options, Context context) {
        super(options);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull IndividualRouteAdapter.FirebaseViewHolder holder, int position, @NonNull IndividualRouteModel model) {

        Picasso.with(mContext).load(model.getImage1()).transform(new PicassoCircleTransformation()).into(holder.mImg_pic);
        holder.mTv_name.setText(model.getName());
        holder.mTv_route.setText(model.getRoute());
        holder.mTv_capacity.setText(model.getCapacity());
        holder.mTv_plate.setText(model.getPlate());
        holder.mRatingBar.setRating(model.getRating());
        holder.mTv_no_of_stars.setText(holder.mRatingBar.getRating() +"");
    }

    @NonNull
    @Override
    public IndividualRouteAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicles_routes,parent,false);
        return new FirebaseViewHolder(view);
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTv_name;
        private final TextView mTv_capacity;
        private final TextView mTv_plate;
        private final RatingBar mRatingBar;
        private final ImageView mImg_pic;
        private final TextView mTv_no_of_stars;
        private final TextView mTv_route;
        private final LottieAnimationView mLocation;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mImg_pic = itemView.findViewById(R.id.imgview_vehicle_photo);
            mTv_name = itemView.findViewById(R.id.tv_vehicle_name1);
            mTv_route = itemView.findViewById(R.id.tv_sacco1);
            mTv_capacity = itemView.findViewById(R.id.tv_capacity1);
            mTv_plate = itemView.findViewById(R.id.tv_no_plate1);
            mRatingBar = itemView.findViewById(R.id.ratings);
            mTv_no_of_stars = itemView.findViewById(R.id.tv_no_of_stars);
            mLocation = itemView.findViewById(R.id.route_location);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent i = new Intent(context, IndividualVehicle.class);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation((Activity) context);

                i.putExtra("NAME_KEY", mTv_name.getText().toString());
                i.putExtra("PLATE_KEY", mTv_plate.getText().toString());
                i.putExtra("ROUTE_KEY", mTv_capacity.getText().toString());
                i.putExtra("ROUTE_KEYY", mTv_route.getText().toString());
                context.startActivity(i,options.toBundle());
            });
            mLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, MapsActivity.class);
                    i.putExtra("NAME_KEY", mTv_name.getText().toString());
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation((Activity)context);
                    context.startActivity(i,options.toBundle());
                }
            });

        }
    }
}
