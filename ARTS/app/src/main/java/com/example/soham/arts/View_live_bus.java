package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class View_live_bus extends AppCompatActivity{
	
	ListView l1;
	TextView u;

	TextView unavailable;


	public static String NAMESPACE="http://tempuri.org/";
	public static String URL=Login.URL;
	
	public static String METHOD_NAME="live_bus";
	public static String SOAP_ACTION="http://tempuri.org/live_bus";


	public static int posi;
	String[] col ;

	public static String getdelay;
	
	ArrayList<String>  listarray = new ArrayList<String>();

	static public ArrayList<Buses> Buse = new ArrayList<Buses>();
	static public ArrayList<String> bus_route_list  = new ArrayList<String>();

	//static public ArrayList<String> delay  = new ArrayList<String>();
	//static public ArrayList<String> listarray  = new ArrayList<String>();

	class Buses {
		String bus_id;
		String status;
		String source;
		String destination;
		String despatch_time;
		String arrival_time;

		String bus_route;



		public Buses(String bus_id, String status, String source,
					 String destination, String despatch_time , String arrival_time,String bus_route) {
			super();
			this.bus_id = bus_id;
			this.status = status;
			this.source = source;
			this.destination = destination;
			this.despatch_time = despatch_time;
			this.arrival_time = arrival_time;

			this.bus_route=bus_route;

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

				Toast.makeText(View_live_bus.this,"Help ARTS",Toast.LENGTH_SHORT).show();


			default:
				return super.onOptionsItemSelected(item);
		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_live_bus);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Live Bus Status");

		l1=(ListView)findViewById(R.id.listView1);
		unavailable = (TextView)findViewById(R.id.unavailble);
		registerForContextMenu(l1);

		/*@Override
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

			return 1;
		}*/

		/*Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				handler.postDelayed(r, 10000);
			}
		};
		handler.postDelayed(r, 0);*/

		//final Handler handler = new Handler();
		/*handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				//Do something after 20 seconds
			}
		}, 20000);*/


		
		new live_bus().execute();


	}





	public class live_bus extends AsyncTask<Void, Void, String>{
		
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(View_live_bus.this);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			   try
	  			{
	  				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	  				
	  				request.addProperty("bus_no",Live_bus_status.bus.getSelectedItem().toString());
					//request.addProperty("current_city",backgroundservice.city.toString());
	  			
	  				
	  				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	  		        envelope.dotNet=true;
	  		        envelope.setOutputSoapObject(request);
	  		        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	  		        androidHttpTransport.call(SOAP_ACTION, envelope);
	  		       
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


			if(!result.equals("fail")) {
				Buse.clear();
				String rows[] = result.split("`");          // rows split by ,


				for (int i = 0; i < rows.length; i++) {
					col = rows[i].split("~");
					Buses bus = new Buses(col[1], col[2], col[3], col[4], col[5], col[6], col[7]);
					Buse.add(bus);
				}

				//delay.add(Live_bus_status.times);

				//SharedPreferences objpref = getSharedPreferences(Live_bus_status.PREFS_DELAY, MODE_PRIVATE);
				//SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
				//getdelay = objpref.getString("setdelay", "");

				//getdelay = Live_bus_status.times;
				//getdelay="new delay";
				//Toast.makeText(View_live_bus.this,getdelay+"helloooooo",Toast.LENGTH_LONG).show();

				CustomAdapter_live_bus adapter = new CustomAdapter_live_bus(View_live_bus.this, Buse);
				l1.setAdapter(adapter);
			}
			//l1.setAdapter( new ArrayAdapter<String>(View_live_bus.this, android.R.layout.simple_list_item_1, listarray));

			else
			{
				l1.setVisibility(View.GONE);
				unavailable.setVisibility(View.VISIBLE);
			}

		}
		

	}



	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenu.ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);   //.....[2]....create the context menu

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.add("View Current Station");
		menu.add("All Availability");

		posi = info.position;
		menu.setHeaderIcon(android.R.drawable.ic_menu_info_details);



	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {    //.....[3]....context item select ..like "Edit" or "delete"
		// TODO Auto-generated method stub

		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();  // make a final
		if (item.getTitle().equals("View Current Station")) {


			view_current_station();
			//Toast.makeText(this,"fare inquary is in process....",0).show();
		} else {

			Intent i1= new Intent(View_live_bus.this, All_Availability.class);
			startActivity(i1);


			//Toast.makeText(View_live_bus.this," in process....",0).show();
		}


		return super.onContextItemSelected(item);

	}

	private void view_current_station() {

		Toast.makeText(View_live_bus.this,Buse.get(posi).bus_id+" "+Buse.get(posi).status+" "+Buse.get(posi).source+" "+Buse.get(posi).destination+" "+Buse.get(posi).despatch_time+" "+Buse.get(posi).arrival_time+" "+Buse.get(posi).bus_route,Toast.LENGTH_SHORT).show();
		AlertDialog.Builder builder = new AlertDialog.Builder(View_live_bus.this);

		LayoutInflater inflater = getLayoutInflater();


		View dialogView = inflater.inflate(R.layout.next_fevent,null);


		TextView e1 = (TextView)dialogView.findViewById(R.id.Text111);
		TextView e2 = (TextView)dialogView.findViewById(R.id.Text2);
		TextView e3 = (TextView)dialogView.findViewById(R.id.Text3);
		TextView e4 = (TextView)dialogView.findViewById(R.id.Text4);
		TextView e5 = (TextView)dialogView.findViewById(R.id.Text5);
		TextView e6 = (TextView)dialogView.findViewById(R.id.Text6);
		TextView e7 = (TextView)dialogView.findViewById(R.id.Text7);
		TextView e8 = (TextView)dialogView.findViewById(R.id.Text8);
        TextView e9 = (TextView)dialogView.findViewById(R.id.Text9);
		e1.setText("title            :"+Buse.get(posi).bus_id);
		e2.setText("status       :"+Buse.get(posi).status);
		e3.setText("source       :"+Buse.get(posi).source);
		e4.setText("destination  :"+Buse.get(posi).destination);
		e5.setText("desp_time    :"+Buse.get(posi).despatch_time);
		e6.setText("arri_time    :"+Buse.get(posi).arrival_time);
		e7.setText("bus_route    :"+Buse.get(posi).bus_route);
		e8.setText("current_city :"+Live_bus_status.city.toString());
        e9.setText("Delay_time   :"+Live_bus_status.times.toString());
		builder.setView(dialogView);



		final AlertDialog dialog = builder.create();
		dialog.show();
	}


}
