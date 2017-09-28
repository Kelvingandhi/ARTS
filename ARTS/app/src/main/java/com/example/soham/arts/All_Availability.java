package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by 100HM on 16/04/2017.
 */
public class All_Availability extends AppCompatActivity {

    ListView l1;
    TextView bus_no;

    public static String NAMESPACE="http://tempuri.org/";
    public static String URL=Login.URL;

    public static String METHOD_NAME="Update_availability";
    public static String SOAP_ACTION="http://tempuri.org/Update_availability";


    static public ArrayList<Buses_Time> Buse_time = new ArrayList<Buses_Time>();
    class Buses_Time {


        String source;
        String destination;
        String despatch_time;
        String arrival_time;

        String new_dep_time;
        String new_arr_time;




        public Buses_Time( String source,
                     String destination, String despatch_time , String arrival_time,String new_dep_time,String new_arr_time) {
            super();


            this.source = source;
            this.destination = destination;
            this.arrival_time = arrival_time;
            this.despatch_time = despatch_time;
            this.new_dep_time=new_dep_time;
            this.new_arr_time=new_arr_time;

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            case R.id.help_icon:

                Toast.makeText(All_Availability.this,"Help ARTS",Toast.LENGTH_SHORT).show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_availability);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Live Bus Status");
        bus_no=(TextView)findViewById(R.id.bus_no);
        l1=(ListView)findViewById(R.id.listView2);


        bus_no.setText(com.example.soham.arts.Live_bus_status.bus.getSelectedItem().toString());



        new all_availability().execute();
    }



    private class all_availability extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(All_Availability.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("bus_id", com.example.soham.arts.Live_bus_status.bus.getSelectedItem().toString());
                //request.addProperty("current_city",backgroundservice.city.toString());


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

			/*
			String rows [] = result.split("`");          // rows split by ,

			//for(int i =0;i<rows.length;i++)
			//{
				//String col[]= rows[i].split("-");
				//Buses bus = new Buses(col[1],col[2],col[3],col[4],col[5],col[6]);
				//Buse.add(bus);
				listarray.add(rows[0]);         //add in array           represent the each rows
				//listarray.add(col[1]);
				//listarray.add(col[2]);
				//listarray.add(col[3]);
				//listarray.add(col[4]);
				//listarray.add(col[5]);
				//listarray.add(col[6]);
			//}*/


            Buse_time.clear();
            String rows[] = result.split("`");          // rows split by ,


            for (int i = 0; i < rows.length; i++) {
                String col[] = rows[i].split("~");
                Buses_Time bus = new Buses_Time(col[3], col[4], col[5], col[6], col[9], col[10]);
                Buse_time.add(bus);
            }

            CustomAdapter_Avail_bus adapter1 = new CustomAdapter_Avail_bus(All_Availability.this, Buse_time);
            l1.setAdapter(adapter1);
            //l1.setAdapter( new ArrayAdapter<String>(View_live_bus.this, android.R.layout.simple_list_item_1, listarray));


        }
    }
}
