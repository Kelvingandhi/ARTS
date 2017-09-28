package com.example.soham.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soham.arts.Login;
import com.example.soham.arts.R;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by 100HM on 23-03-2017.
 */
public class Card_detail_verify extends Activity {

    public static EditText card_no,cvv_no,expiry_date,card_name ;
    Button done;

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "Add_amount";
    public static String SOAP_ACTION = "http://tempuri.org/Add_amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail_verify);


        card_no=(EditText)findViewById(R.id.card_no);
        cvv_no=(EditText)findViewById(R.id.cvv_no);
        expiry_date=(EditText)findViewById(R.id.expiry_date);
        card_name=(EditText)findViewById(R.id.card_name);
        done=(Button)findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i1 = new Intent(Add_money.this, Card_detail_verify.class);
                //startActivity(i1);
                new adds_money().execute();
            }
        });



    }

    private class adds_money extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Card_detail_verify.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {

                SharedPreferences applicationpreferences =  getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);

                String email = applicationpreferences.getString("user_email","");
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("debit_no",card_no.getText().toString());
                request.addProperty("cvv_no", cvv_no.getText().toString());
                request.addProperty("expiry_date", expiry_date.getText().toString());
                request.addProperty("name", card_name.getText().toString());

                request.addProperty("add_money", Add_money.add_money.getText().toString());

                request.addProperty("email",email);
                request.addProperty("total_money",Add_money.total.getText().toString());
                //  request.addProperty("destination", Bus_between_station.desti.getText().toString());


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
            Toast.makeText(Card_detail_verify.this, result, Toast.LENGTH_SHORT).show();



        }

    }

}
