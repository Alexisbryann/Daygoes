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

import java.util.ArrayList;

public class MatatuAdapter1 extends RecyclerView.Adapter<MatatuAdapter1.ViewHolder> {

    private final Context mContext;
    private final ArrayList mMatatuModelList;

    public MatatuAdapter1(Context context, ArrayList matatuLists) {
        mContext = context;
        mMatatuModelList = matatuLists;

    }

    @NonNull
    @Override
    public MatatuAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        View view = layoutInflater.inflate(R.layout.item_vehicles, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatatuAdapter1.ViewHolder holder, int position) {

        holder.setData((MatatuModel) mMatatuModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMatatuModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_name;
        private final TextView mTv_route;
        private final TextView mTv_capacity;
        private final TextView mTv_plate;
        private final RatingBar mTv_ratings;
        MatatuModel item;
        private final ImageView mImg_pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImg_pic = itemView.findViewById(R.id.imgview_vehicle_photo);
            mTv_name = itemView.findViewById(R.id.tv_vehicle_name1);
            mTv_route = itemView.findViewById(R.id.tv_route1);
            mTv_capacity = itemView.findViewById(R.id.tv_capacity1);
            mTv_plate = itemView.findViewById(R.id.tv_no_plate1);
            mTv_ratings = itemView.findViewById(R.id.ratings);
        }

        private void setData(MatatuModel item) {
            this.item = item;

//            mImg_pic.setImageResource(item.getImage());
            mTv_name.setText(item.getName());
            mTv_route.setText(item.getRoute());
            mTv_capacity.setText(item.getCapacity());
            mTv_plate.setText(item.getPlate());
            mTv_ratings.setRating(item.getRatings());

        }
    }
}
