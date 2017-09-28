package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by 100HM on 10/04/2017.
 */
public class Live_station_status extends AppCompatActivity{
    static Spinner station;
    Button search;
    ListView l1;
    TextView unavailable;

    public static String NAMESPACE="http://tempuri.org/";
    public static String URL=Login.URL;

    public static String METHOD_NAME="live_stations";
    public static String SOAP_ACTION="http://tempuri.org/live_stations";

    ArrayList<String> list= new ArrayList<String>();

    static public ArrayList<Buses> Buse = new ArrayList<Buses>();
    class Buses {
        String bus_id;
        String status;
        String source;
        String destination;
        String arrival_time;
        String despatch_time;
        String bus_route;



        public Buses(String bus_id, String status, String source,
                     String destination,String despatch_time , String arrival_time, String bus_route) {
            super();
            this.bus_id = bus_id;
            this.status = status;
            this.source = source;
            this.destination = destination;
            this.despatch_time = despatch_time;
            this.arrival_time = arrival_time;

            this.bus_route=bus_route;

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_station_status);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Live Station Status");

        station = (Spinner) findViewById(R.id.spinner2);
        search = (Button) findViewById(R.id.search);
        unavailable = (TextView)findViewById(R.id.unavailbl);
        l1 = (ListView) findViewById(R.id.list11);


        list.add("surat");
        list.add("udhna");
        list.add("bhestan");
        list.add("maroli");
        list.add("navsari");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Live_station_status.this, android.R.layout.simple_spinner_dropdown_item, list);
        station.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                new live_station().execute();
                //Intent i1=new Intent(Live_station_status.this,View_live_bus.class);
                //startActivity(i1);
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

                Toast.makeText(Live_station_status.this,"Help ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class live_station extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Live_station_status.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("station",station.getSelectedItem().toString());
                //request.addProperty("current_city",backgroundservice.city.toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

                Log.d("message","MEssage : "+resultstr.toString());
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
            dialog.dismiss();

            if(!result.equals("fail")) {

                Buse.clear();
                String rows[] = result.split("`");          // rows split by ,


                for (int i = 0; i < rows.length; i++) {
                    String col[] = rows[i].split("~");
                    Buses bus = new Buses(col[1], col[2], col[3], col[4], col[5], col[6], col[7]);
                    Buse.add(bus);
                }

                CustomAdapter_live_station adapter = new CustomAdapter_live_station(Live_station_status.this, Buse);
                l1.setAdapter(adapter);
                //l1.setAdapter( new ArrayAdapter<String>(View_live_bus.this, android.R.layout.simple_list_item_1, listarray));

            }
            else
            {
                l1.setVisibility(View.GONE);
                unavailable.setVisibility(View.VISIBLE);
            }


            /*
            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int postion, long arg3) {
                    // TODO Auto-generated method stub
                    Toast.makeText(View_live_bus.this,Buse.get(postion).bus_id+" "+Buse.get(postion).status+" "+Buse.get(postion).source+" "+Buse.get(postion).destination+" "+Buse.get(postion).despatch_time+" "+Buse.get(postion).arrival_time+" "+Buse.get(postion).bus_route,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(future_event.this, event.get(postion).title,1).show();

                    //	next_fevent.title=event.get(postion).title;

                    //	next_fevent.detail=event.get(postion).detail;
                    //	next_fevent.date=event.get(postion).date;
                    //next_fevent.time=event.get(postion).time;
                    //next_fevent.posted_by=event.get(postion).posted_by;
                    //next_fevent.status=event.get(postion).status;
                    //	next_fevent.files=event.get(postion).files;
                    //				Intent i=new Intent(getApplicationContext(), next_fevent.class);
                    //			startActivity(i);

                    //--------------------------------------



                    AlertDialog.Builder builder = new AlertDialog.Builder(View_live_bus.this);

                    LayoutInflater inflater = getLayoutInflater();


                    View dialogView = inflater.inflate(R.layout.next_fevent,null);


                    EditText e1 = (EditText)dialogView.findViewById(R.id.editText111);
                    EditText e2 = (EditText)dialogView.findViewById(R.id.editText2);
                    EditText e3 = (EditText)dialogView.findViewById(R.id.editText3);
                    EditText e4 = (EditText)dialogView.findViewById(R.id.editText4);
                    EditText e5 = (EditText)dialogView.findViewById(R.id.editText5);
                    EditText e6 = (EditText)dialogView.findViewById(R.id.editText6);
                    EditText e7 = (EditText)dialogView.findViewById(R.id.editText7);
                    EditText e8 = (EditText)dialogView.findViewById(R.id.editText8);
                    e1.setText("title:"+Buse.get(postion).bus_id);
                    e2.setText("status:"+Buse.get(postion).status);
                    e3.setText("source:"+Buse.get(postion).source);
                    e4.setText("destination:"+Buse.get(postion).destination);
                    e5.setText("desp_time:"+Buse.get(postion).despatch_time);
                    e6.setText("arri_time:"+Buse.get(postion).arrival_time);
                    e7.setText("bus_route:"+Buse.get(postion).bus_route);
                    e8.setText("current_city:"+Live_bus_status.city.toString());

                    builder.setView(dialogView);



                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }

            });

            */

        }


    }
}
