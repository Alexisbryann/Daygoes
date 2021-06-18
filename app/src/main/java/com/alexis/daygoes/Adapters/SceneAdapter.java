package com.alexis.daygoes.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Models.SceneModel;
import com.alexis.daygoes.Posts;
import com.alexis.daygoes.R;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(mContext, mRoomName.getText(), Toast.LENGTH_LONG);
                    toast.show();
                    Context context = v.getContext();
                    Intent i = new Intent(context, Posts.class);
                    i.putExtra("NAME_KEY", mRoomName.getText().toString());
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation((Activity)context);
                    context.startActivity(i, options.toBundle());
                }
            });


        }
    }
}
