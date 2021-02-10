package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Models.StopsModel;
import com.alexis.matatu.R;
import com.alexis.matatu.Stops;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StopsAdapter extends FirebaseRecyclerAdapter<StopsModel,StopsAdapter.FirebaseViewHolder> {
    private final Context mContext;

    public StopsAdapter(@NonNull FirebaseRecyclerOptions<StopsModel> options, Stops stops, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull StopsAdapter.FirebaseViewHolder holder, int position, @NonNull StopsModel model) {
        holder.mStop.setText(model.getStop1());
    }

    @NonNull
    @Override
    public StopsAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stops, parent, false);
        return new FirebaseViewHolder(view);
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mStop;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mStop = itemView.findViewById(R.id.tv_stops);
        }
    }
}
