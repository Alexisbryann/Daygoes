package com.alexis.daygoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class ForgotPassword extends AppCompatActivity {

    private EditText mEdtEmail;
    private Button mResetPassword;
    private Button mBack;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    private ImageView mImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        mEdtEmail = findViewById(R.id.edt_email);
        mResetPassword = findViewById(R.id.btn_reset_password);
        mBack = findViewById(R.id.btn_back);
        mProgressBar = findViewById(R.id.progressBar);
        mImgLogo = findViewById(R.id.img_logo);

        Picasso.with(ForgotPassword.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);

        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReset();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void handleReset() {
        String email = mEdtEmail.getText().toString().trim();

        if (email.isEmpty()){
            mEdtEmail.setError(ForgotPassword.this.getString(R.string.email_empty));
        }else {
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }
    }
}