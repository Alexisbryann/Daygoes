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

import com.alexis.matatu.IndividualRouteVehicle;
import com.alexis.matatu.Models.IndividualRouteModel;
import com.alexis.matatu.R;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
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

        Picasso.with(mContext).load(model.getImage()).transform(new PicassoCircleTransformation()).into(holder.mImg_pic);
        holder.mTv_name.setText(model.getName());
        holder.mTv_route.setText(model.getRoute());
        holder.mTv_capacity.setText(model.getCapacity());
        holder.mTv_plate.setText(model.getPlate());
        holder.mTv_ratings.setRating(model.getRatings());
        holder.mTv_no_of_stars.setText(holder.mTv_ratings.getRating() +" Stars");
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
        private final RatingBar mTv_ratings;
        private final ImageView mImg_pic;
        private final TextView mTv_no_of_stars;
        private final TextView mTv_route;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mImg_pic = itemView.findViewById(R.id.imgview_vehicle_photo);
            mTv_name = itemView.findViewById(R.id.tv_vehicle_name1);
            mTv_route = itemView.findViewById(R.id.tv_sacco1);
            mTv_capacity = itemView.findViewById(R.id.tv_capacity1);
            mTv_plate = itemView.findViewById(R.id.tv_no_plate1);
            mTv_ratings = itemView.findViewById(R.id.ratings);
            mTv_no_of_stars = itemView.findViewById(R.id.tv_no_of_stars);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, IndividualRouteVehicle.class);
                    i.putExtra("NAME_KEY", mTv_name.getText().toString());
                    i.putExtra("PLATE_KEY", mTv_plate.getText().toString());
                    i.putExtra("ROUTE_KEY", mTv_capacity.getText().toString());
                    context.startActivity(i);
                }
            });

        }
    }
}
