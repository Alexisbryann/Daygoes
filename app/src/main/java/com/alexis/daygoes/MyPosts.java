package com.alexis.daygoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alexis.daygoes.Adapters.MyPostsAdapter;
import com.alexis.daygoes.Adapters.PostsAdapter;
import com.alexis.daygoes.Models.PostsModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MyPosts extends AppCompatActivity {
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private String mUser;
    private MyPostsAdapter mMyPostsAdapter;
    private ImageView mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

//      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mDelete = findViewById(R.id.img_my_posts_delete);

//      Initialize recyclerview
        RecyclerView recyclerView = findViewById(R.id.rv_my_posts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//      Initialize DB
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mUser = mCurrentUser.getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("User Posts").child(mUser);

//      query db
        FirebaseRecyclerOptions<PostsModel> options
                = new FirebaseRecyclerOptions.Builder<PostsModel>()
                .setQuery(db, PostsModel.class)
                .build();

//      Initialize and set adapter
        mMyPostsAdapter = new MyPostsAdapter(options, MyPosts.this, this);
        recyclerView.setAdapter(mMyPostsAdapter);

//        onClickListeners();


    }

    private void onClickListeners() {
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("User Posts").orderByChild(mUser).orderByChild("messageText").equalTo("algebre");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String key = child.getKey();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mMyPostsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMyPostsAdapter.stopListening();

    }
}
