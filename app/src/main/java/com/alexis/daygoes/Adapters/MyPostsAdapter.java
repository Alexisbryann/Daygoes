package com.alexis.daygoes.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.IndividualVehicle;
import com.alexis.daygoes.Models.PostsModel;
import com.alexis.daygoes.Models.PostsModel1;
import com.alexis.daygoes.MyPosts;
import com.alexis.daygoes.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyPostsAdapter extends FirebaseRecyclerAdapter<PostsModel, MyPostsAdapter.FirebaseViewHolder> {

    private final Context mContext;

    public MyPostsAdapter(@NonNull FirebaseRecyclerOptions<PostsModel> options, MyPosts myPosts, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyPostsAdapter.FirebaseViewHolder holder, int position, @NonNull PostsModel model) {
        holder.mTimestamp.setText(model.getMessageTime());
        holder.mMessage.setText(model.getMessageText());
        Picasso.with(mContext).load(model.getUrl()).resize(300, 300).centerCrop().into(holder.mImg);
    }

    @NonNull
    @Override
    public MyPostsAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_posts, parent, false);
        return new FirebaseViewHolder(view);
    }

    public static class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTimestamp;
        private final TextView mMessage;
        private final ImageView mImg;
        private final ImageView mDelete;

        private FirebaseUser mCurrentUser;
        private FirebaseAuth mAuth;
        private String mUser;
        private final String mText;
        private String mKey;
        private DatabaseReference mDelete1;

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            //      Initialize DB
            mAuth = FirebaseAuth.getInstance();
            mCurrentUser = mAuth.getCurrentUser();
            mUser = mCurrentUser.getUid();

            mTimestamp = itemView.findViewById(R.id.tv_time_stamp_my_posts);
            mMessage = itemView.findViewById(R.id.tv_message_my_posts);
            mImg = itemView.findViewById(R.id.img_my_posts_pic);
            mDelete = itemView.findViewById(R.id.img_my_posts_delete);

            mText = mMessage.toString();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User Posts");
                    Query query = reference.orderByChild("messageText").equalTo(mText);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                mKey = childSnapshot.getKey();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                    mDelete1 = reference.child(mUser).child(mKey);
                    mDelete1.removeValue();
                }
            });
        }

    }
}
