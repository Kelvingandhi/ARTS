package com.example.soham.arts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by 100HM on 16/05/2017.
 */
public class Invite_code extends AppCompatActivity{
    public static String NAMESPACE= Login.NAMESPACE;
    public static String URL=Login.URL;

    public static String METHOD_NAME="registration";
    public static String SOAP_ACTION="http://tempuri.org/registration";

    public static String METHOD_NAME2="registration_with_promocode";
    public static String SOAP_ACTION2="http://tempuri.org/registration_with_promocode";


    public static boolean tag;

    EditText code;
    Button submit,otp_edit_email;

    public String fullname,email,pass,conpass,gender,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.invite_code);


        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor otpeditor = prefs.edit();
        otpeditor.putBoolean("tagvalue",false);
        otpeditor.commit();

        code = (EditText) findViewById(R.id.promo_code);
        submit = (Button) findViewById(R.id.done);


        Intent i1 = getIntent();
        fullname = i1.getStringExtra("full_name");
        email = i1.getStringExtra("email");
        pass = i1.getStringExtra("pass");
        conpass = i1.getStringExtra("con_pass");
        gender = i1.getStringExtra("gender");
        mobile = i1.getStringExtra("mobile_no");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new Regi().execute();

                if(code.getText().toString().equals(""))
                {
                    new Regi().execute();
                }
                else
                {
                    new registration_with_promo().execute();
                }

            }
        });



    }

    private class Regi  extends AsyncTask<Void , Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog=new ProgressDialog(Invite_code.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please  wait...");
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub

            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                /*
                request.addProperty("name", fullname);
                request.addProperty("password", pass);
                request.addProperty("con_pass", conpass);
                request.addProperty("mobile", mobile);


                request.addProperty("email", email);


                    request.addProperty("gender", gender);
                */

                request.addProperty("name", Registration.e5.getText().toString());
                request.addProperty("password", Registration.e2.getText().toString());
                request.addProperty("con_pass", Registration.e3.getText().toString());
                request.addProperty("mobile", Registration.e4.getText().toString());


                request.addProperty("email", Registration.e1.getText().toString());

                if(Registration.r1.isChecked())
                {
                    request.addProperty("gender", Registration.r1.getText().toString());
                }
                else
                {
                    request.addProperty("gender", Registration.r2.getText().toString());
                }


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

                Log.d("message","Message : "+resultstr.toString());
                return resultstr.toString();


            }
            catch(Exception e)
            {
                Log.e("ERROR", e.toString());
                return "fail";

            }


        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss();
            Toast.makeText(Invite_code.this, result, Toast.LENGTH_LONG).show();

            Intent i1 = new Intent(Invite_code.this,Login.class);
            startActivity(i1);

            finish();
        }

    }



    public class registration_with_promo extends AsyncTask<Void , Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog=new ProgressDialog(Invite_code.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please  wait...");
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub

            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                request.addProperty("name", Registration.e5.getText().toString());
                request.addProperty("password", Registration.e2.getText().toString());
                request.addProperty("con_pass", Registration.e3.getText().toString());
                request.addProperty("mobile", Registration.e4.getText().toString());


                request.addProperty("email", Registration.e1.getText().toString());

                if(Registration.r1.isChecked())
                {
                    request.addProperty("gender", Registration.r1.getText().toString());
                }
                else
                {
                    request.addProperty("gender", Registration.r2.getText().toString());
                }

                request.addProperty("promo_code", code.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION2, envelope);

                SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

                Log.d("message","Message : "+resultstr.toString());
                return resultstr.toString();


            }
            catch(Exception e)
            {
                Log.e("ERROR", e.toString());
                return "fail";

            }


        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss();
            Toast.makeText(Invite_code.this, result, Toast.LENGTH_LONG).show();

            Intent i1 = new Intent(Invite_code.this,Login.class);
            startActivity(i1);

            finish();
        }

    }



}
