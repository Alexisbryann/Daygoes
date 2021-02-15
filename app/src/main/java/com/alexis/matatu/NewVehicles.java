package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alexis.matatu.Adapters.ChatAdapter;
import com.alexis.matatu.Models.ChatModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewVehicles extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDb;
    private String mUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_vehicles);

//        Initialize recyclerview
        mRecyclerView = findViewById(R.id.rv_new_vehicle);
        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//      Initialize DB
        mDb = FirebaseDatabase.getInstance().getReference().child("Chats");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mUid = mCurrentUser.getUid();

//      query db
        FirebaseRecyclerOptions<ChatModel> options
                = new FirebaseRecyclerOptions.Builder<ChatModel>()
                .setQuery(mDb, ChatModel.class)
                .build();

        //      Initialize and set adapter
//        mChatAdapter = new ChatAdapter(options, Chat.this, this);
//        mRecyclerView.setAdapter(mChatAdapter);

    }
}