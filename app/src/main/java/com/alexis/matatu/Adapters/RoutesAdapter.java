package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.alexis.matatu.Models.RoutesModel;
import com.alexis.matatu.R;

import java.util.ArrayList;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList mRoutesModelList;


    public RoutesAdapter (Context context, ArrayList routeLists){
        mContext = context;
        mRoutesModelList = routeLists;
    }

    @NonNull
    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        View view = layoutInflater.inflate(R.layout.item_routes,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesAdapter.ViewHolder holder, int position) {
        holder.setData((RoutesModel) mRoutesModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRoutesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_start;
//        private final TextView mTv_to;
//        private final TextView mTv_destination;
        RoutesModel item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTv_start = itemView.findViewById(R.id.tv_route);
//            mTv_to = itemView.findViewById(R.id.tv_to);
//            mTv_destination = itemView.findViewById(R.id.tv_destination);
        }
        private void setData(RoutesModel item) {
            this.item = item;

            mTv_start.setText(item.getFrom());
//            mTv_destination.setText(item.getDestination());


        }
    }
}
