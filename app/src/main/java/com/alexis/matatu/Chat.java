package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexis.matatu.Adapters.ChatAdapter;
import com.alexis.matatu.Adapters.VehiclesAdapter;
import com.alexis.matatu.Models.ChatModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {

    private FloatingActionButton mSend;
    private ImageView mMedia;
    private EditText mEdtMessage;
    private TextView mVehicleName;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private DatabaseReference mDb;
    private ChatAdapter mChatAdapter;

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

        //      Initialize recyclerview
        mRecyclerView = findViewById(R.id.rv_chat);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      Initialize DB
        assert name != null;
        mDb = FirebaseDatabase.getInstance().getReference().child("Chats").child(groupName);

//      query db
        FirebaseRecyclerOptions<ChatModel> options
                = new FirebaseRecyclerOptions.Builder<ChatModel>()
                .setQuery(mDb, ChatModel.class)
                .build();

        //      Initialize and set adapter
        mChatAdapter = new ChatAdapter(options, Chat.this,this);
        mRecyclerView.setAdapter(mChatAdapter);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChat();
            }
        });

    }

    private void sendChat() {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String group = mVehicleName.getText().toString();
                String msg = mEdtMessage.getText().toString().trim();
                String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                if (msg.isEmpty()) {
                    Toast.makeText(Chat.this, "You can not send a blank message", Toast.LENGTH_LONG).show();
                    mEdtMessage.setText("");
                } else {
                    // Read the input field and push a new instance of ChatModel to the Firebase database
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Chats").child(group)
                            .push()
                            .setValue(new ChatModel(msg,user));

                    // Clear the input
                    mEdtMessage.setText("");
                }
            }
        });

    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart()
    {
        super.onStart();
        mChatAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        mChatAdapter.stopListening();

    }
}