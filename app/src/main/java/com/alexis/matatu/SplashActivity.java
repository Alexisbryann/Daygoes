package com.alexis.matatu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.alexis.matatu.usersession.UserSession;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 7000;

    //to get user session data
    private UserSession session;
    private ImageView mImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        session =new UserSession(SplashActivity.this);
        mImgLogo = findViewById(R.id.img_logo);
        Picasso.with(SplashActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);

        // making notification bar transparent
        changeStatusBarColor();

        YoYo.with(Techniques.BounceInUp)
                .duration(7000)
                .playOn(findViewById(R.id.img_logo));

        YoYo.with(Techniques.FadeInLeft)
                .duration(3000)
                .playOn(findViewById(R.id.appname));

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashActivity.this,WelcomeActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}
