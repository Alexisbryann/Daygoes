package com.alexis.daygoes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.PostsAdapter;
import com.alexis.daygoes.Models.PostsModel;
import com.alexis.daygoes.Models.PostsModel1;
import com.alexis.daygoes.Models.SceneModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Posts extends AppCompatActivity {

    private ImageButton mSend;
    private EditText mEdtMessage;
    private PostsAdapter mPostsAdapter;
    private String mUsername1;
    private String mName;
    private TextView mVehicleName;
    private String mGroupName;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private String mUserId;
    private String mCurrentUser1;
    private ImageView mImgLike;
    private ImageView mImgComment;
    private TextView mLikesNo;
    private TextView mCommentsNo;
    private ImageView mMedia;
    private TextView mUsername;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

//      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mUsername = findViewById(R.id.tv_username_posts);
        mVehicleName = findViewById(R.id.tv_vehicle_name_posts);
        mSend = findViewById(R.id.fab_send_posts);
        mMedia = findViewById(R.id.img_upload_media_posts);
        mEdtMessage = findViewById(R.id.edt_message_posts);
        mImgLike = findViewById(R.id.img_like_post1);
        mImgComment = findViewById(R.id.img_comment_post);
        mLikesNo = findViewById(R.id.tv_likes_post);
        mCommentsNo = findViewById(R.id.tv_comment_post);

//      receive intent data passed.
        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");
        mVehicleName.setText(mName + " posts");
        mGroupName = mName + " posts";

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        mUsername1 = prefs.getString("username", "");

        renderui();

        mMedia.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, PostImage.class);
            intent.putExtra("NAME_KEY", mName);
            context.startActivity(intent);

        });
        mSend.setOnClickListener(v -> mSend.setOnClickListener(view -> post()));

//        mImgLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onLikeClicked();
//            }
//        });
//        mImgComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    private void renderui() {
        //      Initialize recyclerview
        RecyclerView recyclerView = findViewById(R.id.rv_posts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//      Initialize DB
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        String user = mCurrentUser.getUid();
        assert mName != null;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Posts").child(mGroupName);

//      query db
        FirebaseRecyclerOptions<PostsModel1> options
                = new FirebaseRecyclerOptions.Builder<PostsModel1>()
                .setQuery(db, PostsModel1.class)
                .build();

//      Initialize and set adapter
        mPostsAdapter = new PostsAdapter(options, Posts.this, this);
        recyclerView.setAdapter(mPostsAdapter);

        DisplayLikesNo();
        DisplayCommentsNo();
    }
    public void onLikeClicked() {

        DatabaseReference liked = FirebaseDatabase.getInstance().getReference().child("Posts Likes")
                .child(mName).child(mUserId);
        DatabaseReference disliked = FirebaseDatabase.getInstance().getReference().child("Posts Dislikes")
                .child(mName);

        liked.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(Posts.this, "Already liked " + mName, Toast.LENGTH_LONG).show();
                } else {
                    liked.setValue(mUserId);
                    disliked.child(mUserId).removeValue();
                    mImgLike.setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void DisplayCommentsNo() {

    }

    private void DisplayLikesNo() {
    }

    private void setAnimation() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        explode.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }

    private void post() {

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mUserId = mCurrentUser.getUid();

        SharedPreferences sharedPreferences = this.getSharedPreferences("MY_PREF2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", mUserId);
        editor.putString("VehicleName", mName);
        editor.apply();


        DatabaseReference Posts = FirebaseDatabase.getInstance()
                .getReference()
                .child("Posts").child(mGroupName);
        DatabaseReference PostGroups = FirebaseDatabase.getInstance()
                .getReference()
                .child("ChatGroups");
        DatabaseReference userPosts = FirebaseDatabase.getInstance()
                .getReference()
                .child("User Posts").child(mUserId);

        String msg = mEdtMessage.getText().toString().trim();
        String messageSender = mUsername1;
        mCurrentUser1 = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (msg.isEmpty()) {
            Toast.makeText(Posts.this, "You can not send a blank message", Toast.LENGTH_LONG).show();
        } else {
            // Read the input field and push a new instance of PostsModel to the Firebase database
            Posts.push().setValue(new PostsModel(msg, messageSender,null)).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    PostGroups.child(mGroupName).setValue(new SceneModel(mName));
                    userPosts.push().setValue(new PostsModel(msg, messageSender,null));
                }
                else{
                    Toast.makeText(Posts.this, "Failed", Toast.LENGTH_LONG).show();

                }
            });
            // Clear the input
        }
        mEdtMessage.setText("");
    }

    @Override
    public void onStart() {
        super.onStart();
        mPostsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPostsAdapter.stopListening();

    }
}