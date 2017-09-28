package com.example.soham.arts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.soham.booking.cancel_bookng;
import com.example.soham.booking.past_booking;
import com.example.soham.booking.upcoming_booking;

/**
 * Created by 100HM on 10/05/2017.
 */


public class BookingFragmentAdapter extends FragmentStatePagerAdapter {

    int TabCount;

    public BookingFragmentAdapter(FragmentManager fragmentManager, int tabCount) {

        super(fragmentManager);

        this.TabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                past_booking tab1 = new past_booking();
                return tab1;

            case 1:
                upcoming_booking tab2= new upcoming_booking();
                return tab2;

            case 2:
                cancel_bookng tab3= new cancel_bookng();
                return tab3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return TabCount;
    }
}

