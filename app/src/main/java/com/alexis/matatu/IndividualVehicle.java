package com.alexis.matatu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private ImageView mDislike;
    private RatingBar mRatingBar;
    private FloatingActionButton mFabChat;
    private String mUserId;
    private TextView mNumOfLikes;
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_vehicle);

        inflateImageSlider();

        mAuth = FirebaseAuth.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();


//        inflate the layout
        mFabChat = findViewById(R.id.fab_chat);
        mTv_name= findViewById(R.id.tv_matatu_name);
        mTv_plate=findViewById(R.id.tv_plate);
        mTv_route=findViewById(R.id.tv_sacco);
        mSlider=findViewById(R.id.slider);
        mLike=findViewById(R.id.img_like);
        mFavourite=findViewById(R.id.img_favourite);
        mShare=findViewById(R.id.img_share);
        mDislike=findViewById(R.id.img_dislike);
        mRatingBar=findViewById(R.id.ratingBar);
        mNumOfLikes = findViewById(R.id.tv_likes_no);

        //retrieving data using intent
        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");
        String plate = i.getStringExtra("PLATE_KEY");
        String route = i.getStringExtra("ROUTE_KEY");

        mTv_name.setText(mName);
        mTv_plate.setText(plate);
        mTv_route.setText(route);

        displayNumberOfLikes();
        iconInitialize();
    }
    @Override
    protected void onResume() {
        iconInitialize();
        super.onResume();
    }
    private void iconInitialize() {
        mLike.setOnClickListener(v -> {
            mLike.setColorFilter(Color.rgb(0, 100, 0));
            mDislike.setColorFilter(Color.rgb(221,221,221));
            Toast toast1 = Toast.makeText(IndividualVehicle.this, "Liked", Toast.LENGTH_LONG);
            toast1.show();
        });
        mDislike.setOnClickListener(v -> {
            mDislike.setColorFilter(Color.rgb(255, 0, 0));
            mLike.setColorFilter(Color.rgb(221,221,221));
            Toast toast = Toast.makeText(IndividualVehicle.this,"Disliked",Toast.LENGTH_LONG);
            toast.show();
        });

        mFavourite.setOnClickListener(v -> {
            mFavourite.setColorFilter(Color.rgb(255, 191, 0));
            Toast toast = Toast.makeText(IndividualVehicle.this,"Vehicle made favourite",Toast.LENGTH_LONG);
            toast.show();
        });
        mShare.setOnClickListener(v -> {
            Toast toast = Toast.makeText(IndividualVehicle.this,"Share",Toast.LENGTH_LONG);
            toast.show();
        });
        mRatingBar.getRating();

        mFabChat.setOnClickListener(v -> {


            Context context = v.getContext();
            Intent i = new Intent(context, Chat.class);
            i.putExtra("NAME_KEY", mTv_name.getText().toString());
            context.startActivity(i);

        });
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
        mSliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
    }


    public void displayNumberOfLikes(){
        DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference().child("Vehicle details").child(mName);
        likesRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    long numOfLikes = 0;
                    if(dataSnapshot.hasChild("likes")){
                        numOfLikes = dataSnapshot.child("Likes").getValue(Long.class);
                        mNumOfLikes.setText((int) numOfLikes);
                    }

                    //Populate numOfLikes on post i.e. textView.setText(""+numOfLikes)
                    //This is to check if the user has liked the post or not
                    mLike.setSelected(dataSnapshot.hasChild(mUserId));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onLikeClicked(View v, String postId, String userId){
        DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference().child("Vehicle details").child(mName).child("likes");
        likesRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long numLikes = 0;
                if(dataSnapshot.exists()){
                    numLikes = dataSnapshot.getValue(Long.class);
                }
                boolean isLiked = mLike.isSelected();
                if(isLiked){
                    //If already liked then user wants to unlike the post
//                    likesRef.set(numLikes-1);
                }else {
                    //If not liked already then user wants to like the post
//                    likesRef.set(numLikes+1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
