package com.alexis.daygoes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.IndividualVehicle;
import com.alexis.daygoes.Models.NewVehiclesModel;
import com.alexis.daygoes.R;
import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class NewVehiclesAdapter extends FirebaseRecyclerAdapter<NewVehiclesModel, NewVehiclesAdapter.FirebaseViewHolder> {

    private final Context mContext;

        public NewVehiclesAdapter(FirebaseRecyclerOptions<NewVehiclesModel> options, Context context) {
        super( options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NewVehiclesAdapter.FirebaseViewHolder holder, int position, @NonNull NewVehiclesModel model) {

        Picasso.with(mContext).load(model.getImage1()).transform(new PicassoCircleTransformation()).into(holder.mImgVehicleImage);
        holder.mTv_name.setText(model.getName());
    }

    @NonNull
    @Override
    public NewVehiclesAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_vehicles, parent, false);
        return new FirebaseViewHolder(view);
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImgVehicleImage;
        private final TextView mTv_name;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgVehicleImage = itemView.findViewById(R.id.img_vehicles);
            mTv_name = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, IndividualVehicle.class);
                    i.putExtra("NAME_KEY", mTv_name.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }
}
