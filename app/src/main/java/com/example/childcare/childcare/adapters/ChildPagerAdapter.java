package com.example.childcare.childcare.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.childcare.childcare.ActivitiesFragment;
import com.example.childcare.childcare.DetailsFragment;
import com.example.childcare.childcare.PhotosFragment;

public class ChildPagerAdapter extends FragmentStatePagerAdapter {
    public ChildPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new DetailsFragment();
            case 1: return new ActivitiesFragment();
            case 2: return new PhotosFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Details";
            case 1: return "Activities";
            case 2: return "Photos";
            default: return null;
    }
    }
}
