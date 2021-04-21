package com.alexis.daygoes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.daygoes.Adapters.PostsAdapter;
import com.alexis.daygoes.Models.PostsModel;
import com.alexis.daygoes.Models.PostsModel1;
import com.alexis.daygoes.Models.SceneModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Posts extends AppCompatActivity {

    private ImageButton mSend;
    private EditText mEdtMessage;
    private PostsAdapter mPostsAdapter;
    private String mUsername1;
    private String mName;
    private TextView mVehicleName;
    private String mGroupName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);


//      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mVehicleName = findViewById(R.id.tv_vehicle_name_posts);
        mSend = findViewById(R.id.fab_send_posts);
        ImageView media = findViewById(R.id.img_upload_media_posts);
        mEdtMessage = findViewById(R.id.edt_message_posts);

//      receive intent data passed.
        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY" );
        mVehicleName.setText(mName + " posts");
        mGroupName = mName + " posts";

        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        mUsername1 = prefs.getString("username", "");

//      Initialize recyclerview
        RecyclerView recyclerView = findViewById(R.id.rv_posts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//      Initialize DB
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

        media.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, PostImage.class);
            intent.putExtra("NAME_KEY", mName);
            context.startActivity(intent);

        });
        mSend.setOnClickListener(v -> mSend.setOnClickListener(view -> comment()));

    }

    private void comment() {

        DatabaseReference Posts = FirebaseDatabase.getInstance()
                .getReference()
                .child("Posts").child(mGroupName);
        DatabaseReference PostGroups = FirebaseDatabase.getInstance()
                .getReference()
                .child("ChatGroups");

        String msg = mEdtMessage.getText().toString().trim();
        String messageSender = mUsername1;

        if (msg.isEmpty()) {
            Toast.makeText(Posts.this, "You can not send a blank message", Toast.LENGTH_LONG).show();
        } else {
            // Read the input field and push a new instance of PostsModel to the Firebase database
            Posts.push().setValue(new PostsModel(msg, messageSender)).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    PostGroups.child(mGroupName).setValue(new SceneModel(mName));
                }
            });
            // Clear the input
        }
        mEdtMessage.setText("");
    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart() {
        super.onStart();
        mPostsAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
        mPostsAdapter.stopListening();

    }
}