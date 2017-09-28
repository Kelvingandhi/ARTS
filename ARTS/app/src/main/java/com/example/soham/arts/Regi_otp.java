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
public class Regi_otp extends AppCompatActivity {

    public static String NAMESPACE= Login.NAMESPACE;
    public static String URL=Login.URL;
    public static String METHOD_NAME="registration_verify_otp";
    public static String SOAP_ACTION="http://tempuri.org/registration_verify_otp";

    public static boolean tag;

    public String email_otp;

    EditText otp_num;
    Button submit,otp_edit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.regi_otp);

        otp_num = (EditText) findViewById(R.id.OTP_number);
        submit = (Button) findViewById(R.id.otp_submit_button);

        otp_edit_email = (Button) findViewById(R.id.otp_edit_email);

        Intent i1 = getIntent();

        email_otp = i1.getStringExtra("email");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new verify_regi_otp().execute();

            }
        });

        otp_edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("tagvalue",false);
                editor.commit();

                Intent i1 = new Intent(Regi_otp.this,Registration.class);
                startActivity(i1);

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("tagvalue",true);
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }


    public class verify_regi_otp extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Regi_otp.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.mipmap.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("email_id",email_otp);
                request.addProperty("otp",otp_num.getText().toString());



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

            if(result.equals("true"))
            {
                Intent i1 = new Intent(Regi_otp.this,Invite_code.class);
                startActivity(i1);
            }
            else
            {
                Toast.makeText(Regi_otp.this, "Please enter valid OTP" , Toast.LENGTH_LONG).show();
            }

        }
    }


}
