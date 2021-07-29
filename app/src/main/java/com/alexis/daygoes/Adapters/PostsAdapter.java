package com.alexis.daygoes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Models.PostsModel1;
import com.alexis.daygoes.Posts;
import com.alexis.daygoes.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PostsAdapter extends FirebaseRecyclerAdapter<PostsModel1, PostsAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public PostsAdapter(@NonNull FirebaseRecyclerOptions<PostsModel1> options, Posts posts, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostsAdapter.FirebaseViewHolder holder, int position, @NonNull PostsModel1 model) {
        holder.mUsername.setText(model.getMessageSender());
        holder.mTimestamp.setText(model.getMessageTime());
        holder.mMessage.setText(model.getMessageText());
        holder.mTitle.setText(model.getTitle());
        Picasso.with(mContext).load(model.getUrl()).resize(300,300).centerCrop().into(holder.mImg);
    }

    @NonNull
    @Override
    public PostsAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts, parent, false);
        return new FirebaseViewHolder(view);
    }

    public static class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mUsername;
        private final TextView mTimestamp;
        private final TextView mMessage;
        private final ImageView mImg;
        private final TextView mTitle;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.tv_username_posts);
            mTimestamp = itemView.findViewById(R.id.tv_time_stamp_posts);
            mMessage = itemView.findViewById(R.id.tv_message_posts);
            mImg = itemView.findViewById(R.id.img_pic);
            mTitle = itemView.findViewById(R.id.tv_title);

        }
    }
}
