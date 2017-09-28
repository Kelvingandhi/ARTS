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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Kelvin on 3/15/2017.
 */
public class Fare_inquiry_bus_search extends AppCompatActivity {

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;
    public static String METHOD_NAME="fare_inquiry_bus_display";
    public static String SOAP_ACTION="http://tempuri.org/fare_inquiry_bus_display";

    ArrayList<fare_bus_search> bus_search_list = new ArrayList<>();
    ArrayList<String> list= new ArrayList<String>();
    ListView fare_bus_search_list;

    public static ArrayList<String> bus_route_list = new ArrayList<>();

    Spinner bus_no;
    Button search;
    public static String use_bus_no="";


    public static class fare_bus_search
    {
        static String bus_id;
        String avail;
        String source;
        String destination;
        String arrivial_time;
        String departure_time;

        public fare_bus_search(String bus_id,String avail, String source, String destination,String departure_time, String arrivial_time ) {
            this.bus_id = bus_id;
            this.avail = avail;
            this.source = source;
            this.destination = destination;
            this.arrivial_time = arrivial_time;
            this.departure_time = departure_time;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fare_inquiry_bus_search);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fare Inquiry");

        bus_no = (Spinner) findViewById(R.id.fare_bus_no_editText);
        search = (Button) findViewById(R.id.fare_bus_list_btn);
        fare_bus_search_list = (ListView) findViewById(R.id.fare_bus_listView);

        list.add("1234");
        list.add("1990");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Fare_inquiry_bus_search.this, android.R.layout.simple_spinner_dropdown_item, list);
        bus_no.setAdapter(adapter);

        use_bus_no = bus_no.getSelectedItem().toString();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Bus_search().execute();

                //new Bus_route.execute();

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

                Toast.makeText(Fare_inquiry_bus_search.this,"Help ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class Bus_search extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Fare_inquiry_bus_search.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("bus_id", bus_no.getSelectedItem().toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "Message : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";

            }

            // return null;

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();

            bus_search_list.clear();

            String[] rows = result.split("`");


            String[] cols = new String[0];

            for (int i = 0; i < rows.length; i++) {
                cols = rows[i].split("~");


                fare_bus_search temp1 = new fare_bus_search(cols[1], cols[2], cols[3], cols[4], cols[5], cols[6]);
                bus_search_list.add(temp1);



            }



            bus_route_list.clear();
            String[] routes = cols[7].split(",");

            for (int i = 0; i < routes.length; i++) {
                bus_route_list.add(routes[i]);
            }

            CustomView_bus_search_list adapter = new CustomView_bus_search_list(Fare_inquiry_bus_search.this, bus_search_list);

            fare_bus_search_list.setAdapter(adapter);
            // bus_list.setAdapter(new ArrayAdapter<String>(View_Bus_between_station.this, android.R.layout.simple_list_item_1,bus_search_list));



            Toast.makeText(Fare_inquiry_bus_search.this, result, Toast.LENGTH_LONG).show();

            fare_bus_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Fare_inquiry_bus_search.this,bus_search_list.get(position).bus_id,Toast.LENGTH_LONG).show();

                    Intent i1= new Intent(Fare_inquiry_bus_search.this, Fare_inquiry.class);
                    startActivity(i1);

                }
            });


        }
    }


}
