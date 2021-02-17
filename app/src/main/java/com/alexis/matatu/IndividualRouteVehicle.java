package com.alexis.matatu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class IndividualRouteVehicle extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String mUserId;
    private SliderLayout mSliderShow;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;
    private SliderLayout mSlider;
    private ImageView mLike;
    private ImageView mFavourite;
    private ImageView mShare;
    private TextView mNumOfLikes;
    private TextView mNumOfFavs;
    private TextView mNumOfDislikes;
    private MaterialButton mPay;
    private RatingBar mRatingBar;
    private ImageView mDislike;
    private String mName;
    private long mRating;
    private TextView mTv_rating_comments;
    private DatabaseReference mDb;
    private String mImage1;
    private String mImage2;
    private String mImage3;
    private String mImage4;
    private String mImage5;
    private String mPlate;
    private String mRoute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_route_vehicle);

        mAuth = FirebaseAuth.getInstance();
        mUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");
        mPlate = i.getStringExtra("PLATE_KEY");
        mRoute = i.getStringExtra("ROUTE_KEY");

        inflateImageSlider();
        inflateViews();
        getIntentData();
        iconInitialize();
        displayNumberOfLikes();
        displayNumberOfFavourites();
        displayNumberOfDislikes();
        displayRatings();
        iconInitialize();

    }

    private void getIntentData() {
        //retrieving data using intent

        mTv_name.setText(mName);
        mTv_plate.setText(mPlate);
        mTv_route.setText(mRoute);
        getSupportActionBar().setTitle(mName);
    }

    private void inflateViews() {
        mTv_name= findViewById(R.id.tv_matatu_name);
        mTv_plate=findViewById(R.id.tv_plate);
        mTv_route=findViewById(R.id.tv_sacco);
        mSlider=findViewById(R.id.slider);
        mLike=findViewById(R.id.img_like);
        mFavourite=findViewById(R.id.img_favourite);
        mDislike = findViewById(R.id.img_dislike);
        mShare=findViewById(R.id.img_share);
        mPay=findViewById(R.id.btn_pay);
        mRatingBar=findViewById(R.id.ratingBar);
        mNumOfLikes = findViewById(R.id.tv_likes_no);
        mNumOfFavs = findViewById(R.id.tv_favourites_no);
        mNumOfDislikes = findViewById(R.id.tv_dislikes_no);
        mTv_rating_comments = findViewById(R.id.tv_rating_comments);
    }

    private void iconInitialize() {
        checkLikes();
        mLike.setOnClickListener(v -> onLikeClicked());

        checkFavourites();
        mFavourite.setOnClickListener(v -> onFavouriteClicked());

        mShare.setOnClickListener(v -> {
            Toast toast = Toast.makeText(IndividualRouteVehicle.this, "Share", Toast.LENGTH_LONG);
            toast.show();
        });

        checkDislikes();
        mDislike.setOnClickListener(v -> onDislikeClicked());

        mRatingBar.setRating(setRatings());

        mPay.setOnClickListener(v -> {
            Intent intent = new Intent(IndividualRouteVehicle.this, PayActivity.class);
            startActivity(intent);
        });
    }
    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        mSliderShow = findViewById(R.id.slider);

        mDb.child(mName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    mImage1 = dataSnapshot.child("image1").getValue(String.class);
                    mImage2 = dataSnapshot.child("image2").getValue(String.class);
                    mImage3 = dataSnapshot.child("image3").getValue(String.class);
                    mImage4 = dataSnapshot.child("image4").getValue(String.class);
                    mImage5 = dataSnapshot.child("image5").getValue(String.class);

                    ArrayList<String> sliderImages = new ArrayList<>();

                    sliderImages.add(mImage1);
                    sliderImages.add(mImage2);
                    sliderImages.add(mImage3);
                    sliderImages.add(mImage4);
                    sliderImages.add(mImage5);

                    for (String s : sliderImages) {
                        DefaultSliderView sliderView = new DefaultSliderView(IndividualRouteVehicle.this);
                        sliderView.image(s);
                        mSliderShow.addSlider(sliderView);
                    }
                    mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private float setRatings() {
        DatabaseReference checkRating = FirebaseDatabase.getInstance().getReference("Ratings")
                .child(mName)
                .child("User Ratings");

        checkRating
                .child(mUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            mRating =  snapshot.child("Rating").getValue(long.class);
                            mRatingBar.setRating(mRating);
                            mTv_rating_comments.setText("You have given "+mName+" a " + mRating + " star rating!");
                        } else {
                            mTv_rating_comments.setText("You are yet to rate " + mName);
                            getRatings();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return mRating;
    }


    @SuppressLint("SetTextI18n")
    public void getRatings() {
        mRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {

            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Ratings")
                    .child(mName).child("User Ratings").child(mUserId).child("Rating");

            dbRef.setValue((float) rating);
            setAverage();
        });
    }
    public void setAverage() {

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Ratings")
                .child(mName).child("User Ratings");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long total = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    long rating = ds.child("Rating").getValue(long.class);
                    total = total + rating;
                }
                long average = total / dataSnapshot.getChildrenCount();

                final DatabaseReference newRef = FirebaseDatabase.getInstance()
                        .getReference("Ratings")
                        .child(mName);

                final DatabaseReference vehicleRef = FirebaseDatabase.getInstance().getReference("Vehicles")
                        .child(mName);

                newRef.child("averageRating").setValue(average);

                vehicleRef.child("rating").setValue(average);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    private void displayRatings() {
        getRatings();
    }
    private void checkLikes() {
        DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(mName)
                .child(mUserId);

        likesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mLike.setColorFilter(Color.rgb(0, 100, 0), PorterDuff.Mode.SRC_IN);
                    mLike.setSelected(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void displayNumberOfLikes() {
        DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(mName);

        likesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        long numOfLikes = dataSnapshot.getChildrenCount();
                        mNumOfLikes.setText(numOfLikes + "");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onLikeClicked() {

        DatabaseReference liked = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(mName).child(mUserId);
        DatabaseReference disliked = FirebaseDatabase.getInstance().getReference().child("Dislikes")
                .child(mName);

        liked.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(IndividualRouteVehicle.this, "Already liked " + mName, Toast.LENGTH_LONG).show();
                } else {
                    liked.setValue(mUserId);
                    disliked.child(mUserId).removeValue();
                    mDislike.setColorFilter(Color.rgb(221,221,221), PorterDuff.Mode.SRC_IN);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void checkFavourites() {
        DatabaseReference favRef = FirebaseDatabase.getInstance().getReference()
                .child("Favourites")
                .child(mName)
                .child(mUserId);

        favRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mFavourite.setColorFilter(Color.rgb(255, 191, 0), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void displayNumberOfFavourites() {
        DatabaseReference FavRef = FirebaseDatabase.getInstance().getReference()
                .child("Favourites")
                .child(mName);

        FavRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        long numOfFavs = dataSnapshot.getChildrenCount();
                        mNumOfFavs.setText(numOfFavs + "");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onFavouriteClicked() {

        DatabaseReference favourites = FirebaseDatabase.getInstance().getReference().child("Favourites")
                .child(mName).child(mUserId);

        favourites.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(IndividualRouteVehicle.this, "Already added " + mName + " to your favourites", Toast.LENGTH_LONG).show();

                } else {
                    favourites.setValue(mUserId);
                    mFavourite.setColorFilter(Color.rgb(255, 191, 0), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void checkDislikes() {

        DatabaseReference DislikesRef = FirebaseDatabase.getInstance().getReference()
                .child("Dislikes")
                .child(mName)
                .child(mUserId);

        DislikesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mDislike.setColorFilter(Color.rgb(255,0,0), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void displayNumberOfDislikes() {
        DatabaseReference DislikesRef = FirebaseDatabase.getInstance().getReference()
                .child("Dislikes")
                .child(mName);

        DislikesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        long numOfDislikes = dataSnapshot.getChildrenCount();
                        mNumOfDislikes.setText(numOfDislikes + "");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onDislikeClicked() {

        DatabaseReference liked = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(mName);
        DatabaseReference disliked = FirebaseDatabase.getInstance().getReference().child("Dislikes")
                .child(mName).child(mUserId);

        disliked.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(IndividualRouteVehicle.this, "Already disliked "+mName, Toast.LENGTH_LONG).show();

                } else {
                    disliked.setValue(mUserId);
                    liked.child(mUserId).removeValue();
                    mDislike.setColorFilter(Color.rgb(255,0,0),PorterDuff.Mode.SRC_IN);
                    mLike.setColorFilter(Color.rgb(221,221,221), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
