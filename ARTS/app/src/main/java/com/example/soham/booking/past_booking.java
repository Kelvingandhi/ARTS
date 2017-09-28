package com.example.soham.booking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.soham.fragment.HomeFragment;
import com.example.soham.arts.Login;
import com.example.soham.arts.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by 100HM on 22-02-2017.
 */
public class past_booking extends Fragment {
    ListView pastlist;
    TextView unavailable;

    //EditText t1;

    public static String NAMESPACE="http://tempuri.org/";
    public static String URL= Login.URL;

    public static String METHOD_NAME="pasttickets";
    public static String SOAP_ACTION="http://tempuri.org/pasttickets";

    private SwipeRefreshLayout swipeRefreshLayout;
    public static ArrayList<Ticket_Lists> tkt_Lists = new ArrayList<Ticket_Lists>();
    ArrayList<String> listarray = new ArrayList<String>();

    public static class Ticket_Lists {

        public String ticket_id;
        public String bus_id;
        public String user_name,booking_date, source, destination, journey_date, return_date, journey_departure_time, journey_arrivial_time, return_departure_time, return_arrivial_time, bus_type, no_of_passenger, single_lady, seat_no, amount,user_email;


        public Ticket_Lists(String ticket_id, String bus_id, String user_name,String booking_date, String source,
                            String destination, String journey_date, String return_date, String journey_departure_time, String journey_arrivial_time,
                            String return_departure_time, String return_arrivial_time, String bus_type, String no_of_passenger, String single_lady, String seat_no, String amount,String user_email) {
            super();
            this.ticket_id = ticket_id;
            this.bus_id = bus_id;
            this.user_name = user_name;

            this.booking_date=booking_date;
            this.source = source;
            this.destination = destination;

            this.journey_date = journey_date;

            this.return_date = return_date;
            this.journey_departure_time = journey_departure_time;
            this.journey_arrivial_time = journey_arrivial_time;
            this.return_departure_time = return_departure_time;
            this.return_arrivial_time = return_arrivial_time;
            this.bus_type = bus_type;
            this.no_of_passenger = no_of_passenger;
            this.single_lady = single_lady;
            this.seat_no = seat_no;
            this.amount = amount;


            this.user_email = user_email;
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        // TODO Auto-generated method stub


        View v= inflater.inflate(R.layout.past_booking, container, false);


        pastlist=(ListView)v.findViewById(R.id.past_list);
        unavailable = (TextView) v.findViewById(R.id.unavailable);


        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //    new Handler().postDelayed(new Runnable() {               // handler do handle refresh time
                //       @Override
                //       public void run() {
                new bus_search().execute();
                swipeRefreshLayout.setRefreshing(false);
                //        }
                //   },1000);                                                  // refresh time is 1000

            }
        });
        new bus_search().execute();
        return v;
    }



    public class bus_search extends AsyncTask<Void, Void, String> {

        // ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //dialog = new ProgressDialog(upcoming_booking.this);
            //dialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SharedPreferences applicationpreferences = getActivity().getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);

                String email = applicationpreferences.getString("user_email","");

                //request.addProperty("user_email", HomeFragment.email.toString());

                request.addProperty("user_email",email);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
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
            // dialog.dismiss();

            if(!result.equals("fail")) {

                String rows[] = result.split("`");          // rows split by ,

                tkt_Lists.clear();
                //listarray.clear();
                for (int i = 0; i < rows.length; i++) {
                    String col[] = rows[i].split("~");
                    Ticket_Lists list = new Ticket_Lists(col[0], col[1], col[2], col[3], col[4], col[5], col[6], col[7], col[8], col[9], col[10], col[11], col[12], col[13], col[14], col[15], col[16], col[17]);
                    tkt_Lists.add(list);

                }
                /*listarray.clear();
                for (int i = 0; i < rows.length; i++) {
                    //String col[]= rows[i].split("-");
                    //Buses bus = new Buses(col[1],col[2],col[3],col[4],col[5],col[6]);
                    //Buse.add(bus);
                    listarray.add(rows[i]);         //add in array           represent the each rows
                    //listarray.add(col[1]);
                    //listarray.add(col[2]);
                    //listarray.add(col[3]);
                    //listarray.add(col[4]);
                    //listarray.add(col[5]);
                    //listarray.add(col[6]);
                }*/
                // upcominglist.setAdapter(new ArrayAdapter<String>(upcoming_booking.this,andr));
              //  pastlist.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, listarray));
                CustomAdapter_past_ticket adapter = new CustomAdapter_past_ticket(getActivity(), tkt_Lists);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, listarray);
                pastlist.setAdapter(adapter);

            }
            else
            {
                pastlist.setVisibility(View.GONE);
                unavailable.setVisibility(View.VISIBLE);
            }
            //upcominglist.setAdapter(new ArrayAdapter<String>(upcoming_booking.this, android.R.layout.simple_list_item_1, listarray));
        }


    }

}
