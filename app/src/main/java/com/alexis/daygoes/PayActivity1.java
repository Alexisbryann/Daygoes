//package com.alexis.daygoes;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.alexis.daygoes.Network.CheckInternetConnection;
//
//public class PayActivity1 extends AppCompatActivity {
//
//    private TextView mTv_name;
//    private TextView mAmt;
//    private ProgressBar mProgress_pay;
//
//    private DarajaApiClient mApiClient;
//    private Button mOkButton;
//    private TextView mError;
//    private TextView mTv_till;
//    private EditText mPhone;
//    private String mPhone_number;
//    private String mAmount;
//    private String mTillNumber;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pay);
//
//        //check Internet Connection
//        new CheckInternetConnection(this).checkConnection();
//
////        mTv_name = findViewById(R.id.tv_name_pay);
//        mTv_till = findViewById(R.id.tv_till_pay);
////        mAmt = findViewById(R.id.edt_amount_pay);
////        mProgress_pay = findViewById(R.id.progress_pay);
////        mPhone = findViewById(R.id.edt_phone_number);
////        mOkButton = findViewById(R.id.button_OK);
////        mError = findViewById(R.id.text_view_error_message);
//
////        mApiClient = new DarajaApiClient();
////        getAccessToken();
//
////        mOkButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                mError.setVisibility(View.INVISIBLE);
//
////                mPhone_number = mPhone.getText().toString().trim();
////                mAmount = mAmt.getText().toString().trim();
////                mTillNumber = mTv_till.toString().trim();
//
////                if (mPhone_number.isEmpty()) {
////                    mPhone.setError("Enter your phone number");
////                } else {
////                    if (mAmount.isEmpty()) {
////                        mAmt.setError("Please enter amount you want to pay");
////                    } else {
////                        performSTKPush(mPhone_number, mAmount);
////                    }
////                }
//            }
////        });
//
////    }
//
////    public void getAccessToken() {
////        mApiClient.setGetAccessToken(true);
////        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
////            @Override
////            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
////
////                if (response.isSuccessful()) {
////                    mApiClient.setAuthToken(response.body().accessToken);
////                }
////            }
////
////            @Override
////            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
////
////            }
////        });
////    }
//
////    private void performSTKPush(String phone_number, String amount) {
////
////        mProgress_pay.setVisibility(View.VISIBLE);
////        String timestamp = Utils.getTimestamp();
////        STKPush stkPush = new STKPush(
//////                BUSINESS_SHORT_CODE,
////                mTillNumber,
//////                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
////                Utils.getPassword(mTillNumber, PASSKEY, timestamp),
////                timestamp,
////                TRANSACTION_TYPE,
////                String.valueOf(amount),
////                Utils.sanitizePhoneNumber(phone_number),
//////                PARTYB
////                mTillNumber,
////                Utils.sanitizePhoneNumber(phone_number),
////                CALLBACKURL,
////                "Pay " + mTv_name, //Account reference
////                "Fare payment"  //Transaction description
////        );
////
////        mApiClient.setGetAccessToken(false);
////
////        //Sending the data to the Mpesa API, remember to remove the logging when in production.
////        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
////            @Override
////            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
////                mProgress_pay
////                        .setVisibility(View.INVISIBLE);
////                try {
////                    if (response.isSuccessful()) {
////                        mError.setVisibility(View.VISIBLE);
////                        mError.setText(R.string.success);
////                        Timber.d("post submitted to API. %s", response.body());
////                    } else {
////                        mError.setVisibility(View.VISIBLE);
////                        mError.setText(R.string.error);
////                        Log.e("TAG", response.errorBody().string());
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////
////            @Override
////            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
////                mProgress_pay.setVisibility(View.INVISIBLE);
////                Timber.e(t);
////            }
////        });
////
////
////    }
//}