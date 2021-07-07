package com.alexis.daygoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

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
        mEdtCurrentUsername = findViewById(R.id.edt_current_username);
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
        mCurrentUsername = Objects.requireNonNull(mEdtCurrentUsername.getText()).toString();
        mNewUsername = Objects.requireNonNull(mEdtNewUsername.getText()).toString();
    }
}