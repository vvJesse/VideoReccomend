package com.example.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class HomeAdapter extends FragmentPagerAdapter {
    private String[] mTitle;
    private ArrayList<Fragment> mFragment;

    public HomeAdapter(FragmentManager fm, String title[], ArrayList<Fragment> fragments) {
        super(fm);
        mTitle = title;
        mFragment = fragments;
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }
}
