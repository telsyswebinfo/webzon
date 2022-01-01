package com.webzon.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewPager extends FragmentPagerAdapter {
    private final List<Fragment> mList = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();

    public AdapterViewPager(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void addFragment(Fragment fragment) {
        mList.add(fragment);
//        mTitleList.add(title);
    }
//    public void addFragment(Fragment fragment, String title) {
//        mList.add(fragment);
//        mTitleList.add(title);
//    }
}