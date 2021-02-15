package com.alexis.matatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alexis.matatu.Adapters.ViewPagerAdapter;
import com.alexis.matatu.Models.User;
import com.alexis.matatu.Uitility.PicassoCircleTransformation;
import com.alexis.matatu.usersession.UserSession;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private View mHeaderView;
    private TextView mUsername;
    private TextView mEmail;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private ImageView mImgLogo;
    private UserSession session;
    private DatabaseReference mDatabase;
    private String mUid;
    private String mUsername1;
    private String mPhone;

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUid = mCurrentUser.getUid();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextAppearance(this, R.style.almendraText);

        mDrawer = findViewById(R.id.drawer_layout);

        ViewPager viewPager = findViewById(R.id.viewPager2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mHeaderView = mNavigationView.getHeaderView(0);

        mUsername = mHeaderView.findViewById(R.id.tv_username);
        mEmail = mHeaderView.findViewById(R.id.tv_email);
        mImgLogo = mHeaderView.findViewById(R.id.img_logo);

        // Set username & email

        mUsername1 = getIntent().getStringExtra("Username");
        mPhone = getIntent().getStringExtra("Phone");
        Picasso.with(MainActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(mImgLogo);

        mEmail.setText(mPhone);
        mUsername.setText("Welcome " + mUsername1);

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setBackgroundColor(getResources().getColor(R.color.cardBg));

//        if (session.getFirstTime()) {
//            //tap target view
//            tapview();
//            session.setFirstTime(false);
//        }
    }

    private void tapview() {

        new TapTargetSequence(this)
                .targets(
                        TapTarget.forToolbarNavigationIcon(mToolbar, "Navigation Drawer", "You can Edit profile, read about the app, give us feedback,contact help, sign out, get an app tour and explore the app from here!")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorPrimaryBlack)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.colorPrimaryBlack)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccentAmber),

                        TapTarget.forView(findViewById(R.id.spinner), "Spinner", "YYou can select to filter vehicles by either popularity or your favourites!")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorAccentAmber)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.colorPrimaryBlack)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorShimmer),

                        TapTarget.forView(findViewById(R.id.tabLayout), "Swipe tabs", "Here you can choose whether to view vehicles, routes or hype!")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorAccentAmber)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.colorPrimaryBlack)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorShimmer))
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
//                        session.setFirstTime(false);
                        Toast.makeText(MainActivity.this, " You are ready to go !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                    }
                }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit_profile) {
            Intent i = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(i);
        }
        if (id == R.id.explore) {
            tapview();
        }
        if (id == R.id.sign_out) {
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}