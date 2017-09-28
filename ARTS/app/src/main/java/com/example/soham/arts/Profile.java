package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Kelvin on 4/3/2017.
 */
public class Profile extends Activity {

    public static String NAMESPACE=Login.NAMESPACE;
    public static String URL=Login.URL;

    public static String METHOD_NAME="get_user_name";
    public static String SOAP_ACTION="http://tempuri.org/get_user_name";

    public static String METHOD_NAME1="fare_inquiry_bus_display";
    public static String SOAP_ACTION1="http://tempuri.org/fare_inquiry_bus_display";

    public static String username;

    TextView t1,t2,t3,t4;
    ImageButton editpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);

        t1 = (TextView) findViewById(R.id.profile_user);
        t2 = (TextView) findViewById(R.id.profile_email);
        t3 = (TextView) findViewById(R.id.profile_user_email);
        t4 = (TextView) findViewById(R.id.profile_change_pass);

        editpass = (ImageButton) findViewById(R.id.pass_edit_imageButton);

        new get_user().execute();

        SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
        //SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
        String u_email = applicationpreferences.getString("user_email", "");


        t2.setText("Email");
        t3.setText(u_email);
        t4.setText("Change Password");

        editpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Profile.this,Change_password.class);
                startActivity(i1);
            }
        });

    }


    public class get_user extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Profile.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {


                SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
                //SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
                String u_email = applicationpreferences.getString("user_email", "");

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("email", u_email);

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

            username=result;
            t1.setText(username);
            Toast.makeText(Profile.this, result, Toast.LENGTH_LONG).show();




        }
    }


}
