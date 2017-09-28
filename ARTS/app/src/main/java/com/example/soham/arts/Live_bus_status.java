package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Live_bus_status extends AppCompatActivity {
	
	public static Spinner bus;
	Button search;

	TimerTask timerTask2;
	Timer timer = new Timer();
	Handler handler = new Handler();
	public static String flag="";
	public static String city="";

	public static String times="";

	public static String NAMESPACE="http://tempuri.org/";
	public static String URL=Login.URL;
	public static String METHOD_NAME2="get_lat_long_bus_depot_live_bus";
	public static String SOAP_ACTION2="http://tempuri.org/get_lat_long_bus_depot_live_bus";

	public static String METHOD_NAME1="get_bus_lat_long_live_bus";
	public static String SOAP_ACTION1="http://tempuri.org/get_bus_lat_long_live_bus";

	public static String METHOD_NAME3="calc_time_delay";
	public static String SOAP_ACTION3="http://tempuri.org/calc_time_delay";

	public static final String PREFS_DELAY = "DelayPrefs";

	SharedPreferences objpref;
	SharedPreferences.Editor editor;

	String city1="";
	String place2;
	ArrayList<String> list= new ArrayList<String>();


	ArrayList<station_latlong_data> station_latlong_list = new ArrayList<>();
	ArrayList<bus_current_latlong_data> bus_current_latlong_list = new ArrayList<>();


	class bus_current_latlong_data {
		String id;
		String bus_id;
		String buslat;
		String buslongi;



		public bus_current_latlong_data(String id, String bus_id, String buslat,
										String buslongi) {
			super();
			this.id = id;
			this.bus_id = bus_id;
			this.buslat = buslat;
			this.buslongi = buslongi;

		}

	}


	class station_latlong_data {
		String id;
		String station_name;
		String lat;
		String longi;



		public station_latlong_data(String id, String station_name, String lat,
									String longi) {
			super();
			this.id = id;
			this.station_name = station_name;
			this.lat = lat;
			this.longi = longi;

		}

	}






	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.live_bus_status);


		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Live Bus Status");
		bus=(Spinner)findViewById(R.id.bus_spiner);

		list.add("1234");
		list.add("1990");

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Live_bus_status.this, android.R.layout.simple_spinner_dropdown_item, list);
		bus.setAdapter(adapter1);
		
		search=(Button)findViewById(R.id.btn1);
		
		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				new bus_lat_long1().execute();
				//city1=city.toString();

				Intent i1=new Intent(Live_bus_status.this,View_live_bus.class);
				startActivity(i1);
			}
		});
	}

	public class bus_lat_long1 extends AsyncTask<Void, Void, String> {


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();


		}


		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try
			{

				/*SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String emailaddr=applicationpreferences.getString("user_email","");*/
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
				//request.addProperty("email",emailaddr);
				//request.addProperty("pass",pass.getText().toString());

				request.addProperty("bus_id",bus.getSelectedItem().toString());

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION1, envelope);

				SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

				Log.d("message","Message : "+resultstr.toString());
				//Toast.makeText(Login.this, "valid", 0).show();
				return resultstr.toString();


			}
			catch(Exception e)
			{
				Log.e("ERROR", e.toString());
				return "fail";

			}


		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);


			String[] rows;
			bus_current_latlong_list.clear();
			if(!result.equals("fail")) {

				rows = result.split("~");

				bus_current_latlong_data bus_latlong = new bus_current_latlong_data(rows[0], rows[1], rows[2], rows[3]);
				bus_current_latlong_list.add(bus_latlong);

			}

			new db_lat_long1().execute();


		}

	}




	public class db_lat_long1 extends AsyncTask<Void, Void, String> {


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}


		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				/*SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String abc = applicationpreferences.getString("user_email", "");
				*/
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
				request.addProperty("bus_id",bus.getSelectedItem().toString());
				//request.addProperty("email", abc);
				//request.addProperty("pass",pass.getText().toString());

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION2, envelope);

				SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

				Log.d("message", "Message : " + resultstr.toString());
				//Toast.makeText(Login.this, "valid", 0).show();
				return resultstr.toString();


			} catch (Exception e) {
				Log.e("ERROR", e.toString());
				return "fail";

			}


		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);


			String[] rows;

			String[] cols;

			if (!result.equals("fail")) {

				rows = result.split("`");

				station_latlong_list.clear();

				for (int i = 0; i < rows.length; i++) {



					cols = rows[i].split("~");

					station_latlong_data bus = new station_latlong_data(cols[0], cols[1], cols[2], cols[3]);
					station_latlong_list.add(bus);


				}
			}

			//Toast.makeText(backgroundservice.this, result, Toast.LENGTH_LONG).show();
			//Toast.makeText(backgroundservice.this, bus_route_list + "jfhj", Toast.LENGTH_LONG).show();
			//Toast.makeText(backgroundservice.this, get_geo_fence_lat_long.get(0)+"hello"+get_geo_fence_lat_long.get(1), Toast.LENGTH_LONG).show();



			for(int i=0;i<station_latlong_list.size();i++) {

				//place1 = station_latlong_list.get(i).station_name;

				calculate_distance(Double.valueOf(station_latlong_list.get(i).lat), Double.valueOf(station_latlong_list.get(i).longi), station_latlong_list.get(i).station_name);

				/*if(place1.equals(place2))
				{
					break;
				}*/
			}



			new Delay_time().execute();

		}






	}


	private class Delay_time extends AsyncTask<Void, Void, String>{

		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(Live_bus_status.this);
			dialog.show();
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try
			{
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);

				request.addProperty("station",city.toString());
				request.addProperty("bus_id",bus.getSelectedItem().toString());



				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION3, envelope);

				SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

				Log.d("message","MEssage : "+resultstr.toString());
				return resultstr.toString();


			}
			catch(Exception e)
			{
				Log.e("ERROR", e.toString());
				return "fail";

			}

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			times=result;

			Toast.makeText(Live_bus_status.this,times,Toast.LENGTH_LONG).show();

			//objpref = getSharedPreferences(PREFS_DELAY,MODE_PRIVATE);


			//editor = objpref .edit();


			//editor.putString("setdelay",times);

			//editor.commit();

		}


	}




	/////fetch latlong of station based on bus route

	public void calculate_distance(double sta_lat,double sta_long,String station_name)
	{

		Log.d("message","Message : "+city);
		Log.d("message","Message1 : "+station_name);
		float[] results = new float[1];
		Location.distanceBetween(Double.valueOf(bus_current_latlong_list.get(0).buslat), Double.valueOf(bus_current_latlong_list.get(0).buslongi) ,sta_lat ,sta_long , results);
		float distanceInMeters = results[0];

		if(distanceInMeters < 4000 ) {

			Toast.makeText(Live_bus_status.this,distanceInMeters+"jdhfj",Toast.LENGTH_SHORT).show();
			if(!station_name.equals(city))
			{
				city = station_name;
				//addNotification(station_name + " : " + sta_lat + " and " + sta_long);
				//place2=station_name;
			}

		}

		else
		{

			//	Toast.makeText(backgroundservice.this, "not in geofence area", Toast.LENGTH_LONG).show();
		}

	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:

				// app icon in action bar clicked; goto parent activity.
				this.finish();
				return true;

			case R.id.help_icon:

				Toast.makeText(Live_bus_status.this,"Help ARTS",Toast.LENGTH_SHORT).show();


			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
