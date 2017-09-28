package com.example.soham.arts;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soham.googlemapdemo.MapActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class View_between_station extends AppCompatActivity {

    ListView l1;
    EditText e1;
    TextView unavailable;

    String station;
    public static String result1[];
    public static String toward_time,bus_id,status,source,destination;

    String[] col ;
    String[] routes;
    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "bus_search";
    public static String SOAP_ACTION = "http://tempuri.org/bus_search";



   // public static String METHOD_NAME2 ="fetch_bus_latlong_for_bus_track";
   // public static String SOAP_ACTION2 = "http://tempuri.org/fetch_bus_latlong_for_bus_track";



    static public ArrayList<Buses> Buse = new ArrayList<Buses>();
    static public ArrayList<String> bus_route_list  = new ArrayList<String>();
    //static public ArrayList<String> listarray  = new ArrayList<String>();


    class Buses {
        String bus_id;
        String status;
        String source;
        String destination;
        String arrival_time;
        String despatch_time;
        String bus_route;


        public Buses(String bus_id, String status, String source,
                     String destination,String despatch_time , String arrival_time, String bus_route) {
            super();
            this.bus_id = bus_id;
            this.status = status;
            this.source = source;
            this.destination = destination;
            this.arrival_time = arrival_time;
            this.despatch_time = despatch_time;
            this.bus_route=bus_route;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_between_station);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Between Station");

        e1 = (EditText) findViewById(R.id.editText1);
        l1 = (ListView) findViewById(R.id.list);
        unavailable = (TextView)findViewById(R.id.unavailbl1);
        registerForContextMenu(l1);

	/*	e1.addTextChangedListener(new TextWatcher() {

			ArrayList<String> tamp = new ArrayList<String>();

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				tamp.clear();
				for(int i=0;i<listarray.size();i++)
				{
					if(listarray.get(i).contains(arg0))
					{
						tamp.add(listarray.get(i));
						l1.setAdapter( new ArrayAdapter<String>(View_between_station.this, android.R.layout.simple_list_item_1, tamp));
					}
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		*/

        e1.addTextChangedListener(new TextWatcher() {

            ArrayList<Buses> tamp = new ArrayList<Buses>();


            @Override                                  //   code of searchbox
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                tamp.clear();
                for (int i = 0; i < Buse.size(); i++) {
                    if (Buse.get(i).bus_id.contains(arg0) || Buse.get(i).arrival_time.contains(arg0) || Buse.get(i).despatch_time.contains(arg0)) {
                        tamp.add(Buse.get(i));


                        CustomAdapter adapter = new CustomAdapter(View_between_station.this, tamp);
                        l1.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        new view_buses().execute();

    }


    class view_buses extends AsyncTask<Void, Void, String> {

       // ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("sour", Between_station.source.getSelectedItem().toString());
                request.addProperty("desti", Between_station.destination.getSelectedItem().toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
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
            //dialog.dismiss();


            if(!result.equals("fail")) {
                Buse.clear();
                String rows[] = result.split("`");          // rows split by ,


                for (int i = 0; i < rows.length; i++) {
                    col = rows[i].split("~");
                    Buses bus = new Buses(col[1], col[2], col[3], col[4], col[5], col[6], col[7]);
                    Buse.add(bus);
                    //listarray.add(rows[i]);         //add in array           represent the each rows
                    //listarray.add(col[1]);
                    //listarray.add(col[2]);
                    //listarray.add(col[3]);
                    //listarray.add(col[4]);
                    //listarray.add(col[5]);
                    //listarray.add(col[6]);


                }

                //l1.setAdapter(new ArrayAdapter<String>(View_between_station.this, android.R.layout.simple_list_item_1, listarray));

                CustomAdapter adapter = new CustomAdapter(View_between_station.this, Buse);
                l1.setAdapter(adapter);

            }
            else
            {
                l1.setVisibility(View.GONE);
                unavailable.setVisibility(View.VISIBLE);
            }



		/*l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int postion, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(View_between_station.this, Buse.get(postion).bus_id + " " + Buse.get(postion).available + " " + Buse.get(postion).source + " " + Buse.get(postion).destination + " " + Buse.get(postion).arrival_time + " " + Buse.get(postion).despatch_time, 1).show();
				//Toast.makeText(past_event.this, event.get(postion).title,1).show();

				//next_pevent.title=event.get(postion).title;

				//next_pevent.detail=event.get(postion).detail;
				//next_pevent.date=event.get(postion).date;
				//next_pevent.time=event.get(postion).time;
				//next_pevent.posted_by=event.get(postion).posted_by;
				//next_pevent.status=event.get(postion).status;
				//next_pevent.files=event.get(postion).files;

				//Intent i=new Intent(getApplicationContext(), next_pevent.class);
				//startActivity(i);
			}
		});
		*/
        }


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);   //.....[2]....create the context menu

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.add("Track This Bus");

        //menu.setHeaderTitle("manage");
        menu.setHeaderTitle("BUS = " + Buse.get(info.position).bus_id);  //...set header title name as selected item name
        menu.setHeaderIcon(android.R.drawable.ic_menu_info_details);
        //menu.setHeaderIcon(R.drawable.service_01);

       // bus_route_list.clear();

        toward_time=Buse.get(info.position).arrival_time;
        bus_id=Buse.get(info.position).bus_id;
        source=Buse.get(info.position).source;
        destination=Buse.get(info.position).destination;
        status=Buse.get(info.position).status;


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {    //.....[3]....context item select ..like "Edit" or "delete"
        // TODO Auto-generated method stub

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();  // make a final
        if (item.getTitle().equals("Track This Bus"))
        {


            String[] routes = col[7].split(",");

            for (int i = 0; i < routes.length; i++) {
                bus_route_list.add(routes[i]);

            }

            //Toast.makeText(View_between_station.this, bus_route_list.toString(),0).show();

            /*for (int i = 0; i < bus_route_list.size(); i++) {
                station = bus_route_list.get(i);
               // Toast.makeText(View_between_station.this, station,0).show();
                new rout_name().execute();


            }*/





           /* for (int i = 0; i < listarray.size(); i++) {
                result1[i] = listarray.get(i);




            }

            Toast.makeText(View_between_station.this, result1.toString(),0).show();*/
            Intent i1= new Intent(View_between_station.this, MapActivity.class);
            startActivity(i1);
            //Toast.makeText(this,"tracking is in process....",0).show();
        }


        return super.onContextItemSelected(item);

    }

    /*(class rout_name extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(View_between_station.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                //request.addProperty("sour", Between_station.source.getText().toString());
                //for (int i = 0; i < routes.length; i++) {
                request.addProperty("station", station);
                //}


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION2, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
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
            dialog.dismiss();

            //result1 = result;
            String rows[] = result.split("`");          // rows split by ,


            for (int i = 0; i < rows.length; i++) {
                col = rows[i].split("~");
                //Buses bus = new Buses(col[1], col[2], col[3], col[4], col[5], col[6],col[7]);
                //Buse.add(bus);
                listarray.add(rows[i]);         //add in array           represent the each rows
                //listarray.add(col[1]);
                //listarray.add(col[2]);
                //listarray.add(col[3]);
                //listarray.add(col[4]);
                //listarray.add(col[5]);
                //listarray.add(col[6]);


            }




            Toast.makeText(View_between_station.this,listarray.toString(),0).show();
            //Toast.makeText(View_between_station.this,listarray.size(),0).show();
            //l1.setAdapter(new ArrayAdapter<String>(View_between_station.this, android.R.layout.simple_list_item_1, listarray));


        }


    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.help_icon:

                Toast.makeText(View_between_station.this,"Help ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

