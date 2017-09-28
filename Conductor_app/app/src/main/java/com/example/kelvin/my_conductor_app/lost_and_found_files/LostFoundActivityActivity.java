package com.example.kelvin.my_conductor_app.lost_and_found_files;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.kelvin.my_conductor_app.R;

public class LostFoundActivityActivity extends AppCompatActivity {

    Toolbar toolbar ;
    TabLayout tabLayout ;
    ViewPager viewPager ;
    FragmentAdapterClass fragmentAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_found_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Lost and Found");


        tabLayout.addTab(tabLayout.newTab().setText("Pending Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed Requests"));

        fragmentAdapter = new FragmentAdapterClass(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(fragmentAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
