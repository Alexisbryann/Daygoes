package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexis.matatu.Adapters.ChatAdapter;
import com.alexis.matatu.Adapters.VehiclesAdapter;
import com.alexis.matatu.Models.ChatModel;
import com.alexis.matatu.Models.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Chat extends AppCompatActivity {

    private FloatingActionButton mSend;
    private ImageView mMedia;
    private EditText mEdtMessage;
    private TextView mVehicleName;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private String mUsername;
    private String mUid;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDb;
    private DatabaseReference mDb1;
    private ChatAdapter mChatAdapter;
    private String mPhone;
    private String mUsername1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mVehicleName = findViewById(R.id.tv_vehicle_name);
        mSend = findViewById(R.id.fab_send);
        mMedia = findViewById(R.id.img_upload_media);
        mEdtMessage = findViewById(R.id.edt_message);

//        receive intent data passed.
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");
        mVehicleName.setText(name + " chat group");
        String groupName = name + " chat group";

        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        mPhone = prefs.getString("phone", "");
        mUsername1 = prefs.getString("username", "");

//      Initialize recyclerview
        mRecyclerView = findViewById(R.id.rv_chat);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      Initialize DB
        assert name != null;
        mDb = FirebaseDatabase.getInstance().getReference().child("Chats").child(groupName);
        mDb1 = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mUid = mCurrentUser.getUid();

//      query db
        FirebaseRecyclerOptions<ChatModel> options
                = new FirebaseRecyclerOptions.Builder<ChatModel>()
                .setQuery(mDb, ChatModel.class)
                .build();

        //      Initialize and set adapter
        mChatAdapter = new ChatAdapter(options, Chat.this, this);
        mRecyclerView.setAdapter(mChatAdapter);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSend.setOnClickListener(view -> {
                    String group = mVehicleName.getText().toString();
                    String msg = mEdtMessage.getText().toString().trim();
                    String messageSender = mUsername1;

                    if (msg.isEmpty()) {
                        Toast.makeText(Chat.this, "You can not send a blank message", Toast.LENGTH_LONG).show();
                    } else {
                        // Read the input field and push a new instance of ChatModel to the Firebase database
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Chats").child(group)
                                .push()
                                .setValue(new ChatModel(msg, messageSender));

                        // Clear the input
                    }
                    mEdtMessage.setText("");
                });

            }
        });

    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart() {
        super.onStart();
        mChatAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
        mChatAdapter.stopListening();

    }
}