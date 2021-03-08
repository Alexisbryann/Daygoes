package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Models.SceneModel;
import com.alexis.matatu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SceneAdapter extends FirebaseRecyclerAdapter<SceneModel, SceneAdapter.FirebaseViewHolder> {
    private final Context mContext;

    public SceneAdapter(@NonNull FirebaseRecyclerOptions<SceneModel> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull SceneAdapter.FirebaseViewHolder holder, int position, @NonNull SceneModel model) {
        holder.mRoomName.setText(model.getChatGroup());
    }

    @NonNull
    @Override
    public SceneAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scene, parent, false);
        return new FirebaseViewHolder(view);
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mRoomName;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mRoomName = itemView.findViewById(R.id.tv_chat_room);

        }
    }
}
