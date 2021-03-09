package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Models.ChatModel;
import com.alexis.matatu.Models.OffersModel;
import com.alexis.matatu.R;
import com.alexis.matatu.VehicleOffers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OffersAdapter extends FirebaseRecyclerAdapter<OffersModel,OffersAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public OffersAdapter(FirebaseRecyclerOptions<OffersModel> options,  Context context ) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull OffersAdapter.FirebaseViewHolder holder, int position, @NonNull OffersModel model) {
        holder.mOffer.setText(model.getOffer());
        holder.mName.setText(model.getVehicle());
        holder.mRoute.setText(model.getRoute());

    }

    @NonNull
    @Override
    public OffersAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offers, parent, false);
        return new FirebaseViewHolder(view);
    }

    public  class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mOffer;
        private final TextView mName;
        private final TextView mRoute;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mRoute = itemView.findViewById(R.id.tv_route);
            mName = itemView.findViewById(R.id.tv_Vname);
            mOffer = itemView.findViewById(R.id.tv_offer);

        }
    }
}
