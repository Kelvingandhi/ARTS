package com.example.soham.arts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Kelvin on 4/3/2017.
 */
public class Lost_item_request extends AppCompatActivity implements View.OnClickListener {

    public static String NAMESPACE="http://tempuri.org/";
    public static String URL=Login.URL;
    public static String METHOD_NAME="lost_and_found_insert_data";
    public static String SOAP_ACTION="http://tempuri.org/lost_and_found_insert_data";


    EditText e1,e2,e3;
    Spinner spi_bus_id;
    Button submit_req;
    String get_bus_id_spinner;

    private DatePickerDialog DatePickerDialog;

    private SimpleDateFormat dateFormatter;


    ArrayList<String> bus_id_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lost_item_req_layout);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lost Item Request");

        e2 = (EditText) findViewById(R.id.landf_item_name);
        e3 = (EditText) findViewById(R.id.landf_item_descri);

        spi_bus_id = (Spinner) findViewById(R.id.bus_id_spinner);

        submit_req = (Button) findViewById(R.id.landf_req_btn);

        bus_id_list.add("1990");
        bus_id_list.add("1234");
        bus_id_list.add("1992");


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();

        setDateTimeField();

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(Lost_item_request.this,android.R.layout.simple_spinner_dropdown_item,bus_id_list );

        spi_bus_id.setAdapter(adapter);

        submit_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_bus_id_spinner = spi_bus_id.getSelectedItem().toString();

                new send_request().execute();

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

                Toast.makeText(Lost_item_request.this,"Help ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void findViewsById() {
        e1 = (EditText) findViewById(R.id.landfjour_date);
        e1.setInputType(InputType.TYPE_NULL);
        e1.requestFocus();

    }

    private void setDateTimeField() {
        e1.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                e1.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if(view == e1) {
            DatePickerDialog.show();
        }
    }


    public class send_request extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Lost_item_request.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.mipmap.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("journey_date", e1.getText().toString());
                request.addProperty("bus_id", get_bus_id_spinner);
                request.addProperty("item_name",e2.getText().toString());
                request.addProperty("item_description",e3.getText().toString());


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



            Toast.makeText(Lost_item_request.this, result, Toast.LENGTH_LONG).show();




        }
    }



}
