package com.alexis.daygoes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private TextInputEditText mPhoneNumber1;
    private Button mSendOTP;
    private ProgressBar mProgressBar;
    private TextView mInformationText;
    private TextInputLayout mPhoneNumber;
    private TextInputLayout mUsername;
    private TextInputEditText mUsername1;
    private String mNumber;
    private String mUsername2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        ImageView imgLogo = findViewById(R.id.img_logo);
        mUsername = findViewById(R.id.editTextUsername);
        mUsername1 = findViewById(R.id.editTextUsername1);
        mPhoneNumber = findViewById(R.id.editTextPhone);
        mPhoneNumber1 = findViewById(R.id.editTextPhone1);
        mSendOTP = findViewById(R.id.button_send_otp);
        mProgressBar = findViewById(R.id.progressBar);
        mInformationText = findViewById(R.id.text_view_information_text);

        Picasso.with(LoginActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(imgLogo);

        mSendOTP.setOnClickListener(view -> {

            mNumber = mPhoneNumber1.getText().toString();
            mUsername2 = mUsername1.getText().toString();
            String completeNumber = "+254" + mNumber.replaceFirst("^0+(?!$)", "");

            if (mNumber.isEmpty()) {
                mPhoneNumber.setError("Phone number must be entered");
            }
            if (mUsername2.isEmpty()) {
                mUsername.setError("Username must be entered");
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mSendOTP.setEnabled(false);

                PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions
                        .newBuilder(FirebaseAuth.getInstance())
                        .setActivity(LoginActivity.this)
                        .setPhoneNumber(completeNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(mCallbacks)
                        .build());
//                    PhoneAuthProvider.verifyPhoneNumber(options);

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                mInformationText.setText(R.string.information_text);
                mInformationText.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                mSendOTP.setEnabled(true);
            }

            @Override
            public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Intent otpIntent = new Intent(LoginActivity.this, OtpConfirmationActivity.class);
                otpIntent.putExtra("AuthCredentials", s);
                otpIntent.putExtra("Username", mUsername2);
                otpIntent.putExtra("Phone", mNumber);
                startActivity(otpIntent);
                sendToSharedPref();
                finish();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

    private void sendToSharedPref() {

        SharedPreferences sharedPreferences = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", mNumber);
        editor.putString("username", mUsername2);
        editor.apply();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        sendUserToHome();
                        // Sign in success, update UI with the signed-in user's information
                        // ...
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            mInformationText.setVisibility(View.VISIBLE);
                            mInformationText.setText(R.string.info_text);
                        }
                    }
                    mSendOTP.setEnabled(true);
                    mProgressBar.setVisibility(View.INVISIBLE);
                });
    }

    public void sendUserToHome() {
        Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

}