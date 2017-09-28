package com.example.kelvin.my_conductor_app;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class backgroundservice extends Service {
	public static double speed;
	public static double lntd;
	public static double altd;
	TimerTask timerTask2;
	Timer timer = new Timer();
	Handler handler2 = new Handler();


	public static String NAMESPACE="http://tempuri.org/";
	public static String URL = Login.URL;

	public static String METHOD_NAME="lat_long_store";
	public static String SOAP_ACTION="http://tempuri.org/lat_long_store";


	public static String METHOD_NAME1="conductor_bus_track_live_time_calculate_for_delay";
	public static String SOAP_ACTION1="http://tempuri.org/conductor_bus_track_live_time_calculate_for_delay";

	public static String lati="";
	public static String longi="";
	/*@Override
	public void onCreate() {
		super.onCreate();

	}*/

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		timerTask2 = new TimerTask() {
			@Override
			public void run() {
				handler2.post(new Runnable() {
					@Override
					public void run() {
						getloc();
						Location locationA = new Location("Current");

						locationA.setLatitude(altd);
						locationA.setLongitude(lntd);
						Log.d("Location", altd + "-" + lntd);

						lati=String.valueOf(altd);
						longi=String.valueOf(lntd);

						new get_lat_long().execute();

						new update_current_time_for_delay().execute();

						Toast.makeText(backgroundservice.this,altd + "-" + lntd, Toast.LENGTH_SHORT).show();

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

	public void getloc() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {

					lntd = location.getLongitude();
					altd = location.getLatitude();
					speed = location.getSpeed();
				}
			}
		};
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}


	public class get_lat_long extends AsyncTask<Void, Void, String> {

		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			/*dialog=new ProgressDialog(backgroundservice.this);
			dialog.setTitle("Loading...");
			dialog.setMessage("Please Wait");
			dialog.setIcon(R.mipmap.ic_launcher);
			dialog.show();*/
		}


		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try
			{
				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String emailaddr=applicationpreferences.getString("user_email","");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("email",emailaddr);
				request.addProperty("latitude",lati);
				request.addProperty("longitude",longi);

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;




				envelope.setOutputSoapObject(request);

			//	MarshalDouble md = new MarshalDouble();
			//	md.register(envelope);



				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);

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

			//dialog.dismiss();

			//single_login_email = email.getText().toString();
			//single_login_pass = pass.getText().toString();
			Toast.makeText(backgroundservice.this,result,Toast.LENGTH_LONG).show();

		}

	}

	private class update_current_time_for_delay extends AsyncTask<Void, Void, String> {

		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			/*dialog=new ProgressDialog(backgroundservice.this);
			dialog.setTitle("Loading...");
			dialog.setMessage("Please Wait");
			dialog.setIcon(R.mipmap.ic_launcher);
			dialog.show();*/
		}


		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try
			{
				SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				String email=applicationpreferences.getString("user_email","");
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
				request.addProperty("email",email);

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;




				envelope.setOutputSoapObject(request);

				//	MarshalDouble md = new MarshalDouble();
				//	md.register(envelope);



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

			//dialog.dismiss();

			if(result.equals("new time updated successfull"))
			{
				Toast.makeText(backgroundservice.this,"new time updated successfull",Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(backgroundservice.this,result,Toast.LENGTH_SHORT).show();
			}
			//single_login_email = email.getText().toString();
			//single_login_pass = pass.getText().toString();


		}

	}
/*
	public class MarshalDouble implements Marshal
	{


		public Object readInstance(XmlPullParser parser, String namespace, String name,
								   PropertyInfo expected) throws IOException, XmlPullParserException {

			return Double.parseDouble(parser.nextText());
		}


		public void register(SoapSerializationEnvelope cm) {
			cm.addMapping(cm.xsd, "double", Double.class, this);

		}


		public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
			writer.text(obj.toString());
		}

	}
*/

}