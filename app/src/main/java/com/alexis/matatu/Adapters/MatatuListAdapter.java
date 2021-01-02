package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.alexis.matatu.Models.MatatuModel;
import com.alexis.matatu.R;
import java.util.List;

public class MatatuListAdapter extends RecyclerView.Adapter<MatatuListAdapter.ViewHolder> {

    private final Context mContext;
    private final List<MatatuModel> mMatatuModelList;

    public MatatuListAdapter (Context context, List<MatatuModel> matatuLists){
        mContext = context;
        mMatatuModelList = matatuLists;

    }
    @NonNull
    @Override
    public MatatuListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        View view = layoutInflater.inflate(R.layout.individual_matatu,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatatuListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
