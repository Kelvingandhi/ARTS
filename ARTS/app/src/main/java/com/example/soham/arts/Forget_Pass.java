package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
 * Created by 100HM on 04-03-2017.
 */
public class Forget_Pass extends Activity {

    public static EditText email,otp;
    Button enter,done;

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = "http://192.168.43.162/WebSiteARTS/WebService.asmx";

    public static String METHOD_NAME = "password";
    public static String SOAP_ACTION = "http://tempuri.org/password";


    public static String METHOD_NAME2 = "verify_otp";
    public static String SOAP_ACTION2 = "http://tempuri.org/verify_otp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass);

        email = (EditText) findViewById(R.id.email1);
        enter = (Button) findViewById(R.id.enter);
        otp = (EditText) findViewById(R.id.otp);
        done = (Button) findViewById(R.id.done);
        otp.setVisibility(View.GONE);
        done.setVisibility(View.GONE);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new forget_pass().execute();

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new verify_otp().execute();

            }
        });


    }

    private class forget_pass extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Forget_Pass.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("email_id", email.getText().toString());


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

            Toast.makeText(Forget_Pass.this, result, Toast.LENGTH_LONG).show();
            otp.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);


        }

    }

    private class verify_otp extends AsyncTask<Void, Void, String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Forget_Pass.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                request.addProperty("email_id", email.getText().toString());
                request.addProperty("otp", otp.getText().toString());

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

            if(result.toString().equals("true")) {
                Toast.makeText(Forget_Pass.this, result, Toast.LENGTH_LONG).show();

                Intent i1 = new Intent(Forget_Pass.this, Update_pass.class);
                startActivity(i1);
            }
            else
            {
                Toast.makeText(Forget_Pass.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
}