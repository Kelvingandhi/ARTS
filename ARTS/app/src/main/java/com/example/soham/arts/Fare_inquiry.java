package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Kelvin on 3/15/2017.
 */
public class Fare_inquiry extends AppCompatActivity{

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;
    public static String METHOD_NAME="fare_inquiry";
    public static String SOAP_ACTION="http://tempuri.org/fare_inquiry";


    Spinner source_spin,desti_spin;
    Button get_fare;
    LinearLayout l_fare;
    TextView t1,t2;

    String source_data,desti_data;

    //ArrayList<Bus_route> bus_route_list = new ArrayList<>();

/*
    class Bus_route
    {
        String bus_id;
        String route;


        public Bus_route(String bus_id,String route) {
            this.bus_id = bus_id;
            this.route = route;


        }
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fare_inquiry);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fare Inquiry");

        source_spin = (Spinner) findViewById(R.id.source_spinner);
        desti_spin = (Spinner) findViewById(R.id.destination_spinner);
        get_fare = (Button) findViewById(R.id.get_fare_button);

        l_fare = (LinearLayout) findViewById(R.id.fare_layout);

        t1 = (TextView) findViewById(R.id.fare_equal_textView);
        t2 = (TextView) findViewById(R.id.actual_fare_textView);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(Fare_inquiry.this,android.R.layout.simple_spinner_dropdown_item,Fare_inquiry_bus_search.bus_route_list );

        source_spin.setAdapter(adapter);
        desti_spin.setAdapter(adapter);



        get_fare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Fare_inquiry.this,source_data+"55"+desti_spin.getSelectedItem(),Toast.LENGTH_SHORT).show();

                source_data=source_spin.getSelectedItem().toString();
                desti_data=desti_spin.getSelectedItem().toString();

                new get_fare().execute();

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

                Toast.makeText(Fare_inquiry.this,"Help ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class get_fare extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Fare_inquiry.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("bus_id", Fare_inquiry_bus_search.fare_bus_search.bus_id);
                request.addProperty("source",source_data);
                request.addProperty("destination",desti_data);


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
                l_fare.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
                t2.setText(result);

                Toast.makeText(Fare_inquiry.this, result, Toast.LENGTH_LONG).show();
            }
            else
            {
                t1.setVisibility(View.GONE);
                t2.setText("Please check your From and To station entry");
            }



        }
    }

}
