package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Fragments.HypeFragment;
import com.alexis.matatu.Models.HypeModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HypeAdapter extends FirebaseRecyclerAdapter<HypeModel,HypeAdapter.FirebaseViewHolder> {
    public HypeAdapter(FirebaseRecyclerOptions<HypeModel> options, HypeFragment hypeFragment, Context context) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HypeAdapter.FirebaseViewHolder holder, int position, @NonNull HypeModel model) {

    }

    @NonNull
    @Override
    public HypeAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public static class FirebaseViewHolder extends RecyclerView.ViewHolder {
        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
