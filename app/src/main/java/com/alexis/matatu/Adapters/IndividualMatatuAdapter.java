package com.alexis.matatu.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Models.IndividualMatatuModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class IndividualMatatuAdapter extends FirebaseRecyclerAdapter<IndividualMatatuModel,IndividualMatatuAdapter.ViewHolder> {

    public IndividualMatatuAdapter(@NonNull FirebaseRecyclerOptions<IndividualMatatuModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IndividualMatatuAdapter.ViewHolder holder, int position, @NonNull IndividualMatatuModel model) {

    }

    @NonNull
    @Override
    public IndividualMatatuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
