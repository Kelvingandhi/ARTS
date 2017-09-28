package com.example.soham.arts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soham.fragment.HomeFragment;

/**
 * Created by 100HM on 21/05/2017.
 */
public class Success_change_pass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_change_pass);


        /*TextView t2=(TextView) findViewById(R.id.textchangepass);
        TextView t1=(TextView) findViewById(R.id.textBookingsucc);

        t1.setVisibility(View.GONE);
        t2.setVisibility(View.VISIBLE);*/





    }
    public void onBackPressed() {

       /* SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
        editor.clear();

        editor.putBoolean("flag", false);
        editor.commit();
        finish();*/
        Intent i1=new Intent(Success_change_pass.this, Login.class);
        startActivity(i1);


    }


}

