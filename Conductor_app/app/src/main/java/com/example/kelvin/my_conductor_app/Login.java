package com.example.kelvin.my_conductor_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Kelvin on 3/6/2017.
 */
public class Login extends Activity {

    public static String single_login_email,single_login_pass;
    public static EditText email,pass;
    Button b1;
    TextView b2;

    public static String NAMESPACE="http://tempuri.org/";
    public static String URL = "http://192.168.43.162/WebARTS/WebService.asmx";

    public static String METHOD_NAME="conductor_login";
    public static String SOAP_ACTION="http://tempuri.org/conductor_login";
    public static String METHOD_NAME1="conductor_bus_id";
    public static String SOAP_ACTION1="http://tempuri.org/conductor_bus_id";

    public static final String PREFS_NAME = "LoginPrefs";

    public static String loginemail;

    public static String bus_id;
    SharedPreferences applicationpreferences;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        Boolean flag;

        applicationpreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        editor = applicationpreferences.edit();

        flag = applicationpreferences.getBoolean("flag", false);

        if (flag) {
            ///second time activity
            Intent i1= new Intent(Login.this, MainActivity.class);
            startActivity(i1);


        }
        else{
                //first time


            setContentView(R.layout.login);
            email = (EditText) findViewById(R.id.login_email_editText);
            pass = (EditText) findViewById(R.id.login_pass_editText);
            b1 = (Button) findViewById(R.id.login_button);
            //b2 = (TextView) findViewById(R.id.regi_button);

            email.setText("");
            pass.setText("");

            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                    loginemail=email.getText().toString();
                    editor.putString("user_email",loginemail);
                    editor.putBoolean("flag", true);
                    editor.commit();

                    new c_login().execute();
                }
            });


        /*
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1= new Intent(Login.this, Registration.class);
                startActivity(i1);
            }
        });*/

            editor.putBoolean("flag", true);
            editor.commit();
        }


    }






    public class c_login extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog=new ProgressDialog(Login.this);
            dialog.setTitle("Loading...");
            dialog.setMessage("Please Wait");
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.show();
        }


        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("email",email.getText().toString());
                request.addProperty("pass",pass.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

                Log.d("message","Message : "+resultstr.toString());
                //Toast.makeText(Login.this, "valid", 0).show();
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

            single_login_email = email.getText().toString();
            single_login_pass = pass.getText().toString();

            if(result.equals("true"))
            {
                //new fetch_bus_id().execute();
                Intent i2= new Intent(Login.this, MainActivity.class);
                startActivity(i2);


            }
            else
            {
                Toast.makeText(Login.this, result, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /*
    private class fetch_bus_id extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog=new ProgressDialog(Login.this);
            dialog.setTitle("Loading...");
            dialog.setMessage("Please Wait");
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.show();
        }


        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
                request.addProperty("email",email.getText().toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

                Log.d("message","Message : "+resultstr.toString());
                //Toast.makeText(Login.this, "valid", 0).show();
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

            editor.putString("conductor_bus_id",result);
            editor.putBoolean("flag", true);
            editor.commit();
            //bus_id=result;
                Toast.makeText(Login.this, result, Toast.LENGTH_LONG).show();

        }

    }*/
}
