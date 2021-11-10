package com.alexis.daygoes.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alexis.daygoes.Fragments.GridFragment;
import com.alexis.daygoes.Fragments.ImageFragment;
import com.alexis.daygoes.Fragments.VideosFragment;

public class ViewPagerAdapter1 extends FragmentStatePagerAdapter {
    public ViewPagerAdapter1(@NonNull FragmentManager fm) {
        super(fm);
    }
    public ViewPagerAdapter1(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return new VideosFragment();
 }


        @Nullable
        @Override
    public CharSequence getPageTitle(int position) {


            return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
