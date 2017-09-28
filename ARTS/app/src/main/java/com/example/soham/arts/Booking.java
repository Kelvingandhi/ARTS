package com.example.soham.arts;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.soham.adapters.MyFragmentPagerAdapter;
import com.example.soham.booking.cancel_bookng;
import com.example.soham.booking.past_booking;
import com.example.soham.booking.upcoming_booking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 100HM on 22-02-2017.
 */



public class Booking extends AppCompatActivity {

    Toolbar toolbar ;
    TabLayout tabLayout ;
    ViewPager viewPager ;
    BookingFragmentAdapter fragmentAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Booking");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);

        //setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcomming"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancel"));

        fragmentAdapter = new BookingFragmentAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.help_icon:

                Toast.makeText(Booking.this,"ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



/*
public class Booking extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    ViewPager viewpager;

    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Booking");

        initViewPager();
        initTabHost();


    }



    private void initTabHost() {
        // TODO Auto-generated method stub
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

        String[] tabName={"Past booking","canceled booking","upcoming booking"};
        for(int i=0;i<tabName.length;i++)
        {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabName[i]);
            tabSpec.setIndicator(tabName[i]);
            tabSpec.setContent(new fackcontent(getApplicationContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
    }


    public class fackcontent implements TabHost.TabContentFactory
    {
        Context context;
        public fackcontent(Context mcontext) {
            // TODO Auto-generated constructor stub
            context = mcontext ;
        }

        @Override
        public View createTabContent(String arg0) {
            // TODO Auto-generated method stub

            View fackview = new View(context);
            fackview.setMinimumHeight(0);
            fackview.setMinimumWidth(0);
            return fackview;
        }

    }

    private void initViewPager() {

        viewpager = (ViewPager)findViewById(R.id.view_pager);

        List<Fragment> listFragments =new ArrayList<Fragment>();
        listFragments.add(new past_booking());
        listFragments.add(new cancel_bookng());
        listFragments.add(new upcoming_booking());

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
        viewpager.setAdapter(myFragmentPagerAdapter);
        // Set up the action bar.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //viewTabListener
    @Override
    public void onTabChanged(String arg0) {
        // TODO Auto-generated method stub
        int selectedItem=tabHost.getCurrentTab();
        viewpager.setCurrentItem(selectedItem);


    }

    //viewPagerListener
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageSelected(int selectedItem) {
        // TODO Auto-generated method stub
        tabHost.setCurrentTab(selectedItem);
    }


}
*/