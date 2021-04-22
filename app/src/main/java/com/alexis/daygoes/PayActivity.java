package com.alexis.daygoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexis.daygoes.Model.AccessToken;
import com.alexis.daygoes.Model.STKPush;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.Uitility.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.alexis.daygoes.Constants.BUSINESS_SHORT_CODE;
import static com.alexis.daygoes.Constants.CALLBACKURL;
import static com.alexis.daygoes.Constants.PARTYB;
import static com.alexis.daygoes.Constants.PASSKEY;
import static com.alexis.daygoes.Constants.TRANSACTION_TYPE;

public class PayActivity extends AppCompatActivity {

    private TextView mTv_till;
    private DatabaseReference mDb;
    private String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        mTv_till = findViewById(R.id.tv_till_pay);

        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");

        assert mName != null;
        mDb.child(mName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int till = dataSnapshot.child("till").getValue(int.class);
                mTv_till.setText(String.valueOf(till));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}