package com.alexis.daygoes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Explode;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alexis.daygoes.Models.FavouriteVehicleModel;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class IndividualVehicle extends AppCompatActivity {

    private SliderLayout mSliderShow;
    private TextView mTv_name;
    private TextView mTv_plate;
    private TextView mTv_route;
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
    private DatabaseReference mDb;
    private String mPlate;
    private String mRoute;
    private String mImage1;
    private String mImage2;
    private String mImage3;
    private String mImage4;
    private String mImage5;
    private long mNumOfLikes1;
    private String mName1;
    private String mImage11;
    private String mCapacity;
    private String mSacco;
    private String mPlate1;
    private long mRatings;
    private String mRoute1;
    private Button mMake_post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.individual_vehicle);
//        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.background1));

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");
        mPlate = i.getStringExtra("PLATE_KEY");
        mRoute = i.getStringExtra("ROUTE_KEY");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        mDb = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        inflateViews();
        inflateImageSlider();
        getIntentData();
        displayNumberOfLikes();
        displayNumberOfFavourites();
        displayNumberOfDislikes();
        displayRatings();
        iconInitialize();
    }

    private void setAnimation() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        explode.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

    }

    private void inflateViews() {
//      inflate the layout
        mFabChat = findViewById(R.id.fab_chat);
        mMake_post = findViewById(R.id.btn_make_post);
        mTv_name = findViewById(R.id.tv_matatu_name);
        mTv_plate = findViewById(R.id.tv_plate);
        mTv_route = findViewById(R.id.tv_sacco);
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
        mTv_name.setText(mName);
        mTv_plate.setText(mPlate);
        mTv_route.setText(mRoute);
//        getSupportActionBar().setTitle(mName);
    }

    @Override
    protected void onResume() {
        iconInitialize();
        super.onResume();
        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();
    }

    private void iconInitialize() {
        checkLikes();
        checkFavourites();
        checkDislikes();

        mLike.setOnClickListener(v -> onLikeClicked());
        mFavourite.setOnClickListener(v -> onFavouriteClicked());
        mShare.setOnClickListener(v -> {
            try {
                share();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        mDislike.setOnClickListener(v -> onDislikeClicked());
        mFabChat.setOnClickListener(v -> {

            Context context = v.getContext();
            Intent i = new Intent(context, Chat.class);
            i.putExtra("NAME_KEY", mTv_name.getText().toString());
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation((Activity)context);
            context.startActivity(i,options.toBundle());
        });
        mMake_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent i = new Intent(context, Posts.class);
                i.putExtra("NAME_KEY", mTv_name.getText().toString());
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation((Activity)context);
                context.startActivity(i,options.toBundle());
            }
        });

        mRatingBar.setRating(setRatings());
    }

    private void share() throws ExecutionException, InterruptedException {

        @SuppressLint("StaticFieldLeak")
        class myTask extends AsyncTask<Void, Void, Bitmap> {

            protected Bitmap doInBackground(Void... params) {
                Bitmap myBitmap = null;
                try {
                    java.net.URL url = new URL(mImage1);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);

                } catch (IOException e) {
                    // Log exception
                }
                return myBitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                //do stuff
            }
        }

        Bitmap returned_bitmap = new myTask().execute().get();

        final String appPackageName = this.getPackageName();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, check out this Vehicle on Daygoes !, Download the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), returned_bitmap, "title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share image via..."));

    }

    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        mSliderShow = findViewById(R.id.slider);

        mDb.child(mName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ignored : dataSnapshot.getChildren()) {

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
                        DefaultSliderView sliderView = new DefaultSliderView(IndividualVehicle.this);
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
                            mRating = snapshot.child("Rating").getValue(long.class);
                            mRatingBar.setRating(mRating);
                            mTv_rating_comments.setText("You have given " + mName + " a " + mRating + " star rating!");
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

                    for (DataSnapshot ignored : dataSnapshot.getChildren()) {
                        mNumOfLikes1 = dataSnapshot.getChildrenCount();
                        mNumOfLikes.setText(mNumOfLikes1 + "");
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
                    Toast.makeText(IndividualVehicle.this, "Already liked " + mName, Toast.LENGTH_LONG).show();
                } else {
                    liked.setValue(mUserId);
                    disliked.child(mUserId).removeValue();
                    mDislike.setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN);

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

                    for (DataSnapshot ignored : dataSnapshot.getChildren()) {
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
        DatabaseReference favourited = FirebaseDatabase.getInstance().getReference().child("Favourited")
                .child(mUserId).child(mName);

        favourites.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().removeValue();
                    favourited.getRef().removeValue();
                    mFavourite.setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN);
                    Toast.makeText(IndividualVehicle.this, "Removed " + mName + " from your favourites", Toast.LENGTH_LONG).show();

                } else {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("Vehicles")
                            .child(mName);

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ignored : snapshot.getChildren()) {

                                mName1 = snapshot.child("name").getValue(String.class);
                                mImage11 = snapshot.child("image1").getValue(String.class);
                                mCapacity = snapshot.child("capacity").getValue(String.class);
                                mSacco = snapshot.child("sacco").getValue(String.class);
                                mPlate1 = snapshot.child("plate").getValue(String.class);
                                mRatings = snapshot.child("rating").getValue(Long.class);
                                mRoute1 = snapshot.child("route").getValue(String.class);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    mFavourite.setColorFilter(Color.rgb(255, 191, 0), PorterDuff.Mode.SRC_IN);
                    favourites.setValue(mUserId).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            favourited.setValue(new FavouriteVehicleModel(mImage11, mName1, mSacco, mRoute1, mCapacity, mPlate1, mRatings));

                        }
                    });
                    Toast.makeText(IndividualVehicle.this, "Added " + mName + " to your favourites", Toast.LENGTH_LONG).show();

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
                    mDislike.setColorFilter(Color.rgb(255, 0, 0), PorterDuff.Mode.SRC_IN);
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
                    for (DataSnapshot ignored : dataSnapshot.getChildren()) {
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
                    Toast.makeText(IndividualVehicle.this, "Already disliked " + mName, Toast.LENGTH_LONG).show();

                } else {
                    disliked.setValue(mUserId);
                    liked.child(mUserId).removeValue();
                    mDislike.setColorFilter(Color.rgb(255, 0, 0), PorterDuff.Mode.SRC_IN);
                    mLike.setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}