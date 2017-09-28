package com.example.soham.booking;

import android.app.ProgressDialog;
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
public class cancel_bookng extends Fragment {
    ListView cancellist;

    TextView unavailable;
    //EditText t1;

    static String str = "";

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME = "cancel_booking_display";
    public static String SOAP_ACTION = "http://tempuri.org/cancel_booking_display";

    //ArrayList<String>  listarray = new ArrayList<String>();


    public static ArrayList<Cancel_Ticket_Lists> tkt_Lists = new ArrayList<Cancel_Ticket_Lists>();

    public static int posi;
    String b_id;
    private SwipeRefreshLayout swipeRefreshLayout;

    //ArrayList<String>  listarray = new ArrayList<String>();


    public static class Cancel_Ticket_Lists {

        public String ticket_id;
        public String bus_id;
        public String del_ticket_id,cancelled_date,u_name,booking_date, source, destination, journey_date, journey_departure_time, journey_arrivial_time, bus_type, no_of_pass, single_lady, seat_no, amount, user_email;


        public Cancel_Ticket_Lists(String del_ticket_id,String ticket_id, String bus_id,String cancelled_date, String source, String destination,
                                   String journey_date,  String journey_depart_time, String journey_arri_time,
                                   String seat_no, String amount) {
            super();
            this.del_ticket_id=del_ticket_id;

            this.ticket_id = ticket_id;
            this.bus_id = bus_id;
            this.cancelled_date=cancelled_date;
            //this.u_name = u_name;

            //this.booking_date=booking_date;
            this.source = source;
            this.destination = destination;

            this.journey_date = journey_date;

            //this.return_date = return_date;
            this.journey_departure_time = journey_depart_time;
            this.journey_arrivial_time = journey_arri_time;
           // this.return_departure_time = return_departure_time;
            //this.return_arrivial_time = return_arrivial_time;
            //this.bus_type = bus_type;
            //this.no_of_pass = no_of_pass;
            //this.single_lady = single_lady;
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



        View v = inflater.inflate(R.layout.canceled_booking, container, false);

        //t1=(EditText)v.findViewById(R.id.text1);
        cancellist = (ListView) v.findViewById(R.id.cancel_list);


        unavailable = (TextView) v.findViewById(R.id.unavailable);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //    new Handler().postDelayed(new Runnable() {               // handler do handle refresh time
                //       @Override
                //       public void run() {
                new cancelbus_search().execute();
                swipeRefreshLayout.setRefreshing(false);
                //        }
                //   },1000);                                                  // refresh time is 1000

            }
        });
        new cancelbus_search().execute();
        return v;
    }


    public class cancelbus_search extends AsyncTask<Void, Void, String> {

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


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


              /*  Calendar c= Calendar.getInstance();

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy");
                String Date= dateFormat.format(new Date());
                request.addProperty("current_date", Date.toString());

                */
                //request.addProperty("ticket_id", upcoming_booking.tkt_id.toString());
                SharedPreferences applicationpreferences = getActivity().getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);

                String email = applicationpreferences.getString("user_email","");
                //request.addProperty("email", HomeFragment.email.toString());
                request.addProperty("email",email);

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


            if(!result.equals("fail")) {
                String rows[] = result.split("`");          // rows split by ,
                // int l = rows.length;
                // Toast.makeText(getActivity(),str,0).show();
                tkt_Lists.clear();
                //listarray.clear();
                for (int i = 0; i < rows.length; i++) {

                    // str=rows[i].toString();
                    String col[] = rows[i].split("~");
                    Cancel_Ticket_Lists list = new Cancel_Ticket_Lists(col[0], col[2], col[3], col[1], col[4], col[5], col[6], col[7], col[8], col[9], col[10]);
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


                CustomAdapter_cancel_ticket adapter = new CustomAdapter_cancel_ticket(getActivity(), tkt_Lists);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, listarray);
                cancellist.setAdapter(adapter);

            }
            else
            {
                cancellist.setVisibility(View.GONE);
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
}


