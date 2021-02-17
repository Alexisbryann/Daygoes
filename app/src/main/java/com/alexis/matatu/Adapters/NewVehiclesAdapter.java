package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Models.NewVehiclesModel;
import com.alexis.matatu.R;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewVehiclesAdapter extends FirebaseRecyclerAdapter<NewVehiclesModel, NewVehiclesAdapter.FirebaseViewHolder> {

    private final Context mContext;

        public NewVehiclesAdapter(FirebaseRecyclerOptions<NewVehiclesModel> options, Context context) {
        super( options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NewVehiclesAdapter.FirebaseViewHolder holder, int position, @NonNull NewVehiclesModel model) {

        Picasso.with(mContext).load(model.getImg()).transform(new PicassoCircleTransformation()).into(holder.mImgVehicleImage);
    }

    @NonNull
    @Override
    public NewVehiclesAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_vehicles, parent, false);
        return new FirebaseViewHolder(view);
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImgVehicleImage;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgVehicleImage = itemView.findViewById(R.id.img_vehicles);
        }
    }
}
