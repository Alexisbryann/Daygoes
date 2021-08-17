package com.alexis.daygoes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.alexis.daygoes.Adapters.ViewPagerAdapter;
import com.alexis.daygoes.Adapters.ViewPagerAdapter1;
import com.alexis.daygoes.Network.CheckInternetConnection;
import com.google.android.material.tabs.TabLayout;

public class MediaActivity extends AppCompatActivity {
    private TabLayout mTabLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        ViewPager viewPager = findViewById(R.id.media_viewPager);
        ViewPagerAdapter1 viewPagerAdapter1 = new ViewPagerAdapter1(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter1);

        mTabLayout = findViewById(R.id.tabLayout2);
        mTabLayout.setupWithViewPager(viewPager);

    }
}
