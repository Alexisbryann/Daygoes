package com.alexis.daygoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alexis.daygoes.Models.User;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.alexis.daygoes.usersession.UserSession;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class OtpConfirmationActivity extends AppCompatActivity {
    private UserSession session;
    private FirebaseAuth mAuth;
    private String mAuthVerificationId;
    private FirebaseUser mCurrentUser;
    private TextInputEditText mOtpVerification;
    private Button mVerifyOtp;
    private ProgressBar mProgressBar;
    private String mUsername;
    private String mPhone;
    private DatabaseReference mDatabase;
    private TextInputLayout mInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        session = new UserSession(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");
        mUsername = getIntent().getStringExtra("Username");
        mPhone = getIntent().getStringExtra("Phone");

        ImageView imgLogo = findViewById(R.id.img_logo);
        mInformation = findViewById(R.id.edit_text_enter_otp1);
        mOtpVerification = findViewById(R.id.edit_text_enter_otp);
        mVerifyOtp = findViewById(R.id.button_verify_otp);
        mProgressBar = findViewById(R.id.progressBar2);
        Picasso.with(OtpConfirmationActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(imgLogo);

        mVerifyOtp.setOnClickListener(view -> {
            String Otp = mOtpVerification.getText().toString();

            if (Otp.isEmpty()) {
                mInformation.setError("Please enter the otp sent to you");
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, Otp);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpConfirmationActivity.this, task -> {
                    if (task.isSuccessful()) {
                        //create shared preference and store data
                        sendUserToHome();
                        // Sign in success, update UI with the signed-in user's information
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
//                                mInformation.setVisibility(View.VISIBLE);
                            mInformation.setError("OTP verification failed, please try again.");
                        }
                    }
                    mVerifyOtp.setEnabled(true);
                    mProgressBar.setVisibility(View.INVISIBLE);
                });
        writeNewUser(mUsername, mPhone);


    }

    //    private void writeNewUser(String userId, String username, String phone) {
    private void writeNewUser(String username, String phone) {
        User user = new User(username, phone);

        mDatabase.child("Users").child(mPhone).setValue(user);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

    public void sendUserToHome() {

        session.createLoginSession(mUsername, mPhone);

        Intent homeIntent = new Intent(OtpConfirmationActivity.this, MainActivity.class);
        homeIntent.putExtra("Username", mUsername);
        homeIntent.putExtra("Phone", mPhone);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}
