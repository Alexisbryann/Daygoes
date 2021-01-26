package com.alexis.matatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ProgressBar mProgressBar;
    private Button mLogin;
    private EditText mEmail;
    private EditText mPassword;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password);
        mLogin = findViewById(R.id.button_login);
        mRegister = findViewById(R.id.btn_register);
        mProgressBar = findViewById(R.id.progressBar);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                //login user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            mProgressBar.setVisibility(View.GONE);

                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                    mPassword.setError(Login.this.getString(R.string.pass_less_than_six));
                                } else {
                                    Toast.makeText(Login.this, Login.this.getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Login.this.startActivity(new Intent(Login.this, MainActivity.class));
                                Login.this.finish();
                            }
                        });
            }
                        });
            }

//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                mProgressBar.setVisibility(View.INVISIBLE);
//                mSendOTP.setVisibility(View.GONE);
//                mPhoneNumber.setError("OTP sending failed, please resend");
//                mResendOTP.setVisibility(View.VISIBLE);
//                mPhoneNumber.setText("");
//            }
//
//            @Override
//            public void onCodeSent(final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
////                new android.os.Handler().postDelayed(
////                        new Runnable() {
////                            public void run() {
//                Intent otpIntent = new Intent(Login.this, Register.class);
//                otpIntent.putExtra("AuthCredentials", s);
//                startActivity(otpIntent);
//            }
////                        },
////                        10000);
//            }
//        };


    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            sendUserToHome();
//                            // Sign in success, update UI with the signed-in user's information
//                            // ...
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                                mPhoneNumber.setError("OTP sending failed, please resend");
//                            }
//                        }
//                        mProgressBar.setVisibility(View.INVISIBLE);
//                    }
//                });
//    }

    public void sendUserToHome() {
        Intent homeIntent = new Intent(Login.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}
//