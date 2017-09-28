package com.example.soham.seatBookingrecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soham.arts.Booking_true;
import com.example.soham.arts.Display_price;
import com.example.soham.arts.Fatch_BusList;
import com.example.soham.arts.Login;
import com.example.soham.arts.R;
import com.example.soham.arts.Ticket_Genrate;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSeatSelected {

    private static final int COLUMNS = 4;
    private TextView txtSeatSelected;

    public static String flag="";

    Button book_done;
    int[] a;
    List<Integer> newList;

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "seatmap";
     public static String METHOD_NAME2="Booking_done";
    public static String METHOD_NAME3="Payment";
    public static String METHOD_NAME1 = "Booked_seat";

     public static String SOAP_ACTION1 = "http://tempuri.org/Booked_seat";

    public static String METHOD_NAME4 = "seatmap_return";

    public static String SOAP_ACTION4 = "http://tempuri.org/seatmap_return";

    //public static String METHOD_NAME3="dlt_resurved_seat";

    public static String SOAP_ACTION = "http://tempuri.org/seatmap";
    public static String SOAP_ACTION2 = "http://tempuri.org/Booking_done";
    public static String SOAP_ACTION3 = "http://tempuri.org/Payment";
    //public static String SOAP_ACTION3 = "http://tempuri.org/dlt_resurved_seat";

    public static ArrayList<Booked_seats> reserved = new ArrayList<>();
    public static List<AbstractItem> items;
    public static ArrayList<Integer> count;
    public static ArrayList<Integer> seats = new ArrayList<>();




    public class Booked_seats
    {
        int booked_seats_map;

        public Booked_seats(int booked_seats_map) {
            this.booked_seats_map = booked_seats_map;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket Genrate");

        txtSeatSelected = (TextView)findViewById(R.id.txt_seat_selected);
        book_done = (Button) findViewById(R.id.book_button);

        items = new ArrayList<>();




        //seats.add(5);
        int j=0;

/*        for (int i=1; i<=28; i++) {
          /*  if (i%COLUMNS==0 || i%COLUMNS==4) {
                items.add(new EdgeItem(String.valueOf(i)));
            } else if (i%COLUMNS==1 || i%COLUMNS==3) {
                items.add(new CenterItem(String.valueOf(i)));
            } else if (i==seats.get(j))
            {
                items.add(new BookedItem(String.valueOf(i)));
                j++;
                if(j>seats.size())
                {
                    break;
                }
            }
            else {
                items.add(new EmptyItem(String.valueOf(i)));
            }*/
/*            if (Display_price.seats.contains(i))
            {
                items.add(new BookedItem(String.valueOf(i)));
                //j++;
                //if(j>seats.size())
                //{
                //    break;
                //}
            }
            /*else if (i%COLUMNS==0) {
                items.add(new EdgeItem(String.valueOf(i)));
            } else if (i%COLUMNS==1 || i%COLUMNS==3) {
                items.add(new CenterItem(String.valueOf(i)));
            }*/
/*            else{
                items.add(new EdgeItem(String.valueOf(i)));
            }





        }*/

        new Booked().execute();


        //AirplaneAdapter1 adapter1 = new AirplaneAdapter1(this,items);
        //recyclerView.setAdapter(adapter1);


        //Toast.makeText(MainActivity.this,Integer.parseInt(Ticket_Genrate.psgr.getSelectedItem().toString()),0).show();

        book_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new payment().execute();


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

                Toast.makeText(MainActivity.this,"ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSeatSelected(ArrayList<Integer> count) {

        flag="";
        txtSeatSelected.setText("Book " + count + " seats");
       // Toast.makeText(MainActivity.this,Integer.parseInt(Ticket_Genrate.psgr.getSelectedItem().toString()),0).show();
        if(count.size()<=Integer.parseInt(Ticket_Genrate.psgr.getSelectedItem().toString()))
        {


        for (int i = 0; i < count.size(); i++) {


            flag = flag + count.get(i) + ",";
        }


        }
        else {
            Toast.makeText(MainActivity.this,"max 5 seat select",Toast.LENGTH_SHORT).show();
        }




        Toast.makeText(MainActivity.this,flag.toString(),Toast.LENGTH_SHORT).show();
    }



    public class Booked extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

               /* request.addProperty("bus_id", Fatch_BusList.Buse_list.get(Fatch_BusList.posit).bus_id.toString());
                request.addProperty("date", Ticket_Genrate.fromDateEtxt.getText().toString());
                request.addProperty("departure_time", Fatch_BusList.Buse_list.get(Fatch_BusList.posit).despatch_time.toString());
                request.addProperty("arrival_time",  Fatch_BusList.Buse_list.get(Fatch_BusList.posit).arrival_time.toString());

                */

                request.addProperty("bus_id", Display_price.bus_id.getText().toString());
                request.addProperty("date", Display_price.toward_date.getText().toString());

                request.addProperty("sour", Display_price.source.getText().toString());
                request.addProperty("dest", Display_price.destination.getText().toString());

                //request.addProperty("departure_time", Display_price.departure_time_1.getText().toString());
                //request.addProperty("arrival_time", Display_price.arrival_time_1.getText().toString());

                //Toast.makeText(Display_price.this,bus_id+""+Ticket_Genrate.fromDateEtxt+""+departure_time_1+""+arrival_time_1,0).show();
                //  request.addProperty("destination", Bus_between_station.desti.getText().toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "Message : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";

            }

            // return null;
        }

        public void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();
            //Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();

            //String rows[] = result.split(",");

         /*   for (int i = 0; i < rows.length; i++) {
                Booked_seats temp = new Booked_seats(Integer.parseInt(rows[i]));
                reserved.add(temp);
            }

            */
           /* newList = new ArrayList<Integer>(reserved.size()) ;
            for (Booked_seats myInt : reserved)
            {
                newList.add(Integer.valueOf(String.valueOf(myInt)));
            }
            //System.out.println(newList);
            */

           /* ArrayList<Integer> numbers = new ArrayList<Integer>();

            for(int i = 0; i < reserved.size(); i++) {
                numbers.add(Integer.parseInt(Booked_seats.get(i)));
                Toast.makeText(MainActivity.this,numbers.get(i),Toast.LENGTH_SHORT).show();
            }
            */


            if (result.toString().equals("fail")) {


                Toast.makeText(MainActivity.this, "no Booked seat", Toast.LENGTH_SHORT).show();
                //String[] arr=result.split(",");

            } else {

                String rows[] = result.split(",");
                seats.clear();
                for (int i = 0; i < rows.length; i++) {

                    seats.add(Integer.parseInt(rows[i]));

                    //seats.notify();



                       /* if (seats.contains(i))
                        {
                            items.add(new BookedItem(String.valueOf(i)));
                            //j++;
                            //if(j>seats.size())
                            //{
                            //    break;
                            //}
                        }
                        else{
                            items.add(new EdgeItem(String.valueOf(i)));
                        }*/








                    //Toast.makeText(MainActivity.this,seats.get(i).toString(),Toast.LENGTH_SHORT).show();
                }


            }

            //System.out.println(abc.toString());

            // Toast.makeText(MainActivity.this,seats.get(0).toString(),Toast.LENGTH_SHORT).show();
            //Toast.makeText(Display_price.this, result, Toast.LENGTH_SHORT).show();

              for (int i=1; i<=28; i++)
                {

                    if (seats.contains(i))
                    {
                        items.add(new BookedItem(String.valueOf(i)));

                    }

                    else{
                        items.add(new EdgeItem(String.valueOf(i)));
                    }
                }


            GridLayoutManager manager = new GridLayoutManager(MainActivity.this, COLUMNS);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
            recyclerView.setLayoutManager(manager);

            AirplaneAdapter adapter = new AirplaneAdapter(MainActivity.this, items);


            recyclerView.setAdapter(adapter);

        }
    }



    /*

    public class Booked extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                request.addProperty("bus_id", Display_price.bus_id.getText().toString());
                request.addProperty("date", Display_price.toward_date.getText().toString());
                request.addProperty("departure_time", Display_price.departure_time_1.getText().toString());
                request.addProperty("arrival_time", Display_price.arrival_time_1.getText().toString());


                //  request.addProperty("destination", Bus_between_station.desti.getText().toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION2, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "Message : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";

            }

            // return null;
        }

        public void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();
            //Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();

            //String rows[] = result.split(",");

         /*   for (int i = 0; i < rows.length; i++) {
                Booked_seats temp = new Booked_seats(Integer.parseInt(rows[i]));
                reserved.add(temp);
            }

            */
           /* newList = new ArrayList<Integer>(reserved.size()) ;
            for (Booked_seats myInt : reserved)
            {
                newList.add(Integer.valueOf(String.valueOf(myInt)));
            }
            //System.out.println(newList);
            */

           /* ArrayList<Integer> numbers = new ArrayList<Integer>();

            for(int i = 0; i < reserved.size(); i++) {
                numbers.add(Integer.parseInt(Booked_seats.get(i)));
                Toast.makeText(MainActivity.this,numbers.get(i),Toast.LENGTH_SHORT).show();
            }
            */



            /*
            //String[] arr=result.split(",");
           String rows[] = result.split(",");
            for(int i=0;i<rows.length;i++){

                seats.add(Integer.parseInt(rows[i]));
                //seats.notify();



                //Toast.makeText(MainActivity.this,seats.get(i).toString(),Toast.LENGTH_SHORT).show();
            }

            //System.out.println(abc.toString());

           // Toast.makeText(MainActivity.this,seats.get(0).toString(),Toast.LENGTH_SHORT).show();
        }

    }



    */


    private class sit extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("bus_id", Display_price.bus_id.getText().toString());

                request.addProperty("sour", Display_price.source.getText().toString());
                request.addProperty("dest", Display_price.destination.getText().toString());
                request.addProperty("date", Display_price.toward_date.getText().toString());

                //request.addProperty("departure_time", Display_price.departure_time_1.getText().toString());
                //request.addProperty("arrival_time", Display_price.arrival_time_1.getText().toString());

                request.addProperty("sit_no", flag.toString());

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
           Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();



        }

    }


    private class sit1 extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME4);

                request.addProperty("bus_id", Display_price.bus_id.getText().toString());
                request.addProperty("sour", Display_price.destination.getText().toString());
                request.addProperty("dest", Display_price.source.getText().toString());
                request.addProperty("date", Display_price.return_date.getText().toString());


                request.addProperty("sit_no", flag.toString());

                //  request.addProperty("destination", Bus_between_station.desti.getText().toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION4, envelope);

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
            Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();



        }

    }

    private class booking extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SharedPreferences applicationpreferences =  getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);

                String email = applicationpreferences.getString("user_email","");
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                request.addProperty("bus_id", Display_price.bus_id.getText().toString());
                request.addProperty("user_name",email);
                request.addProperty("booking_date",Ticket_Genrate.booking_date.toString());
                request.addProperty("source", Display_price.source.getText().toString());
                request.addProperty("destination", Display_price.destination.getText().toString());

                request.addProperty("toward_date", Display_price.toward_date.getText().toString());

                request.addProperty("return_date", Display_price.return_date.getText().toString());
                request.addProperty("departure_time_1", Display_price.departure_time_1.getText().toString());
                request.addProperty("arrival_time_1", Display_price.arrival_time_1.getText().toString());
                request.addProperty("departure_time_2", Display_price.departure_time_2.getText().toString());

                request.addProperty("arrival_time_2", Display_price.arrival_time_2.getText().toString());
                if (Ticket_Genrate.r1.isChecked()) {
                    request.addProperty("status", "local");
                    // s1="local";
                } else {
                    request.addProperty("status", "express");
                    //s1="express";

                }
                request.addProperty("passenger", Display_price.passenger.getText().toString());
                request.addProperty("single_lady", Display_price.single_lady.getText().toString());
                request.addProperty("seat_no", flag.toString());

                request.addProperty("price",Display_price.price.getText().toString());

                request.addProperty("user_email",email);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION2, envelope);

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
            Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();



        }

    }



    private class payment extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SharedPreferences applicationpreferences =  getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);

                String email = applicationpreferences.getString("user_email","");
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);

                request.addProperty("minus_money", Display_price.price.getText().toString());
                request.addProperty("email", email);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION3, envelope);

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
            Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();

            if(result.toString().equals("true"))
            {
                new sit().execute();

                if(!Ticket_Genrate.toDateEtxt .getText().toString().equals(""))
                {

                    new sit1().execute();
                }

                new booking().execute();

                Intent i1= new Intent(MainActivity.this,Booking_true.class);
                startActivity(i1);
            }
            else
            {
                Toast.makeText(MainActivity.this,"Please Frist REcharge Your Wallet...!!!", Toast.LENGTH_SHORT).show();

            }


        }

    }
}
