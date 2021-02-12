package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexis.matatu.Models.User;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Logging";
    private FirebaseAuth mAuth;
    private String mAuthVerificationId;
    private FirebaseUser mCurrentUser;
    private ProgressBar mProgressBar;

    String email, password, mUsername2;
    private Button mRegister;
    private EditText mEmail;
    private EditText mPassword;
    private DatabaseReference mDatabase;
    private String mUserId;
    private User mUser;
    private String mUid;
    private TextView mLogin_here;
    private String mUsername;
    private ImageView mImgLogo;
    private EditText mUsername1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mUsername1 = findViewById(R.id.edt_username);
        mEmail = findViewById(R.id.e_mail);
        mPassword = findViewById(R.id.edt_password);
        mRegister = findViewById(R.id.btn_register);
        mLogin_here = findViewById(R.id.tv_login_here);
        mProgressBar = findViewById(R.id.progressBar2);
        mImgLogo = findViewById(R.id.img_logo);

        Picasso.with(RegisterActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);

        mRegister.setOnClickListener(view -> {
            validateData();
        });
        mLogin_here.setOnClickListener(v -> {
            backToLogin();
        });
    }

    private void registerUserToDatabase() {

        mProgressBar.setVisibility(View.VISIBLE);
        mRegister.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                    assert firebaseUser != null;
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterActivity.this, "Verification Link Sent Successfully.", Toast.LENGTH_SHORT).show();
                            mRegister.setEnabled(true);

                            if (task.isSuccessful()) {
                                onAuthSuccess(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Sign In Failed",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                mProgressBar.setVisibility(View.GONE);
                mRegister.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        mUsername = mUsername2;

        // Write new user
        writeNewUser(user.getUid(), mUsername, user.getEmail(),user.getDisplayName());

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

//    private String usernameFromEmail(String email) {
//        if (email.contains("@")) {
//            return email.split("@")[0];
//        } else {
//            return email;
//        }
//    }


    private void writeNewUser(String userId, String username, String email, String displayName) {
        User user = new User(username, email,displayName);

        mDatabase.child("Users").child(userId).setValue(user);
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