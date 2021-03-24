package com.alexis.daygoes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        ImageView imgLogo = findViewById(R.id.img_logo);
        Picasso.with(SplashActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(imgLogo);

        // making notification bar transparent
        changeStatusBarColor();

        YoYo.with(Techniques.BounceInUp)
                .duration(3000)
                .playOn(findViewById(R.id.img_logo));

        YoYo.with(Techniques.FadeInLeft)
                .duration(3000)
                .playOn(findViewById(R.id.appname));

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }, SPLASH_TIME_OUT);
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}
