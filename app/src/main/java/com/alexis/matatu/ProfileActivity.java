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
import android.widget.Toast;
import android.widget.Toolbar;

import com.alexis.matatu.Network.CheckInternetConnection;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private Button changeEmail, changePassword, changeUsername;
    private EditText oldEmail, newEmail, password, newPassword, oldUsername, newUsername;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBar1;
    private ImageView mImgLogo;
    private ProgressBar mProgressBar2;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String mUid;

    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        getSupportActionBar().setTitle("PROFILE");

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mUid = mCurrentUser.getUid();

        mImgLogo = findViewById(R.id.img_logo);

        Picasso.with(ProfileActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);

        authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        };

        changeEmail = findViewById(R.id.changeEmail);
        changePassword = findViewById(R.id.changePass);
        changeUsername = findViewById(R.id.changeUsername);

        oldEmail = findViewById(R.id.old_email);
        newEmail = findViewById(R.id.new_email);

        password = findViewById(R.id.password);
        newPassword = findViewById(R.id.newPassword);

        oldUsername = findViewById(R.id.old_username);
        newUsername = findViewById(R.id.new_username);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar1 = findViewById(R.id.progressBar1);
        mProgressBar2 = findViewById(R.id.progressBar2);


        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                if (user != null && !newEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "Email address is updated. Please sign in with new email address!", Toast.LENGTH_LONG).show();
                                        signOut();
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                    }
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    mProgressBar.setVisibility(View.GONE);
                }else if (oldEmail.getText().toString().trim().equals("")) {
                    oldEmail.setError("Enter old email");
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar1.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                        mProgressBar1.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                        } else {
                                            Toast.makeText(ProfileActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                        }
                                        mProgressBar1.setVisibility(View.GONE);
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    mProgressBar1.setVisibility(View.GONE);
                }
                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Enter old password");
                    mProgressBar1.setVisibility(View.GONE);
                }
            }
        });
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = newUsername.getText().toString();

                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child("Users")
                        .child(mUid)
                        .child("username");

                if (user != null && username.equals("")) {
                    newUsername.setError("Enter new username");
                }
                if (user != null && oldUsername.getText().toString().equals("")) {
                    oldUsername.setError("Enter Old username");
                }
                else {
                    userReference.setValue(username);
                    Toast.makeText(ProfileActivity.this,"Username successfully changed.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    //sign out method
    public void signOut() {
        auth.signOut();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }

    }
}