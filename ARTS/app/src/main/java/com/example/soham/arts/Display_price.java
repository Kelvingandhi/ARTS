package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soham.seatBookingrecyclerView.AbstractItem;
import com.example.soham.seatBookingrecyclerView.BookedItem;
import com.example.soham.seatBookingrecyclerView.EdgeItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 100HM on 10-03-2017.
 */
public class Display_price extends AppCompatActivity {

    public static TextView bus_id, source, destination, toward_date, arrival_time_1, departure_time_1, return_date, arrival_time_2, departure_time_2, single_lady, passenger, seat_no, price;

    Button seat;


    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "amounts";
    public static String METHOD_NAME2 = "Booked_seat";

    public static String SOAP_ACTION2 = "http://tempuri.org/Booked_seat";

    public static String SOAP_ACTION = "http://tempuri.org/amounts";

    public static List<AbstractItem> items;
   // public static String METHOD_NAME3 = "dlt_resurved_seat";


   // public static String SOAP_ACTION3 = "http://tempuri.org/dlt_resurved_seat";
    public static ArrayList<Integer> seats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_price);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket Genrate");

        bus_id = (TextView) findViewById(R.id.bus_id1);
        source = (TextView) findViewById(R.id.source1);
        destination = (TextView) findViewById(R.id.destination1);
        toward_date = (TextView) findViewById(R.id.toward1);
        arrival_time_1 = (TextView) findViewById(R.id.arrival_11);
        departure_time_1 = (TextView) findViewById(R.id.departure_11);
        return_date = (TextView) findViewById(R.id.return_date1);
        arrival_time_2 = (TextView) findViewById(R.id.arrival_12);
        departure_time_2 = (TextView) findViewById(R.id.departure_12);
        single_lady = (TextView) findViewById(R.id.single_lady1);
        passenger = (TextView) findViewById(R.id.passenger1);
        //seat_no=(TextView)findViewById(R.id.seat_no);
        price = (TextView) findViewById(R.id.price1);
        seat = (Button) findViewById(R.id.seat_select);


        //ticket_id.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).ticket_id);
        bus_id.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).bus_id);
        source.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).source);
        destination.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).destination);
        toward_date.setText(Ticket_Genrate.fromDateEtxt.getText().toString());
        arrival_time_1.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).arrival_time);
        departure_time_1.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).despatch_time);
        return_date.setText(Ticket_Genrate.toDateEtxt.getText().toString());
        arrival_time_2.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).arrival_time1);
        departure_time_2.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).despatch_time1);


        if (Ticket_Genrate.slady.isChecked()) {
            single_lady.setText("single lady");
        } else {
            single_lady.setText(" - ");
        }

        passenger.setText(Ticket_Genrate.psgr.getSelectedItem().toString());

        new amounts().execute();

        //new dlt_seat().execute();



        seats.clear();

        seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  if(!return_date.getText().toString().equals(""))
                {

                }*/



                /*for (int i=1; i<=28; i++)
                {

                    if (seats.contains(i))
                    {
                        items.add(new BookedItem(String.valueOf(i)));

                    }

                    else{
                        items.add(new EdgeItem(String.valueOf(i)));
                    }
                }*/
                ///Toast.makeText(Display_price.this, "in prcess", 0).show();
                Intent i1 = new Intent(Display_price.this, com.example.soham.seatBookingrecyclerView.MainActivity.class);

                startActivity(i1);
                new Booked().execute();


            }
        });





        //}
        // seat_no.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).s);
        //price.setText(Fatch_BusList.Buse_list.get(Fatch_BusList.posit).price);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.help_icon:

                Toast.makeText(Display_price.this,"ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public class Booked extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Display_price.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                request.addProperty("bus_id", Fatch_BusList.Buse_list.get(Fatch_BusList.posit).bus_id.toString());
                request.addProperty("date", Ticket_Genrate.fromDateEtxt.getText().toString());
                request.addProperty("departure_time", Fatch_BusList.Buse_list.get(Fatch_BusList.posit).despatch_time.toString());
                request.addProperty("arrival_time",  Fatch_BusList.Buse_list.get(Fatch_BusList.posit).arrival_time.toString());


                //Toast.makeText(Display_price.this,bus_id+""+Ticket_Genrate.fromDateEtxt+""+departure_time_1+""+arrival_time_1,0).show();
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


            if (result.toString().equals("fail")) {


                Toast.makeText(Display_price.this, "no Booked seat", Toast.LENGTH_SHORT).show();
                //String[] arr=result.split(",");

            } else {

                String rows[] = result.split(",");
                for (int i = 0; i < rows.length; i++) {

                    seats.add(Integer.parseInt(rows[i]));

                    //seats.notify();


/*
                        if (seats.contains(i))
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
                        }
*/







                    //Toast.makeText(MainActivity.this,seats.get(i).toString(),Toast.LENGTH_SHORT).show();
                }


            }

            //System.out.println(abc.toString());

            // Toast.makeText(MainActivity.this,seats.get(0).toString(),Toast.LENGTH_SHORT).show();
            //Toast.makeText(Display_price.this, result, Toast.LENGTH_SHORT).show();

        }
    }

    private class amounts extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Display_price.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("bus_id", bus_id.getText().toString());
                //request.addProperty("name", Login.e11.getText().toString());
                request.addProperty("source", source.getText().toString());
                //source1=source.setSelection(3);

                //request.addProperty("source",source.setSelection(Between_station.source.get););
                request.addProperty("destination", destination.getText().toString());
                if (Ticket_Genrate.r1.isChecked()) {
                    request.addProperty("status", "local");
                    // s1="local";
                } else {
                    request.addProperty("status", "express");
                    //s1="express";
                }

                //request.addProperty("toward_date", fromDateEtxt.getText().toString());
                //request.addProperty("toward_time", View_between_station.toward_time.toString());
                //request.addProperty("return_date", toDateEtxt.getText().toString());


                if (single_lady.getText().toString().equals("single lady")) {
                    request.addProperty("single_lady", "1");
                    request.addProperty("passenger", "1");
                } else {
                    request.addProperty("single_lady", "1");
                    request.addProperty("passenger", passenger.getText().toString());
                }
                request.addProperty("trip", Ticket_Genrate.trip.toString());


                //Cursor c1 = db.rawQuery("select amount from tbl_ticket where source='"+source.getSelectedItem().toString()+"' and destination='"+desti.getSelectedItem().toString()+"' and status='"+s1+"'", null);


                //   String fatch_amonut = "select amount from tbl_ticket where source='"+source.getSelectedItem().toString()+"' and destination='"+desti.getSelectedItem().toString()+"' and status='"+s1+"'";


                //  request.addProperty("price", price.getText().toString());


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

            Toast.makeText(Display_price.this, result, Toast.LENGTH_LONG).show();

            price.setText(result);

        }


    }

    /*

    private class dlt_seat extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Display_price.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.drawable.ic_launcher);
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION3, envelope);

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

            Toast.makeText(Display_price.this, result, Toast.LENGTH_LONG).show();

            //price.setText(result);

        }

    }*/
}