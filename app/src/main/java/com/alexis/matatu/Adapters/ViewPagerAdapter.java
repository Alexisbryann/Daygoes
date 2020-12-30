package com.alexis.matatu.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alexis.matatu.MatatuListFragment;
import com.alexis.matatu.RoutesFragment;
import com.alexis.matatu.VehicleFragment;

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
            fragment = new MatatuListFragment();
        }else if (position == 1){
            fragment = new RoutesFragment();
        }
        assert fragment != null;
        return fragment;    }


        @Nullable
        @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0){
            title = "View vehicles";
        }
        else if (position==1) {
            title = "Routes";
        }
//        else if (position==2){
//            title = "Black Friday";
//        }
//        else if (position==3){
//            title = "Help";
//        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
