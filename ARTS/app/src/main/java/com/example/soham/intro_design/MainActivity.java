package com.example.soham.intro_design;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.soham.arts.Login;
import com.example.soham.arts.R;

public class MainActivity extends AppCompatActivity {

    Button next,skip;

    private ViewPager viewPager;

    private ViewPageAAdapter viewPageAAdapter;

    private IntroManager introManager;

    private int[] screen_layouts;

    private TextView[] dots;

    private LinearLayout dotsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        introManager = new IntroManager(this);

        if(!introManager.Check())
        {

            launchHomeScreen();
            //finish();
            /*
            Intent i =new Intent(MainActivity.this,NewMain.class);
            startActivity(i);
            finish();
            */
        }

        if(Build.VERSION.SDK_INT>=21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.intro_design);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        skip = (Button) findViewById(R.id.into_btn_skip);
        next = (Button) findViewById(R.id.into_btn_next);

        screen_layouts = new int[]{R.layout.activity_screen1,
                                    R.layout.activity_screen2,
                                    R.layout.activity_screen3,
                                    R.layout.activity_screen4};


        addButtonDots(0);
        changeStatusBarColor();
        viewPageAAdapter = new ViewPageAAdapter();
        viewPager.setAdapter(viewPageAAdapter);
        viewPager.addOnPageChangeListener(viewListener);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
                /*
                Intent i =new Intent(MainActivity.this,NewMain.class);
                startActivity(i);
                finish();
                */
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = getItems(+1);
                if(current<screen_layouts.length)
                {
                    viewPager.setCurrentItem(current);
                }
                else
                {
                    launchHomeScreen();
                /*    Intent i =new Intent(MainActivity.this,NewMain.class);
                    startActivity(i);
                    finish();
                    */
                }
            }
        });

    }

    private void launchHomeScreen() {
        introManager.setFirst(false);
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }


    private int getItems(int i)
    {
        return viewPager.getCurrentItem()+i;
    }

    private  void addButtonDots(int position)
    {
        dots = new TextView[screen_layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);

        dotsLayout.removeAllViews();

            for(int i=0;i<dots.length;i++)
            {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(colorInactive[position]);
                dotsLayout.addView(dots[i]);
            }

            if(dots.length>0)
            {
                dots[position].setTextColor(colorActive[position]);
            }



    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addButtonDots(position);

            if(position==screen_layouts.length-1)
            {
                next.setText("PROCEED");
                skip.setVisibility(View.GONE);
            }
            else
            {
                next.setText("NEXT");
                skip.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusBarColor()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPageAAdapter extends PagerAdapter
    {

        LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View v = layoutInflater.inflate(screen_layouts[position],container,false);

            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return screen_layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            View v = (View) object;
            container.removeView(v);
        }
    }

}
