package com.alexis.daygoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private Button mBtnMyPosts;
    private Button mBtnChangeUsername;
    private CardView mChangeUsername;
    private Boolean mVisible;
    private Button mBtnChange;
    private TextInputEditText mEdtCurrentUsername;
    private TextInputEditText mEdtNewUsername;
    private String mCurrentUsername;
    private String mNewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mBtnMyPosts = findViewById(R.id.btn_my_posts);
        mBtnChangeUsername = findViewById(R.id.btn_change_username);
        mChangeUsername = findViewById(R.id.card_change_username);
        mBtnChange = findViewById(R.id.btn_change);
        mEdtNewUsername = findViewById(R.id.edt_new_username);

        mVisible = false;
        mChangeUsername.setVisibility(View.GONE);
        onClickListeners();

    }

    private void onClickListeners() {
        mBtnMyPosts.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MyPosts.class)));

        mBtnChangeUsername.setOnClickListener(v -> {
            cardVisibility();
        });
        mBtnChange.setOnClickListener(v -> {
            changeUsername();
        });
    }

    private void cardVisibility() {
        if (!mVisible) {
            mChangeUsername.setVisibility(View.VISIBLE);
            mVisible = true;
        } else {
            mChangeUsername.setVisibility(View.GONE);
            mVisible = false;
        }
    }

    private void changeUsername() {
        mNewUsername = Objects.requireNonNull(mEdtNewUsername.getText()).toString();

        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        String phone = prefs.getString("phone", "");

        if (!mNewUsername.equals("")){
            DatabaseReference username = FirebaseDatabase.getInstance().getReference("Users");
            assert phone != null;
            username.child(phone).child("username").setValue(mNewUsername);
            Toast.makeText(ProfileActivity.this, "Username changed", Toast.LENGTH_LONG).show();
        }else {
            mEdtNewUsername.setError("Username can not be blank");
            Toast.makeText(ProfileActivity.this, "Username can not be blank", Toast.LENGTH_LONG).show();
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", mNewUsername);
        editor.apply();

        mEdtNewUsername.setText("");


    }
}