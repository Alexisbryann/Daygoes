package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexis.matatu.Models.Chats;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {

    private TextView mUsername;
    private TextView mTimestamp;
    private TextView mMessage;
    private FloatingActionButton mSend;
    private ImageView mMedia;
    private EditText mEdtMessage;
    private TextView mVehicleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mVehicleName = findViewById(R.id.tv_vehicle_name);
        mUsername = findViewById(R.id.tv_username);
        mTimestamp = findViewById(R.id.tv_time_stamp);
        mMessage = findViewById(R.id.tv_message);
        mSend = findViewById(R.id.fab_send);
        mMedia = findViewById(R.id.img_upload_media);
        mEdtMessage = findViewById(R.id.edt_message);


        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");

        mVehicleName.setText(name + " CHAT GROUP");

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String group = mVehicleName.getText().toString();
                        String msg = mEdtMessage.getText().toString();
                        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                        // Read the input field and push a new instance of Chats to the Firebase database
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Chats").child(group)
                                .push()
                                .setValue(new Chats(msg,username));

                        // Clear the input
                        mEdtMessage.setText("");
                    }
                });
            }
        });

    }
}