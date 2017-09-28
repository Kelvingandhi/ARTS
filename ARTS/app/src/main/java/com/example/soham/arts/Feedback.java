package com.example.soham.arts;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by 100HM on 09/05/2017.
 */
public class Feedback extends AppCompatActivity {


    public static String NAMESPACE= Login.NAMESPACE;
    public static String URL=Login.URL;
    public static String METHOD_NAME="feedback";
    public static String SOAP_ACTION="http://tempuri.org/feedback";


    EditText e1;

    RatingBar r1;

    Button submit;

    String rating_val="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.feedback);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");
        e1 = (EditText) findViewById(R.id.feedback_Text);
        r1=(RatingBar) findViewById(R.id.ratingBar);
        submit = (Button) findViewById(R.id.submit_feedback);

        r1.setStepSize((float) 0.3);

        r1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // TODO Auto-generated method stub

                rating_val=String.valueOf(rating);
                Toast.makeText(Feedback.this, rating_val, Toast.LENGTH_LONG).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new set_feedback().execute();

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

                Toast.makeText(Feedback.this,"Help ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public class set_feedback extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Feedback.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.mipmap.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {

                SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
                //SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
                String u_email = applicationpreferences.getString("user_email", "");


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("email", u_email);
                request.addProperty("description",e1.getText().toString());
                request.addProperty("stars",rating_val);


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

            Toast.makeText(Feedback.this, result, Toast.LENGTH_LONG).show();

        }
    }


}
