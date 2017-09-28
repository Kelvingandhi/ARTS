package com.example.soham.arts;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Between_station extends AppCompatActivity {

	
	public static Spinner source,destination;
	 Button search;
	ArrayList<String> list= new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.between_station);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Between Station");
		
		//source= (EditText)findViewById(R.id.editText1);
		source=(Spinner)findViewById(R.id.Spinner1);
		destination=(Spinner)findViewById(R.id.Spinner2);

		list.add("ahmedabad");
		list.add("surat");
		list.add("udhna");
		list.add("bhestan");
		list.add("sachin");
		list.add("maroli");
		list.add("navsari");
		list.add("vapi");

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Between_station.this, android.R.layout.simple_spinner_dropdown_item, list);
		source.setAdapter(adapter1);
		destination.setAdapter(adapter1);


		search=(Button) findViewById(R.id.button1);
		
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i1=new Intent(Between_station.this,View_between_station.class);
				startActivity(i1);
				
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

				Toast.makeText(Between_station.this,"Help ARTS",Toast.LENGTH_SHORT).show();


			default:
				return super.onOptionsItemSelected(item);
		}
	}


}
