package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * Created by 100HM on 07-03-2017.
 */
public class Fatch_BusList extends AppCompatActivity {
    ListView l1;
    String a="";

    TextView unavailable;
    public static int posit;

    public static String status="";

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "fetch_buse_list";
   // public static String METHOD_NAME2="fetch_buse_list_1";
    //public static String METHOD_NAME3="round_trip_amount";

    public static String SOAP_ACTION = "http://tempuri.org/fetch_buse_list";
   // public static String SOAP_ACTION2 = "http://tempuri.org/fetch_buse_list_1";

    public static ArrayList<BusesList> Buse_list = new ArrayList<BusesList>();
    //ArrayList<String>  listarray = new ArrayList<String>();


    public static class BusesList {
        String bus_id;
        //String status;
        String source;
        String destination;

        String despatch_time;
        String arrival_time;
        //String amount;

        String despatch_time1;
        String arrival_time1;


        public BusesList(String bus_id, String source,
                         String destination, String despatch_time, String arrival_time,String despatch_time1,
                                 String arrival_time1) {
            super();
            this.bus_id = bus_id;
           // this.status = status;
            this.source = source;
            this.destination = destination;

            this.despatch_time = despatch_time;
            this.arrival_time = arrival_time;
           // this.amount = amount;

            this.despatch_time1=despatch_time1;
            this.arrival_time1=arrival_time1;

        }

    }


    /*
    ArrayList<BusesList_1> Buse_list_1 = new ArrayList<BusesList_1>();
    //ArrayList<String>  listarray = new ArrayList<String>();


    class BusesList_1 {

        String arrival_time1;
        String despatch_time1;


        public BusesList_1( String despatch_time1, String arrival_time1) {
            super();


            this.arrival_time1=arrival_time1;
            this.despatch_time1=despatch_time1;
        }

    }

    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fatch_buslist);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket Genrate");
        //e1 = (EditText) findViewById(R.id.editText1);
        l1 = (ListView) findViewById(R.id.listView);

        unavailable = (TextView) findViewById(R.id.unavlble);
        new view_buses().execute();
      //  new view_buses_1().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.help_icon:

                Toast.makeText(Fatch_BusList.this,"ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class view_buses extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Fatch_BusList.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("source", Ticket_Genrate.sourc.getSelectedItem().toString());
                request.addProperty("destination", Ticket_Genrate.desti.getSelectedItem().toString());
                if (Ticket_Genrate.r1.isChecked()) {
                    request.addProperty("status", "local");
                    //status="local";

                }
                else
                {
                    request.addProperty("status","express");
                    //status="express";

                }
                request.addProperty("return_date", Ticket_Genrate.toDateEtxt.getText().toString());


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
            dialog.dismiss();

            if(!result.equals("fail")) {
                String rows[] = result.split("`");          // rows split by ,

                Buse_list.clear();
                for (int i = 0; i < rows.length; i++) {
                    String col[] = rows[i].split("~");
                    BusesList bus = new BusesList(col[0], col[1], col[2], col[3], col[4], col[5], col[6]);
                    Buse_list.add(bus);
                    //listarray.add(rows[i]);         //add in array           represent the each rows
                    //listarray.add(col[1]);
                    //listarray.add(col[2]);
                    //listarray.add(col[3]);
                    //listarray.add(col[4]);
                    //listarray.add(col[5]);
                    //listarray.add(col[6]);

                    // a=bus.bus_id;


                }

                //l1.setAdapter(new ArrayAdapter<String>(View_between_station.this, android.R.layout.simple_list_item_1, listarray));

                CustomAdapter_Fetch_Buses adapter = new CustomAdapter_Fetch_Buses(Fatch_BusList.this, Buse_list);
                l1.setAdapter(adapter);
                // Toast.makeText(Fatch_BusList.this,a,0).show();


                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        // TODO Auto-generated method stub
                        posit = position;


                        Intent i1 = new Intent(Fatch_BusList.this, Display_price.class);
                        startActivity(i1);
                        //Toast.makeText(View_between_station.this, Buse_list.get(postion).bus_id + " "  + Buse_list.get(postion).source + " " + Buse_list.get(postion).destination + " " + Buse_list.get(postion).arrival_time + " " + Buse_list.get(postion).despatch_time, 1).show();
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
            }
            else {
                l1.setVisibility(View.GONE);
                unavailable.setVisibility(View.VISIBLE);
            }

        }

    }



    /*


    class view_buses_1 extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Fatch_BusList.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                request.addProperty("bus_id", a.toString());
                request.addProperty("source", Ticket_Genrate.sourc.getSelectedItem().toString());
                request.addProperty("destination", Ticket_Genrate.desti.getSelectedItem().toString());
                if (Ticket_Genrate.r1.isChecked()) {
                    request.addProperty("status", "local");

                }
                else
                {
                    request.addProperty("status","express");

                }


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

            String rows[] = result.split(",");          // rows split by ,

            for (int i = 0; i < rows.length; i++) {
                String col[] = rows[i].split("-");
                BusesList_1 bus = new BusesList_1(col[1], col[2]);
                Buse_list_1.add(bus);
                //listarray.add(rows[i]);         //add in array           represent the each rows
                //listarray.add(col[1]);
                //listarray.add(col[2]);
                //listarray.add(col[3]);
                //listarray.add(col[4]);
                //listarray.add(col[5]);
                //listarray.add(col[6]);
            }

            //l1.setAdapter(new ArrayAdapter<String>(View_between_station.this, android.R.layout.simple_list_item_1, listarray));

            CustomAdapter_Fetch_Buses adapter = new CustomAdapter_Fetch_Buses(Fatch_BusList.this, Buse_list_1);
            l1.setAdapter(adapter);

        }

    }

    */
}