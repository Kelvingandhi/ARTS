package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
 * Created by Kelvin on 4/2/2017.
 */
public class Cancelled_bus_action extends AppCompatActivity {

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;
    public static String METHOD_NAME="cancelled_bus_action";
    public static String SOAP_ACTION="http://tempuri.org/cancelled_bus_action";

    ArrayList<cancelled_buses> canc_bus_array_list = new ArrayList<>();

    ListView cancelled_bus_list;

    TextView can_bus;




    public class cancelled_buses
    {
        String bus_id;
        String avail;
        String source;
        String destination;
        String departure_time;
        String arrivial_time;
        String cancelling_date;

        public cancelled_buses(String bus_id,String avail, String source, String destination, String departure_time, String arrivial_time, String cancelling_date) {
            this.bus_id = bus_id;
            this.avail = avail;
            this.source = source;
            this.destination = destination;
            this.departure_time = departure_time;
            this.arrivial_time = arrivial_time;
            this.cancelling_date=cancelling_date;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cancel_bus_action_layout);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cancelled Bus Action");
        cancelled_bus_list = (ListView) findViewById(R.id.cancel_bus_listView);

        can_bus = (TextView) findViewById(R.id.can_bus);

        new cancel_bus_data().execute();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.help_icon:

                Toast.makeText(Cancelled_bus_action.this," ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class cancel_bus_data extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Cancelled_bus_action.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


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
            if(!result.equals("fail")) {

                canc_bus_array_list.clear();

                String[] rows = result.split("`");


                String[] cols = new String[0];

                for (int i = 0; i < rows.length; i++) {
                    cols = rows[i].split("~");


                    cancelled_buses temp1 = new cancelled_buses(cols[1], cols[2], cols[3], cols[4], cols[5], cols[6], cols[7]);
                    canc_bus_array_list.add(temp1);


                }


                Customview_cancelled_bus_action adapter = new Customview_cancelled_bus_action(Cancelled_bus_action.this, canc_bus_array_list);

                cancelled_bus_list.setAdapter(adapter);
                // bus_list.setAdapter(new ArrayAdapter<String>(View_Bus_between_station.this, android.R.layout.simple_list_item_1,bus_search_list));


                Toast.makeText(Cancelled_bus_action.this, result, Toast.LENGTH_LONG).show();

            }
            else
            {
                cancelled_bus_list.setVisibility(View.GONE);
                can_bus.setVisibility(View.VISIBLE);
            }



        }
    }



}
