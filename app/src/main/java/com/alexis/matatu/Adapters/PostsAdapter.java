package com.alexis.matatu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Chat;
import com.alexis.matatu.IndividualRoute;
import com.alexis.matatu.Models.ChatModel1;
import com.alexis.matatu.Models.PostsModel;
import com.alexis.matatu.Models.PostsModel1;
import com.alexis.matatu.Posts;
import com.alexis.matatu.R;
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
        Picasso.with(mContext).load(model.getUrl()).into(holder.mImg);
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

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.tv_username_posts);
            mTimestamp = itemView.findViewById(R.id.tv_time_stamp_posts);
            mMessage = itemView.findViewById(R.id.tv_message_posts);
            mImg = itemView.findViewById(R.id.img_pic);

        }
    }
}
