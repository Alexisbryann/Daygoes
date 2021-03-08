package com.alexis.matatu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Chat;
import com.alexis.matatu.Models.ChatModel1;
import com.alexis.matatu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class ChatAdapter extends FirebaseRecyclerAdapter<ChatModel1, ChatAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public ChatAdapter(@NonNull FirebaseRecyclerOptions<ChatModel1> options, Chat chat, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatAdapter.FirebaseViewHolder holder, int position, @NonNull ChatModel1 model) {
        holder.mUsername.setText(model.getMessageSender());
        holder.mTimestamp.setText(model.getMessageTime());
        holder.mMessage.setText(model.getMessageText());
    }

    @NonNull
    @Override
    public ChatAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new FirebaseViewHolder(view);
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mUsername;
        private final TextView mTimestamp;
        private final TextView mMessage;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.tv_username);
            mTimestamp = itemView.findViewById(R.id.tv_time_stamp);
            mMessage = itemView.findViewById(R.id.tv_message);


        }
    }
}
