package com.alexis.daygoes.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Models.PostsModel1;
import com.alexis.daygoes.MyPosts;
import com.alexis.daygoes.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class MyPostsAdapter extends FirebaseRecyclerAdapter<PostsModel1, MyPostsAdapter.FirebaseViewHolder> {

    private final Context mContext;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private String mUser;
    private String mText;


    public MyPostsAdapter(@NonNull FirebaseRecyclerOptions<PostsModel1> options, MyPosts myPosts, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyPostsAdapter.FirebaseViewHolder holder, int position, @NonNull PostsModel1 model) {

        //      Initialize DB
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        assert mCurrentUser != null;
        mUser = mCurrentUser.getUid();

        holder.mTitle.setText(model.getTitle());
        holder.mTimestamp.setText(model.getMessageTime());
        holder.mMessage.setText(model.getMessageText());
        Picasso.with(mContext).load(model.getUrl()).resize(300, 300).centerCrop().into(holder.mImg);

    }

    @NonNull
    @Override
    public MyPostsAdapter.FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_posts, parent, false);
        return new FirebaseViewHolder(view, mContext);
    }

    public static class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTimestamp;
        private final TextView mMessage;
        private final ImageView mImg;
        private final ImageView mDelete;
        private final TextView mTitle;

        private FirebaseUser mCurrentUser;
        private FirebaseAuth mAuth;
        private String mUser;
        private String mKey;
        private DatabaseReference mDelete1;
        private String mVehicleName;
        private String mVName;
        private Context mContext;

        public FirebaseViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            //      Initialize DB
            mAuth = FirebaseAuth.getInstance();
            mCurrentUser = mAuth.getCurrentUser();
            assert mCurrentUser != null;
            mUser = mCurrentUser.getUid();
            this.mContext = context;

            mTitle = itemView.findViewById(R.id.tv_title_my_posts);
            mTimestamp = itemView.findViewById(R.id.tv_time_stamp_my_posts);
            mMessage = itemView.findViewById(R.id.tv_message_my_posts);
            mImg = itemView.findViewById(R.id.img_my_posts_pic);
            mDelete = itemView.findViewById(R.id.img_my_posts_delete);

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mText;
                    mText = mTitle.getText().toString();

                    SharedPreferences prefs = mContext.getSharedPreferences("MY_PREF2", MODE_PRIVATE);
                    mVehicleName = prefs.getString("GroupName", "");

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User Posts").child(mUser).child(mText);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().setValue(null).addOnCompleteListener(task -> {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Posts").child(mVehicleName).child(mText);
                                ref.getRef().setValue(null);
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }
            });

        }

    }
}
