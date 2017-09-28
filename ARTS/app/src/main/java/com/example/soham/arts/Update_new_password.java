package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Kelvin on 4/3/2017.
 */
public class Update_new_password extends Activity {

    public static String NAMESPACE=Login.NAMESPACE;
    public static String URL=Login.URL;

    public static String METHOD_NAME="save_change_password";
    public static String SOAP_ACTION="http://tempuri.org/save_change_password";


    EditText e1,e2;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_new_pass_layout);

        e1 = (EditText) findViewById(R.id.new_pass_for_change_pass);
        e2 = (EditText) findViewById(R.id.confi_new_pass);
        update = (Button) findViewById(R.id.change_pass_update_btn);



            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!e2.getText().toString().equals(e1.getText().toString()))
                    {
                        e2.setError("Incorrect password");
                    }
                    else {
                    new update_new_pass().execute();

                    }
                }
            });

    }



    public class update_new_pass extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Update_new_password.this);
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
                request.addProperty("pass", e1.getText().toString());

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


                Toast.makeText(Update_new_password.this, result, Toast.LENGTH_LONG).show();

            /*SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
            editor.clear();

            editor.putBoolean("flag", false);
            editor.commit();
            finish();*/

            Intent i1 = new Intent(Update_new_password.this,Success_change_pass.class);
            startActivity(i1);


        }
    }

    
}
