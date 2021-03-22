package com.alexis.matatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alexis.matatu.Network.CheckInternetConnection;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;

    String email, password, mUsername2;
    private Button mRegister;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mUsername1;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mAuth = FirebaseAuth.getInstance();

        mUsername1 = findViewById(R.id.edt_username);
        mEmail = findViewById(R.id.e_mail);
        mPassword = findViewById(R.id.edt_password);
        mRegister = findViewById(R.id.btn_register);
        TextView login_here = findViewById(R.id.tv_login_here);
        mProgressBar = findViewById(R.id.progressBar2);
        ImageView imgLogo = findViewById(R.id.img_logo);

        Picasso.with(RegisterActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(imgLogo);

        mRegister.setOnClickListener(view -> validateData());
        login_here.setOnClickListener(v -> backToLogin());
    }

    private void registerUserToDatabase() {

        mProgressBar.setVisibility(View.VISIBLE);
        mRegister.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                assert firebaseUser != null;
                firebaseUser.sendEmailVerification().addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegisterActivity.this, "Verification Link Sent Successfully.", Toast.LENGTH_SHORT).show();
                    mRegister.setEnabled(true);

                    if (task.isSuccessful()) {
                        onAuthSuccess(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Sign In Failed",
                                Toast.LENGTH_LONG).show();
                    }

                }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            mProgressBar.setVisibility(View.GONE);
            mRegister.setVisibility(View.VISIBLE);
        });
    }

    private void onAuthSuccess(FirebaseUser user) {

        // Write new user
//        writeNewUser(user.getUid(), mUsername, user.getEmail(),user.getDisplayName());

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void validateData() {
        mUsername2 = mUsername1.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString();

        if (mUsername2.isEmpty()) {
            mUsername1.setError(RegisterActivity.this.getString(R.string.email_empty));
        }
        if (email.isEmpty()) {
            mEmail.setError(RegisterActivity.this.getString(R.string.email_empty));
        }
        if (password.isEmpty()) {
            mPassword.setError(RegisterActivity.this.getString(R.string.password_empty));
        }
        if (password.length() < 6) {
            mPassword.setError(RegisterActivity.this.getString(R.string.pass_less_than_six));
        } else {
            registerUserToDatabase();
        }
    }

    private void backToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}