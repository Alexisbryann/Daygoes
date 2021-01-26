package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private static final String TAG = "Logging";
    private FirebaseAuth mAuth;
    private String mAuthVerificationId;
    private FirebaseUser mCurrentUser;
    private ProgressBar mProgressBar;

    String email,password;
    private Button mRegister;
    private EditText mE_mail;
    private EditText mPassword;
    private TextView mInformation;
    private TextView mInformation1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
//        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        mInformation = findViewById(R.id.txt_information_text);
        mInformation1 = findViewById(R.id.txt_information_text1);
        mE_mail = findViewById(R.id.e_mail);
        mPassword = findViewById(R.id.edt_password);
        mRegister = findViewById(R.id.btn_register);
        mProgressBar = findViewById(R.id.progressBar2);

        mRegister.setOnClickListener(view -> {
            email = mE_mail.getText().toString();
            password = mPassword.getText().toString();

            if (email.isEmpty()) {
                mInformation1.setText(R.string.email_empty);
            }if (password.isEmpty()){
                mInformation.setText(R.string.password_empty);
            }
            if (password.length()<6){
                mInformation.setText(R.string.pass_less_than_six);
            }else{
                mProgressBar.setVisibility(View.VISIBLE);
                registerUserToDatabase();
            }
        });
    }

    private void registerUserToDatabase() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, task -> {

            if (task.isComplete()) {
//                addUserInDatabase((task.getResult()).getUser());
                mProgressBar.setVisibility(View.INVISIBLE);
                sendUserToLogin();
            }
//            if (!task.isSuccessful()){
//                Toast.makeText(Register.this, "Authentication failed." + task.getException(),
//                        Toast.LENGTH_SHORT).show();
//            }
//            } else if(!task.isSuccessful()) {
//
//                try {
//                    throw Objects.requireNonNull(task.getException());
//                } catch(FirebaseAuthWeakPasswordException e) {
//                    mInformation.setError("Weak password");
//                    mPassword.requestFocus();
//                } catch(FirebaseAuthInvalidCredentialsException e) {
//                    mInformation1.setError("Invalid e-mail");
//                    mE_mail.requestFocus();
//                } catch(FirebaseAuthUserCollisionException e) {
//                    mInformation1.setError("User exists");
//                    mE_mail.requestFocus();
//                } catch(Exception e) {
//                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
//                }
//            }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void addUserInDatabase(FirebaseUser user) {
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(user.getUid()).setValue(user);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mCurrentUser != null){
            sendUserToLogin();
        }
    }
    public void sendUserToLogin(){
        Intent homeIntent = new Intent(Register.this, MainActivity.class);
        startActivity(homeIntent);
        finishAffinity();
    }
    }