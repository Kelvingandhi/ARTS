package com.example.kelvin.my_conductor_app.lost_and_found_files;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Kelvin on 3/6/2017.
 */
public class FragmentAdapterClass extends FragmentStatePagerAdapter {

    int TabCount;

    public FragmentAdapterClass(FragmentManager fragmentManager, int tabCount) {

        super(fragmentManager);

        this.TabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Pending_requests_l_f tab1 = new Pending_requests_l_f();
                return tab1;

            case 1:
                Completed_requests_l_f tab2 = new Completed_requests_l_f();
                return tab2;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
