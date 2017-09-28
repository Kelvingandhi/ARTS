package com.example.kelvin.my_conductor_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class After_scan_Activity extends AppCompatActivity {

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;
    public static String METHOD_NAME = "check_qr_ticket";
    public static String SOAP_ACTION = "http://tempuri.org/check_qr_ticket";

    Button redirect_scan_btn;
    ListView qr_ticket_details_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_scan_);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket Detail");

        qr_ticket_details_list = (ListView) findViewById(R.id.QR_ticket_listView);
        redirect_scan_btn = (Button) findViewById(R.id.redirect_scan_button);

        CustomView_QRticket_details qradapter = new CustomView_QRticket_details(After_scan_Activity.this, MainActivity.ticket_value_list);

        qr_ticket_details_list.setAdapter(qradapter);

        redirect_scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(After_scan_Activity.this, MainActivity.class);
                startActivity(i1);
            }
        });


        new check_qr_details().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class check_qr_details extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(After_scan_Activity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.mipmap.ic_launcher);
        }


        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("ticket_id", MainActivity.ticket_value_list.get(0).ticket_id);
                request.addProperty("bus_id", MainActivity.ticket_value_list.get(0).bus_id);
                request.addProperty("u_name", MainActivity.ticket_value_list.get(0).u_name);


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

           /* Toast toast = new Toast(getApplicationContext());

            toast.setGravity(Gravity.CENTER,0,-150);
            toast.setDuration(Toast.LENGTH_LONG);


            LayoutInflater inf = getLayoutInflater();

            View layout = inf.inflate(R.layout.toastcustom,(ViewGroup)findViewById(R.id.toastlayout));

            toast.setView(layout);
            toast.show();
*/
            Toast.makeText(After_scan_Activity.this, result, Toast.LENGTH_SHORT).show();

        }
    }
}
