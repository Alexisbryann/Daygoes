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

import com.alexis.matatu.Models.MatatuModel;
import com.alexis.matatu.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MatatuAdapter extends FirebaseRecyclerAdapter<MatatuModel, MatatuAdapter.FirebaseViewHolder> {

    private DatabaseReference mDb;

    public MatatuAdapter(@NonNull FirebaseRecyclerOptions<MatatuModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull MatatuModel model) {

        Picasso.get().load(model.getImage()).into(holder.mImg_pic);
        holder.mTv_name.setText(model.getName());
        holder.mTv_route.setText(model.getRoute());
        holder.mTv_capacity.setText(model.getCapacity());
        holder.mTv_plate.setText(model.getPlate());
        holder.mTv_ratings.setRating(model.getRatings());
    }
    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicles, parent, false);
        return new FirebaseViewHolder(view);
    }

    public static class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_name;
        private final TextView mTv_route;
        private final TextView mTv_capacity;
        private final TextView mTv_plate;
        private final RatingBar mTv_ratings;
        private final ImageView mImg_pic;
        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mImg_pic = itemView.findViewById(R.id.imgview_vehicle_photo);
            mTv_name = itemView.findViewById(R.id.tv_vehicle_name1);
            mTv_route = itemView.findViewById(R.id.tv_route1);
            mTv_capacity = itemView.findViewById(R.id.tv_capacity1);
            mTv_plate = itemView.findViewById(R.id.tv_no_plate1);
            mTv_ratings = itemView.findViewById(R.id.ratings);

        }
    }
}
