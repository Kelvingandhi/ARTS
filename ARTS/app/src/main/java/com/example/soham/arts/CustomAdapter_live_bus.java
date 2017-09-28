package com.example.soham.arts;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by 100HM on 04-02-2017.
 */
public class CustomAdapter_live_bus extends BaseAdapter {

    static String bus_id,time;

   /* public static String NAMESPACE="http://tempuri.org/";
    public static String URL=Login.URL;

    public static String METHOD_NAME="calc_time_delay";
    public static String SOAP_ACTION="http://tempuri.org/calc_time_delay";*/
    Context context;
    ArrayList<com.example.soham.arts.View_live_bus.Buses> rowitem;
    //String delay;

    LayoutInflater inflter;
    public CustomAdapter_live_bus(Context context, ArrayList<com.example.soham.arts.View_live_bus.Buses> rowitem) {

        this.context = context;
        this.rowitem = rowitem;
        //this.delay=delay;
    }




    @Override
    public int getCount() {
        return rowitem.size();
    }

    @Override
    public Object getItem(int position) {
        return rowitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View onelistitem, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);    // inflater = design using coding

        onelistitem = mInflater.inflate(R.layout.custom_buslist, null);

        ViewHolder v1 = new ViewHolder();


        v1.t1 =(TextView) onelistitem.findViewById(R.id.textView1);
        v1.t2 =(TextView) onelistitem.findViewById(R.id.textView2);
        v1.t31 =(TextView) onelistitem.findViewById(R.id.textView31);
        v1.t32 =(TextView) onelistitem.findViewById(R.id.textView32);
        v1.t4 =(TextView) onelistitem.findViewById(R.id.textView4);
        v1.t5 =(TextView) onelistitem.findViewById(R.id.textView5);

        v1.t6 =(TextView) onelistitem.findViewById(R.id.time_delay);



        //toward_time=v1.t4.getText().toString();

        v1.t1.setText(rowitem.get(position).bus_id);
        //bus_id=v1.t1.getText().toString();
        v1.t2.setText(rowitem.get(position).status);
        v1.t31.setText(rowitem.get(position).source);
        v1.t32.setText(rowitem.get(position).destination);

        v1.t4.setText(rowitem.get(position).despatch_time);
        v1.t5.setText(rowitem.get(position).arrival_time);
        //v1.t6.setText(delay);


        //toward_time=v1.t4.getText().toString();

        //onelistitem.setTag(holder);

        //new delay_time().execute();

        v1.t6.setText(Live_bus_status.times.toString());

        return onelistitem;

    }

    private static class ViewHolder {
        static TextView t1,t2,t31,t32,t4,t5,t6;
    }

   /* private class delay_time extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("station",Live_bus_status.city.toString());
                request.addProperty("bus_id",bus_id.toString());
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

			time = result;


        }


    }*/

}

