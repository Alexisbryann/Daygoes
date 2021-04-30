package com.alexis.daygoes;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.alexis.daygoes.Adapters.ViewPagerAdapter;
import com.alexis.daygoes.Fragments.VehiclesFragment;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.alexis.daygoes.Uitility.PicassoCircleTransformation;
import com.alexis.daygoes.usersession.UserSession;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.webianks.easy_feedback.EasyFeedback;

import java.lang.reflect.Method;
import java.util.Objects;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {

    private Toolbar mToolbar;
    private TextView mUsername;
    private TextView mEmail;
    private UserSession session;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private TabLayout mTabLayout;

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                requestPermission(); // Code for permission
            }
        }

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextAppearance(this, R.style.almendraText);
        mToolbar.setTitleMarginStart(TEXT_ALIGNMENT_CENTER);

        mDrawer = findViewById(R.id.drawer_layout);

        ViewPager viewPager = findViewById(R.id.viewPager2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(viewPager);

        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mUsername = headerView.findViewById(R.id.tv_username);
        mEmail = headerView.findViewById(R.id.tv_email);
        ImageView imgLogo = headerView.findViewById(R.id.img_logo);

        Picasso.with(MainActivity.this).load(R.drawable.logo).transform(new PicassoCircleTransformation()).into(imgLogo);

        navigationView.setNavigationItemSelectedListener(this);

        getValues();
        setName();

        if (session.getFirstTime()) {
            tapview();
            session.setFirstTime(false);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void setAnimation() {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(400);
        slide.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(slide);
    }


    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setName() {
        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        String phone = prefs.getString("phone", "");
        String username1 = prefs.getString("username", "");
        mEmail.setText(phone);
        mUsername.setText("Welcome " + username1);
    }

    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getApplicationContext());

        //validating session
        session.isLoggedIn();

    }

    @SuppressLint("ResourceType")
    private void tapview() {

        new TapTargetSequence(this)
                .targets(
                        TapTarget.forToolbarNavigationIcon(mToolbar, "Navigation Drawer", "You can Edit profile, read about the app, give us feedback,contact help, get an app tour and explore the app from here!")
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

                        TapTarget.forView(findViewById(R.id.tabLayout), "Swipe tabs", "Here you can choose whether to view vehicles, routes or hype!")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorAccentAmber)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.colorPrimaryWhite)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccentGreen))
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards to the sequence
                    @Override
                    public void onSequenceFinish() {
                        session.setFirstTime(false);
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

    private void tapview1() {

        new TapTargetSequence(this)
                .targets(
                        TapTarget.forToolbarNavigationIcon(mToolbar, "You can Edit profile, read about the app, give us feedback,contact help, sign out, get an app tour and explore the app from here!", "")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorPrimaryBlack)
                                .titleTextSize(25)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.colorPrimaryBlack)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccentAmber),

                        TapTarget.forView(findViewById(R.id.rv_new_vehicles), "Newest vehicles will be added here!", "")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorPrimaryBlack)
                                .titleTextSize(25)
                                .descriptionTextSize(20).descriptionTypeface(Typeface.DEFAULT_BOLD)
                                .descriptionTextColor(R.color.colorPrimaryWhite)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccentGreen),

                        TapTarget.forView(findViewById(R.id.spinner), "You can select to filter vehicles by either popularity or your favourites!", "")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorPrimaryBlack)
                                .titleTextSize(25)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.colorPrimaryBlack)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccentAmber),

                        TapTarget.forView(findViewById(R.id.search_vehicle), "You can search for a particular vehicle here!", "")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorPrimaryBlack)
                                .titleTextSize(25)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.colorPrimaryBlack)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccentAmber),

                        TapTarget.forView(findViewById(R.id.tabLayout), "Here you can choose whether to view vehicles, routes or hype!", "")
                                .targetCircleColor(R.color.colorAccentRed)
                                .titleTextColor(R.color.colorAccentAmber)
                                .titleTextSize(25)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.colorPrimaryWhite)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccentGreen))
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards to the sequence
                    @Override
                    public void onSequenceFinish() {
                        session.setFirstTime(false);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.app_tour) {
            session.setFirstTimeLaunch(true);
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.help_center) {
            startActivity(new Intent(MainActivity.this, HelpCenter.class));
        }
        if (id == R.id.explore) {
            Objects.requireNonNull(mTabLayout.getTabAt(0)).select();
            tapview1();
        }
        if (id == R.id.feedback) {
            new EasyFeedback.Builder(MainActivity.this)
                    .withEmail("bryannkoech7@gmail.com")
                    .withSystemInfo()
                    .build()
                    .start();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void lockDrawer() {
        mDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        mDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED);
    }
}