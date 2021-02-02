package com.alexis.matatu.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alexis.matatu.VehiclesFragment;
import com.alexis.matatu.ProfilesFragment;
import com.alexis.matatu.RoutesFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new VehiclesFragment();
        }else if (position == 1){
            fragment = new RoutesFragment();
        }else if (position == 2){
            fragment = new ProfilesFragment();
        }
        assert fragment != null;
        return fragment;    }


        @Nullable
        @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0){
            title = "Vehicles";
        }
        else if (position==1) {
            title = "Routes";
        }
        else if (position==2){
            title = "Hype";
        }

        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
