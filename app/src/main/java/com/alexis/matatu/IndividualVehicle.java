package com.alexis.matatu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class IndividualVehicle extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SliderLayout mSliderShow;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;
    private SliderLayout mSlider;
    private ImageView mLike;
    private ImageView mFavourite;
    private ImageView mShare;
    private RatingBar mRatingBar;
    private FloatingActionButton mFabChat;
    private String mUserId;
    private TextView mNumOfLikes;
    private String mName;
    private long mRating;
    private TextView mNumOfFavs;
    private TextView mTv_rating_comments;
    private ImageView mDislike;
    private TextView mNumOfDislikes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_vehicle);


        mAuth = FirebaseAuth.getInstance();
        mUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        inflateImageSlider();
        inflateViews();
        getIntentData();
        displayNumberOfLikes();
        displayNumberOfFavourites();
        displayNumberOfDislikes();
        displayRatings();
        iconInitialize();
    }

    private void inflateViews() {
//      inflate the layout
        mFabChat = findViewById(R.id.fab_chat);
        mTv_name = findViewById(R.id.tv_matatu_name);
        mTv_plate = findViewById(R.id.tv_plate);
        mTv_route = findViewById(R.id.tv_sacco);
        mSlider = findViewById(R.id.slider);
        mLike = findViewById(R.id.img_like);
        mFavourite = findViewById(R.id.img_favourite);
        mShare = findViewById(R.id.img_share);
        mDislike = findViewById(R.id.img_dislike);
        mRatingBar = findViewById(R.id.ratingBar);
        mNumOfLikes = findViewById(R.id.tv_likes_no);
        mNumOfFavs = findViewById(R.id.tv_favourites_no);
        mNumOfDislikes = findViewById(R.id.tv_dislikes_no);
        mTv_rating_comments = findViewById(R.id.tv_rating_comments);
    }

    private void getIntentData() {
        //retrieving data sent via intent
        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");
        String plate = i.getStringExtra("PLATE_KEY");
        String route = i.getStringExtra("ROUTE_KEY");
        mTv_name.setText(mName);
        mTv_plate.setText(plate);
        mTv_route.setText(route);
        getSupportActionBar().setTitle(mName);
    }

    @Override
    protected void onResume() {
        iconInitialize();
        super.onResume();
    }

    private void iconInitialize() {
        checkLikes();
        mLike.setOnClickListener(v -> onLikeClicked());

        checkFavourites();
        mFavourite.setOnClickListener(v -> onFavouriteClicked());

        mShare.setOnClickListener(v -> {
            Toast toast = Toast.makeText(IndividualVehicle.this, "Share", Toast.LENGTH_LONG);
            toast.show();
        });

        checkDislikes();
        mDislike.setOnClickListener(v -> onDislikeClicked());

        mFabChat.setOnClickListener(v -> {

            Context context = v.getContext();
            Intent i = new Intent(context, Chat.class);
            i.putExtra("NAME_KEY", mTv_name.getText().toString());
            context.startActivity(i);
        });

        mRatingBar.setRating(setRatings());
    }

    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        mSliderShow = findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya1.jpg?alt=media&token=8bdc129a-587b-40c9-bf07-135fc45a26af");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya2.jpg?alt=media&token=f41c9793-2214-4e89-a2d5-8bf47772d57b");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya3.jpg?alt=media&token=365909ab-0474-4a6b-9a78-dc8179d2add7");
        sliderImages.add("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/nganya4.jpg?alt=media&token=db170175-c382-47c5-a226-4ea425e6c028");

        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
            mSliderShow.addSlider(sliderView);
        }
        mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
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
            Toast.makeText(IndividualVehicle.this,""+rating+" star!",Toast.LENGTH_LONG).show();
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
                        mNumOfLikes.setText(numOfLikes + " likes");
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
                    Toast.makeText(IndividualVehicle.this, "Already liked this vehicle", Toast.LENGTH_LONG).show();
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
                        mNumOfFavs.setText(numOfFavs + " favourites");
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
                    Toast.makeText(IndividualVehicle.this, "Already added this vehicle to your favourites", Toast.LENGTH_LONG).show();

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
                        mNumOfDislikes.setText(numOfDislikes + " Dislikes");
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
                    Toast.makeText(IndividualVehicle.this, "Already disliked "+mName, Toast.LENGTH_LONG).show();

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