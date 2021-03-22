package com.alexis.matatu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Adapters.ChatAdapter;
import com.alexis.matatu.Models.ChatModel;
import com.alexis.matatu.Models.ChatModel1;
import com.alexis.matatu.Models.ChatModel2;
import com.alexis.matatu.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {

    private ImageButton mSend;
    private EditText mEdtMessage;
    private ChatAdapter mChatAdapter;
    private String mUsername1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        TextView vehicleName = findViewById(R.id.tv_vehicle_name);
        mSend = findViewById(R.id.fab_send);
        mEdtMessage = findViewById(R.id.edt_message);

//      receive intent data passed.
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");
        vehicleName.setText(name + " chat group");
        String groupName = name + " posts";

        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        mUsername1 = prefs.getString("username", "");

//      Initialize recyclerview
        RecyclerView recyclerView = findViewById(R.id.rv_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//      Initialize DB
        assert name != null;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Chats").child(groupName);

//      query db
        FirebaseRecyclerOptions<ChatModel1> options
                = new FirebaseRecyclerOptions.Builder<ChatModel1>()
                .setQuery(db, ChatModel1.class)
                .build();

        //      Initialize and set adapter
        mChatAdapter = new ChatAdapter(options, Chat.this, this);
        recyclerView.setAdapter(mChatAdapter);

        mSend.setOnClickListener(v -> mSend.setOnClickListener(view -> {
            String msg = mEdtMessage.getText().toString().trim();
            String messageSender = mUsername1;

            if (msg.isEmpty()) {
                Toast.makeText(Chat.this, "You can not send a blank message", Toast.LENGTH_LONG).show();
            } else {
                // Read the input field and push a new instance of ChatModel to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Chats").child(groupName)
                        .push()
                        .setValue(new ChatModel(msg, messageSender,null));
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("ChatGroups")
                        .child(groupName)
                        .setValue(new ChatModel2(groupName));

                // Clear the input
            }
            mEdtMessage.setText("");
        }));

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