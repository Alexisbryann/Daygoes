package com.alexis.matatu.Adapters;

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

import com.alexis.matatu.IndividualMatatu;
import com.alexis.matatu.MatatuFragment;
import com.alexis.matatu.Models.MatatuModel;
import com.alexis.matatu.R;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


public class MatatuAdapter extends FirebaseRecyclerAdapter<MatatuModel, MatatuAdapter.FirebaseViewHolder> {

    private DatabaseReference mDb;
    private final Context mContext;

    public MatatuAdapter(@NonNull FirebaseRecyclerOptions<MatatuModel> options, MatatuFragment matatuFragment, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, final int position, @NonNull MatatuModel model) {

        Picasso.with(mContext).load(model.getImage()).transform(new PicassoCircleTransformation()).into(holder.mImg_pic);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Context context = v.getContext();
                    Intent i = new Intent(context, IndividualMatatu.class);
                    i.putExtra("NAME_KEY", mTv_name.getText().toString());
                    i.putExtra("PLATE_KEY", mTv_plate.getText().toString());
                    i.putExtra("ROUTE_KEY", mTv_capacity.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }
}
