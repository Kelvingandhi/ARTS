package com.example.kelvin.my_conductor_app.lost_and_found_files;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kelvin.my_conductor_app.Login;
import com.example.kelvin.my_conductor_app.MainActivity;
import com.example.kelvin.my_conductor_app.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Kelvin on 4/4/2017.
 */
public class Give_response extends AppCompatActivity {

    public static String NAMESPACE= Login.NAMESPACE;
    public static String URL=Login.URL;
    public static String METHOD_NAME="lost_found_data_update_status";
    public static String SOAP_ACTION="http://tempuri.org/lost_found_data_update_status";

    EditText e1;
    Button submit;
    int pos;
    public String ticketid;

    public static ArrayList<Pending_requests_l_f.pending_req> pending_req_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.give_res_layout);

        e1 = (EditText) findViewById(R.id.write_response);
        submit = (Button) findViewById(R.id.submit_res_btn);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Give Response");

        Intent i = getIntent();
        pos = i.getIntExtra("position",5);

        ticketid=Pending_requests_l_f.pending_req_array_list.get(pos).ticket_id;

        Log.d("message", "Message1 : " +ticketid );


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new send_response().execute();

            }
        });


    }

    public class send_response extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Give_response.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.mipmap.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                request.addProperty("updated_status",e1.getText().toString());
                Log.d("message", "Message_new : " +e1.getText().toString() );
                request.addProperty("ticket_id",ticketid);

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



            Toast.makeText(Give_response.this, result, Toast.LENGTH_LONG).show();



            Intent i1 = new Intent(Give_response.this, MainActivity.class);
            startActivity(i1);




        }
    }


}
