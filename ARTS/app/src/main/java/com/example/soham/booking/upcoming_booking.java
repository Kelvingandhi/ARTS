package com.example.soham.booking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soham.arts.Display_ticket;
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
public class upcoming_booking extends Fragment {

    static String ticket_id, bus_id, source, destination, toward_date, arrival_time_1, departure_time_1, return_date, arrival_time_2, departure_time_2, passenger, price;
    ListView upcominglist;
    TextView unavailable;
    public static String single_lady;

    //EditText t1;

    public static String tkt_id ;

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "upcoming";
    public static String SOAP_ACTION = "http://tempuri.org/upcoming";

    public static String METHOD_NAME2 = "cancel_booking";
    public static String SOAP_ACTION2= "http://tempuri.org/cancel_booking";

    //ArrayList<String>  listarray = new ArrayList<String>();

    private SwipeRefreshLayout swipeRefreshLayout;

    public static ArrayList<Ticket_Lists> tkt_Lists = new ArrayList<Ticket_Lists>();

    public static int posi;
    String b_id;
    public static String booking_date;

    //ArrayList<String>  listarray = new ArrayList<String>();


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


    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_genrt);

        t1=(TextView)findViewById(R.id.text1);
        upcominglist=(ListView)findViewById(R.id.upcoming_list);

        Date= DateFormat.getDateTimeInstance().format("dd-MM-yyyy");                              //  display the current date
        t1.setText(Date);

    }

    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        // TODO Auto-generated method stub

       /*

        setContentView(R.layout.ticket_genrt);

        t1=(TextView)findViewById(R.id.text1);
        upcominglist=(ListView)findViewById(R.id.upcoming_list);

       // String Date= DateFormat.getDateTimeInstance().format(new Date());                              //  display the current date
        //t1.setText(Date);


        */

        View v = inflater.inflate(R.layout.upcoming_booking, container, false);

        //t1=(EditText)v.findViewById(R.id.text1);
        upcominglist = (ListView) v.findViewById(R.id.upcoming_list);

        unavailable = (TextView) v.findViewById(R.id.unavailable);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

        registerForContextMenu(upcominglist);
       /* Calendar c= Calendar.getInstance();
                    //System.out.println("current date = "+c.getTime());
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy");
        String Date= dateFormat.format(new Date());*/

        //String Date= DateFormat.getDateTimeInstance().format(new Date());                              //  display the current date
        //t1.setText(Date);

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
            try {


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


              /*  Calendar c= Calendar.getInstance();

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy");
                String Date= dateFormat.format(new Date());
                request.addProperty("current_date", Date.toString());

                */

                SharedPreferences applicationpreferences = getActivity().getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);

                String email = applicationpreferences.getString("user_email","");

                request.addProperty("email", email);

                //request.addProperty("email", HomeFragment.email.toString());

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
            // dialog.dismiss();

            if(!result.equals("fail"))
            {

                String rows[] = result.split("`");          // rows split by ,
                // int l = rows.length;
                // Toast.makeText(getActivity(),str,0).show();
                tkt_Lists.clear();
                //listarray.clear();
                for (int i = 0; i < rows.length; i++) {

                    // str=rows[i].toString();
                    String col[] = rows[i].split("~");
                    Ticket_Lists list = new Ticket_Lists(col[0], col[1], col[2],col[3], col[4], col[5], col[6], col[7], col[8], col[9], col[10], col[11], col[12], col[13], col[14], col[15], col[16], col[17]);
                    tkt_Lists.add(list);

                    // listarray.add(rows[i]);         //add in array           represent the each rows
                    // listarray.add(col[1]);
            /*listarray.add(col[2]);
            listarray.add(col[3]);
            listarray.add(col[4]);
            listarray.add(col[5]);
            listarray.add(col[6]);
                listarray.add(col[7]);
                listarray.add(col[8]);
                listarray.add(col[9]);
                listarray.add(col[10]);
                */
                }
                // upcominglist.setAdapter(new ArrayAdapter<String>(upcoming_booking.this,andr));


                CustomAdapter_upcoming_ticket adapter = new CustomAdapter_upcoming_ticket(getActivity(), tkt_Lists);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, listarray);
                upcominglist.setAdapter(adapter);
            }

            else
            {
                upcominglist.setVisibility(View.GONE);
                unavailable.setVisibility(View.VISIBLE);
            }

           /* upcominglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //ticket_id=tkt_Lists.get(info.position).ticket_id;

                    posi = position;


                    //Toast.makeText(getActivity(),tkt_Lists.get(position).toString(),0).show();

                    Intent i1=new Intent(getActivity(),Display_ticket.class);
                    startActivity(i1);



                }
            });
            */

            //upcominglist.setAdapter(new ArrayAdapter<String>(upcoming_booking.this, android.R.layout.simple_list_item_1, listarray));
        }


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);   //.....[2]....create the context menu

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.add("View Ticket");
        menu.add("Cancel Ticket");

        posi = info.position;

        //b_id = tkt_Lists.get(info.position).bus_id;
        tkt_id=tkt_Lists.get(info.position).ticket_id;
        booking_date=tkt_Lists.get(info.position).booking_date;
        //menu.setHeaderTitle("manage");
        //menu.setHeaderTitle("BUS = " + Buse.get(info.position).bus_id);  //...set header title name as selected item name
        menu.setHeaderIcon(android.R.drawable.ic_menu_info_details);
        //menu.setHeaderIcon(R.drawable.service_01);


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {    //.....[3]....context item select ..like "Edit" or "delete"
        // TODO Auto-generated method stub

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();  // make a final
        if (item.getTitle().equals("View Ticket")) {


            Intent i1 = new Intent(getActivity(), Display_ticket.class);
            startActivity(i1);
            //Toast.makeText(this,"fare inquary is in process....",0).show();
        } else {
            // Intent i1= new Intent(View_between_station.this, MapsActivity.class);
            //startActivity(i1);
            new cancel_tkt().execute();

            //Toast.makeText(getActivity(),"Cancelling is in process....",0).show();
        }


        return super.onContextItemSelected(item);

    }


    public class cancel_tkt extends AsyncTask<Void, Void, String> {


         ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);


              /*  Calendar c= Calendar.getInstance();

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy");
                String Date= dateFormat.format(new Date());
                request.addProperty("current_date", Date.toString());

                */
                request.addProperty("ticket_id", tkt_id.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION2, envelope);

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

            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            /*
            String rows[] = result.split("`");          // rows split by ,
            // int l = rows.length;
            // Toast.makeText(getActivity(),str,0).show();
            tkt_Lists.clear();
            //listarray.clear();
            for (int i = 0; i < rows.length; i++) {

                // str=rows[i].toString();
                String col[] = rows[i].split("~");
                Ticket_Lists list = new Ticket_Lists(col[0], col[1], col[2], col[4], col[5], col[6], col[7], col[8], col[9], col[10], col[11], col[12], col[13], col[14], col[15], col[16],col[17]);
                tkt_Lists.add(list);
                // listarray.add(rows[i]);         //add in array           represent the each rows
                // listarray.add(col[1]);


            }*/
        }


  /* public void setText(String Date) {
       // TextView t1 = (TextView) getView().findViewById(R.id.text1);  //UPDATE

                                      //  display the current date

        t1.setText(Date);
    } */

    }
}
