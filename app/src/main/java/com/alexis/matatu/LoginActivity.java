package com.alexis.matatu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ProgressBar mProgressBar;
    private Button mLogin;
    private EditText mEmail;
    private EditText mPassword;
    private Button mRegister;
    private String mEmail1;
    private String mPassword1;
    private TextView mForgotPass;
    private ImageView mImgLogo;

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
        mForgotPass = findViewById(R.id.tv_forgot_password);
        mProgressBar = findViewById(R.id.progressBar);
        mImgLogo = findViewById(R.id.img_logo);

        Picasso.with(LoginActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);



        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmail1 = mEmail.getText().toString();
                mPassword1 = mPassword.getText().toString();
                if (mPassword1.isEmpty()) {
                    mPassword.setError(LoginActivity.this.getString(R.string.password_empty));
                }
                if (mEmail1.isEmpty()) {
                    mEmail.setError(LoginActivity.this.getString(R.string.email_empty));
                } else {
                    handleLogin();
                }
            }
        });


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void validateInput() {
//        if (mPassword1.length() < 6) {
//            mPassword.setError(LoginActivity.this.getString(R.string.pass_less_than_six));
//        }

    }

    private void handleLogin() {

        mProgressBar.setVisibility(View.VISIBLE);
        mLogin.setEnabled(false);
        //login user
        mAuth.signInWithEmailAndPassword(mEmail1, mPassword1)
                .addOnCompleteListener(task -> {
                    mProgressBar.setVisibility(View.GONE);
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                    } else {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mLogin.setEnabled(true);
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishAffinity();
                    }
                });
        sendToSharedPref();
    }

    private void sendToSharedPref() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",mEmail1 );
        editor.apply();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            sendUserToHome();
//        }
//        assert mCurrentUser != null;
//        mCurrentUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    User still exists and credentials are valid
//                    sendUserToHome();
//
//                }else {
//                    User has been disabled, deleted or login credentials are no longer valid,so send them to Login screen
//                    Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
                }
//            }
//        });


    }

    public void sendUserToHome() {
        Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finishAffinity();
    }
}