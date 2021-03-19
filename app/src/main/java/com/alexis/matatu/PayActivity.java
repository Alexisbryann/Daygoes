package com.alexis.matatu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.alexis.matatu.Network.CheckInternetConnection;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

public class PayActivity extends AppCompatActivity {

    private ImageView mImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
//        getSupportActionBar().setTitle("Pay Ride");

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mImgLogo = findViewById(R.id.img_logo);
        Picasso.with(PayActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);

    }
}