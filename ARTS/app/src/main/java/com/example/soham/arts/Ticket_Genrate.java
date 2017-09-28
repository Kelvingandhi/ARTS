package com.example.soham.arts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 100HM on 18/04/2017.
 */






/**
 * Created by 100HM on 07-03-2017.
 */
public class Ticket_Genrate extends AppCompatActivity implements View.OnClickListener {
    static Spinner sourc,desti;
    public static RadioButton r1,r2;
    Button strip,rtrip;
    DatePicker retn;
    public static Spinner psgr;
    static CheckBox slady;
    Button submit;
    TextView text3,text4,text5,text6;
    TextView price;
    ImageView image;
    String s1,source1;
    public static String trip="2",booking_date="";


    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "Compare_date";
    public static String SOAP_ACTION = "http://tempuri.org/Compare_date";


    Integer amount;
    //  SQLiteDatabase db;

    public static EditText fromDateEtxt;
    public static EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;


    ArrayList<String> list= new ArrayList<String>();

    ArrayList<String> list1= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ticket_genrt);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket Genrate");

        sourc = (Spinner) findViewById(R.id.source);
        desti = (Spinner) findViewById(R.id.destination);
        r1=(RadioButton)findViewById(R.id.local);
        r2=(RadioButton)findViewById(R.id.express);

        strip = (Button) findViewById(R.id.oneway);
        rtrip = (Button) findViewById(R.id.round);
        // toward = (EditText) findViewById(R.id.date1);
        // retn = (DatePicker) findViewById(R.id.datePicker2);
        psgr = (Spinner) findViewById(R.id.spinner);
        slady = (CheckBox) findViewById(R.id.singlelady);
        //price = (TextView) findViewById(R.id.price);
        submit = (Button) findViewById(R.id.btn1);
        // image = (ImageView) findViewById(R.id.QR_code);




        list.add("ahmedabad");
        list.add("surat");
        list.add("udhna");
        list.add("bhestan");
        list.add("maroli");
        list.add("navsari");
        list.add("vapi");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Ticket_Genrate.this, android.R.layout.simple_spinner_dropdown_item, list);
        sourc.setAdapter(adapter);
        desti.setAdapter(adapter);



      /*  for(int i=0;i<adapter.getCount();i++)
        {
            if(adapter.getItem(i).toString().equals(Between_station.source.getText().toString()))
            {
                sourc.setSelection(i);
            }
        }

        for(int i=0;i<adapter.getCount();i++)
        {
            if(adapter.getItem(i).toString().equals(Between_station.destination.getText().toString()))
            {
                desti.setSelection(i);
            }
        }

        */


        //dateFormatter = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss", Locale.US);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();

        setDateTimeField();

        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Ticket_Genrate.this, android.R.layout.simple_spinner_dropdown_item, list1);
        psgr.setAdapter(adapter1);


        // booking_date= dateFormatter.getDateTimeInstance().format(new Date());

        /*Calendar c = Calendar.getInstance();

        booking_date =  c.get(Calendar.DAY_OF_MONTH) + "/"
                + c.get(Calendar.MONTH)
                + "/" + c.get(Calendar.YEAR)
                + " at " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);
                */
        //  display the current date
        //toward.setText(Date);

       /* slady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((slady)view).isChecked())
                {
                    text5 = (TextView) findViewById(R.id.textView5);
                    text5.setVisibility(View.GONE);
                    psgr = (Spinner) findViewById(R.id.spinner);
                    psgr.setVisibility(View.GONE);
                }
                else
                {
                    text5 = (TextView) findViewById(R.id.textView5);
                    text5.setVisibility(View.VISIBLE);
                    psgr = (Spinner) findViewById(R.id.spinner);
                    psgr.setVisibility(View.VISIBLE);
                }
            }
        });*/

     /*   if(View_between_station.status.toString().equals("local"))
        {
            Toast.makeText(Ticket_Genrate.this, "local", 0).show();
            r1.isChecked();
        }
        else
        {
            Toast.makeText(Ticket_Genrate.this,"express",0).show();
            r2.isChecked();
        }

     */

        slady.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    text5 = (TextView) findViewById(R.id.textView5);
                    text5.setVisibility(View.GONE);
                    psgr = (Spinner) findViewById(R.id.spinner);
                    psgr.setVisibility(View.GONE);
                    psgr.setSelection(0);
                } else {
                    text5 = (TextView) findViewById(R.id.textView5);
                    text5.setVisibility(View.VISIBLE);
                    psgr = (Spinner) findViewById(R.id.spinner);
                    psgr.setVisibility(View.VISIBLE);
                }

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new compare_date().execute();
                //Toast.makeText(Ticket_Genrate.this, psgr.getSelectedItem().toString(), 0).show();

                //new Ticket().execute();
            }
        });


    }




    /////  date picker in edittext code : http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event/



    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


       /* Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth ,int hours ,int minutes ,int seconds) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth,hours,minutes,seconds);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
                , newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), newCalendar.get(Calendar.SECOND));
                */
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.help_icon:

                Toast.makeText(Ticket_Genrate.this,"ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);                                     //main replace by login.. but i don't know
        // can't affect in o/p
        return true;
    }


    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }


    public void way(View view) {
        switch (view.getId()) {
            case R.id.oneway:
                text4 = (TextView) findViewById(R.id.textView4);
                text4.setVisibility(View.GONE);
                toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
                toDateEtxt.setVisibility(View.GONE);

                toDateEtxt.setText("");
                // new single_trip().execute();
                trip="1";


                break;
            case R.id.round:
                text4 = (TextView) findViewById(R.id.textView4);
                text4.setVisibility(View.VISIBLE);
                toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
                toDateEtxt.setVisibility(View.VISIBLE);

                trip="2";

                // new round_trip().execute();
                break;
            default:
                break;
        }
    }

    private class compare_date extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Ticket_Genrate.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                request.addProperty("journey_date", fromDateEtxt.getText().toString());
                request.addProperty("return_date", toDateEtxt.getText().toString());



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

            //Toast.makeText(Ticket_Genrate.this, result, Toast.LENGTH_SHORT).show();

            if(result.equals("1"))               //  -1  :  journey_date < return_date
            {
                Toast.makeText(Ticket_Genrate.this,"please... insert valid date", Toast.LENGTH_SHORT).show();

            }
            else
            {
                Intent i1 = new Intent(Ticket_Genrate.this, Fatch_BusList.class);
                startActivity(i1);

            }

        }


    }

}

