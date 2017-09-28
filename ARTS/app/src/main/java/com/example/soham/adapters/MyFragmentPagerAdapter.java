package com.example.soham.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 100HM on 22-02-2017.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listFragments) {
        super(fm);
        this.listFragments = listFragments;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listFragments.size();
    }

}
