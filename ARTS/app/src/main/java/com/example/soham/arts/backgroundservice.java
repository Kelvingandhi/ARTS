package com.example.soham.arts;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.soham.fragment.HomeFragment;
import com.example.soham.fragment.NotificationsFragment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;







public class backgroundservice extends Service {
	public static double speed;
	public static double lntd;
	public static double altd;
	TimerTask timerTask2;
	Timer timer = new Timer();
	Handler handler2 = new Handler();
	//public static String flag="";
	public static String city="";
	public static String city1="";
	public static int flag=1;

	public static String NAMESPACE= Login.NAMESPACE;
	public static String URL=Login.URL;
	public static String METHOD_NAME="multiple_get_lat_long_bus_depot";
	public static String SOAP_ACTION="http://tempuri.org/multiple_get_lat_long_bus_depot";

	public static String METHOD_NAME1="multiple_get_bus_lat_long";
	public static String SOAP_ACTION1="http://tempuri.org/multiple_get_bus_lat_long";
	public static String METHOD_NAME2="chech_return_trip";
	public static String SOAP_ACTION2="http://tempuri.org/chech_return_trip";



	Boolean noti_state;

	String station;
	String place1;
	String last_station;
	String place2;

	//ArrayList<bus_no> no_of_bus_list = new ArrayList<>();
	//ArrayList<bus_no_second> no_of_bus_list1 = new ArrayList<>();

	ArrayList<station_latlong_data> station_latlong_list = new ArrayList<>();
	ArrayList<station_latlong_data1> station_latlong_list1 = new ArrayList<>();
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

	class station_latlong_data1 {
		String id;
		String station_name;
		String lat;
		String longi;



		public station_latlong_data1(String id, String station_name, String lat,
									 String longi) {
			super();
			this.id = id;
			this.station_name = station_name;
			this.lat = lat;
			this.longi = longi;

		}

	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		timerTask2 = new TimerTask() {
			@Override
			public void run() {
				handler2.post(new Runnable() {
					@Override
					public void run() {
						new bus_lat_long().execute();

						//Toast.makeText(backgroundservice.this, "hi", Toast.LENGTH_LONG).show();

					}
				});
			}



		};


		timer.scheduleAtFixedRate(timerTask2, 1000, 10000);

		return START_STICKY;
	}







	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}



	public class bus_lat_long extends AsyncTask<Void, Void, String> {


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

				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String emailaddr=applicationpreferences.getString("user_email","");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
				request.addProperty("email",emailaddr);
				//request.addProperty("pass",pass.getText().toString());

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
			String[] cols;

			bus_current_latlong_list.clear();
			if(!result.equals("fail")) {

				rows = result.split("`");

				for (int i = 0; i < rows.length; i++) {


					cols = rows[i].split("~");


					bus_current_latlong_data bus_latlong = new bus_current_latlong_data(cols[0], cols[1], cols[2], cols[3]);
					bus_current_latlong_list.add(bus_latlong);

				}

			}

			new db_lat_long().execute();


		}

	}



	public class db_lat_long extends AsyncTask<Void, Void, String> {


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}


		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String abc = applicationpreferences.getString("user_email", "");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("email", abc);
				//request.addProperty("pass",pass.getText().toString());

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);

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


			String[] matrix=new String[1];

			String[] rows;

			String[] newrows;

			String[] cols;

			String[] newcols;

			int flag = 0;


			if(result.contains("#"))
			{
				flag = 1;
			}

			if (!result.equals("fail")) {


				matrix = result.split("#");






				//	no_of_bus_list.clear();
				//	no_of_bus_list1.clear();

				//for (int i = 0; i < matrix.length; i++) {

				rows = matrix[0].split("`");

				station_latlong_list.clear();
				station_latlong_list1.clear();

				for(int j = 0; j < rows.length; j++) {

					cols = rows[j].split("~");

					station_latlong_data bus = new station_latlong_data(cols[0], cols[1], cols[2], cols[3]);

					station_latlong_list.add(bus);

					//			bus_no t1 = new bus_no("" + 0, station_latlong_list);
					//			no_of_bus_list.add(t1);
				}


				//last_station = station_latlong_list.get(station_latlong_list.size()-1).station_name;

				if(flag==1) {

					newrows = matrix[1].split("`");

					for (int j = 0; j < newrows.length; j++) {

						newcols = newrows[j].split("~");

						station_latlong_data1 bus1 = new station_latlong_data1(newcols[0], newcols[1], newcols[2], newcols[3]);

						station_latlong_list1.add(bus1);


						//			bus_no_second t2 = new bus_no_second("" + 1, station_latlong_list1);
						//			no_of_bus_list1.add(t2);

					}
				}

				//bus_no temp = new bus_no(""+i);


				//}
			}

			//Toast.makeText(backgroundservice.this, result, Toast.LENGTH_LONG).show();
			//Toast.makeText(backgroundservice.this, bus_route_list + "jfhj", Toast.LENGTH_LONG).show();
			//Toast.makeText(backgroundservice.this, get_geo_fence_lat_long.get(0)+"hello"+get_geo_fence_lat_long.get(1), Toast.LENGTH_LONG).show();




			//Toast.makeText(backgroundservice.this, no_of_bus_list.get(0).get(7).station_name + " " + " hellooo", Toast.LENGTH_SHORT).show();

			//for (int i = 0; i < no_of_bus_list.size(); i++) {



			for(int j=0;j<station_latlong_list.size();j++) {

				//Toast.makeText(backgroundservice.this, station_latlong_list.size() + " " + " hi", Toast.LENGTH_SHORT).show();
				//place1 = station_latlong_list.get(i).station_name;

				SharedPreferences applicationpreferences = getSharedPreferences(NotificationsFragment.PREFS_NAME, MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				noti_state = applicationpreferences.getBoolean("switchState", true);

				if (noti_state) {
					Log.d("message","City : "+city);
					Log.d("message","Message2 : "+j);
					Log.d("message","Message3 : "+station_latlong_list.get(j).station_name);
					calculate_distance(Double.valueOf(station_latlong_list.get(j).lat), Double.valueOf(station_latlong_list.get(j).longi), station_latlong_list.get(j).station_name, 0);

				}

			}

			if(flag == 1) {
				for (int j = 0; j < station_latlong_list1.size(); j++) {

					//Toast.makeText(backgroundservice.this, station_latlong_list1.size() + " " + " hello", Toast.LENGTH_SHORT).show();

					//place1 = station_latlong_list.get(i).station_name;

					SharedPreferences applicationpreferences = getSharedPreferences(NotificationsFragment.PREFS_NAME, MODE_PRIVATE);
					//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
					noti_state = applicationpreferences.getBoolean("switchState", true);

					if (noti_state) {
						Log.d("message", "City1 : " + city1);
						Log.d("message", "Message2 : " + j);
						Log.d("message", "Message3 : " + station_latlong_list1.get(j).station_name);
						calculate_distance_second(Double.valueOf(station_latlong_list1.get(j).lat), Double.valueOf(station_latlong_list1.get(j).longi), station_latlong_list1.get(j).station_name, 1);

					}

				}
			}
			//}



		}






	}





	/////fetch latlong of station based on bus route

	public void calculate_distance(double sta_lat,double sta_long,String station_name,int i)
	{

		Log.d("message","Message : "+city);
		Log.d("message","Message1 : "+station_name);


		float[] results = new float[1];
		Location.distanceBetween(Double.valueOf(bus_current_latlong_list.get(i).buslat), Double.valueOf(bus_current_latlong_list.get(i).buslongi) ,sta_lat ,sta_long , results);
		float distanceInMeters = results[0];

		if(distanceInMeters < 1000 ) {
			Toast.makeText(backgroundservice.this, distanceInMeters+"in geofence area", Toast.LENGTH_LONG).show();



			if (!station_name.equals(city)) {
				city = station_name;
				//addNotification(station_name + " : " + sta_lat + " and " + sta_long);
				//place2=station_name;
				//if(flag==1) {
					addNotification(station_name + " : " + sta_lat + " and " + sta_long);
				//}
				/*if(city.equals(last_station))
				{
					//city="";
					//last_station="";
					new check_return().execute();

				}*/
			}




		}

		else
		{

			//	Toast.makeText(backgroundservice.this, "not in geofence area", Toast.LENGTH_LONG).show();
		}

	}


	public void calculate_distance_second(double sta_lat,double sta_long,String station_name,int i)
	{

		station=station_name;
		Log.d("message","Message : "+city1);
		Log.d("message","Message1 : "+station_name);


		float[] results = new float[1];
		Location.distanceBetween(Double.valueOf(bus_current_latlong_list.get(i).buslat), Double.valueOf(bus_current_latlong_list.get(i).buslongi) ,sta_lat ,sta_long , results);
		float distanceInMeters = results[0];

		if(distanceInMeters < 1000 ) {
			Toast.makeText(backgroundservice.this, distanceInMeters+"in geofence area", Toast.LENGTH_LONG).show();



			if (!station_name.equals(city1)) {
				city1 = station_name;
				addNotification(station_name + " : " + sta_lat + " and " + sta_long);
				//place2=station_name;
			}





		}

		else
		{

			//	Toast.makeText(backgroundservice.this, "not in geofence area", Toast.LENGTH_LONG).show();
		}

	}



	private void addNotification(String msg) {

		Log.d("message","Noti city : "+msg);

		NotificationCompat.Builder builder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.ic_action_location)
						.setColor(Color.RED)
						.setContentTitle(msg)
						.setContentText("Now you are in : "+station)
						.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
						.setAutoCancel(true);

		Intent notificationIntent = new Intent(this, HomeFragment.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);

		// Add as notification
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(0, builder.build());
	}



	/*public class check_return extends AsyncTask<Void, Void, String> {


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

				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String emailaddr=applicationpreferences.getString("user_email","");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
				request.addProperty("email",emailaddr);
				//request.addProperty("pass",pass.getText().toString());

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION2, envelope);

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

			Toast.makeText(backgroundservice.this,result,0).show();

			if(result.equals("true"))
			{
				city = "";
				//last_station=station_latlong_list.get(0).station_name;
				flag=1;
			}
			else
			{
				flag=0;
			}



		}

	}*/


}










/*

public class backgroundservice extends Service {
	public static double speed;
	public static double lntd;
	public static double altd;
	TimerTask timerTask2;
	Timer timer = new Timer();
	Handler handler2 = new Handler();
	public static int flag=1;
	public static String city="";

	public static String station;
	Boolean noti_state;

	public static String NAMESPACE = "http://tempuri.org/";
	public static String URL = Login.URL;

	public static String METHOD_NAME="get_lat_long_bus_depot";
	public static String SOAP_ACTION="http://tempuri.org/get_lat_long_bus_depot";

	public static String METHOD_NAME1="get_bus_lat_long";
	public static String SOAP_ACTION1="http://tempuri.org/get_bus_lat_long";

	public static String METHOD_NAME2="chech_return_trip";
	public static String SOAP_ACTION2="http://tempuri.org/chech_return_trip";


	String last_station;
	String place1;
	String place2;

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
	public int onStartCommand(Intent intent, int flags, int startId) {
		timerTask2 = new TimerTask() {
			@Override
			public void run() {
				handler2.post(new Runnable() {
					@Override
					public void run() {
						new bus_lat_long().execute();

						//Toast.makeText(backgroundservice.this, "hi", Toast.LENGTH_LONG).show();

                    }
				});
			}



		};


        timer.scheduleAtFixedRate(timerTask2, 1000, 10000);

		return START_STICKY;
	}







	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}



	public class bus_lat_long extends AsyncTask<Void, Void, String> {


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

				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String emailaddr=applicationpreferences.getString("user_email","");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
				request.addProperty("email",emailaddr);
				//request.addProperty("pass",pass.getText().toString());

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

			new db_lat_long().execute();


		}

	}



	public class db_lat_long extends AsyncTask<Void, Void, String> {


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}


		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String abc = applicationpreferences.getString("user_email", "");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("email", abc);
				//request.addProperty("pass",pass.getText().toString());

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);

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

				last_station = station_latlong_list.get(station_latlong_list.size()-1).station_name;

			}

			//Toast.makeText(backgroundservice.this, result, Toast.LENGTH_LONG).show();
				//Toast.makeText(backgroundservice.this, bus_route_list + "jfhj", Toast.LENGTH_LONG).show();
				//Toast.makeText(backgroundservice.this, get_geo_fence_lat_long.get(0)+"hello"+get_geo_fence_lat_long.get(1), Toast.LENGTH_LONG).show();



			for(int i=0;i<station_latlong_list.size();i++) {

				//place1 = station_latlong_list.get(i).station_name;

				SharedPreferences applicationpreferences = getSharedPreferences(NotificationsFragment.PREFS_NAME, MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				noti_state = applicationpreferences.getBoolean("switchState", true);

				if(noti_state) {
					calculate_distance(Double.valueOf(station_latlong_list.get(i).lat), Double.valueOf(station_latlong_list.get(i).longi), station_latlong_list.get(i).station_name);

				}

				//calculate_distance(Double.valueOf(station_latlong_list.get(i).lat), Double.valueOf(station_latlong_list.get(i).longi), station_latlong_list.get(i).station_name);


			}



		}






	}





		/////fetch latlong of station based on bus route

	public void calculate_distance(double sta_lat,double sta_long,String station_name)
	{

		station = station_name;
		Log.d("message","Message : "+city);
		Log.d("message","Message1 : "+station_name);
		float[] results = new float[1];
		Location.distanceBetween(Double.valueOf(bus_current_latlong_list.get(0).buslat), Double.valueOf(bus_current_latlong_list.get(0).buslongi) ,sta_lat ,sta_long , results);
		float distanceInMeters = results[0];

		if(distanceInMeters < 4000 ) {

			Toast.makeText(backgroundservice.this,distanceInMeters+"jdhfj",0).show();
			if(!station_name.equals(city))
			{

				city = station_name;
				if(flag==1) {
					addNotification(station_name + " : " + sta_lat + " and " + sta_long);
				}
				if(city.equals(last_station))
				{
					//city="";
					//last_station="";
					new check_return().execute();

				}

				//place2=station_name;
			}

	}

		else
		{

		//	Toast.makeText(backgroundservice.this, "not in geofence area", Toast.LENGTH_LONG).show();
		}

	}



	private void addNotification(String msg) {
		NotificationCompat.Builder builder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.ic_action_location)
						.setColor(Color.RED)
						.setContentTitle(msg)
						.setContentText("Now you are in : "+station)
						.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
						.setAutoCancel(true);

		Intent notificationIntent = new Intent(this, HomeFragment.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);

		// Add as notification
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(0, builder.build());
	}


	public class check_return extends AsyncTask<Void, Void, String> {


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

				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String emailaddr=applicationpreferences.getString("user_email","");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
				request.addProperty("email",emailaddr);
				//request.addProperty("pass",pass.getText().toString());

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION2, envelope);

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

			Toast.makeText(backgroundservice.this,result,0).show();

			if(result.equals("true"))
			{
				city = "";
				//last_station=station_latlong_list.get(0).station_name;
				flag=1;
			}
			else
			{
				flag=0;
			}



		}

	}


}

*/