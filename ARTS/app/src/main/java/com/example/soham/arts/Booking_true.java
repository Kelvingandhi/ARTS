package com.example.soham.arts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soham.fragment.HomeFragment;

import java.util.ArrayList;

/**
 * Created by 100HM on 18/05/2017.
 */
public class Booking_true extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_true);




        /*TextView t2=(TextView) findViewById(R.id.textchangepass);
        TextView t1=(TextView) findViewById(R.id.textBookingsucc);

        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.GONE);
        */




    }
    public void onBackPressed() {

        Intent i1 = new Intent(Booking_true.this,Login.class);
        startActivity(i1);

    }

}
