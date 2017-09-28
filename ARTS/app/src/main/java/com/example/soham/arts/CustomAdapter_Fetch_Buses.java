package com.example.soham.arts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 100HM on 07-03-2017.
 */
public class CustomAdapter_Fetch_Buses extends BaseAdapter {
    Context context;
    ArrayList<Fatch_BusList.BusesList> rowitem;
    public CustomAdapter_Fetch_Buses(Context context, ArrayList<Fatch_BusList.BusesList> rowitem) {

        this.context=context;
        this.rowitem=rowitem;
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

        onelistitem = mInflater.inflate(R.layout.custom_fetch_bus, null);

        ViewHolder v1 = new ViewHolder();


        //v1.t0 =(TextView) onelistitem.findViewById(R.id.);

        v1.t1 =(TextView) onelistitem.findViewById(R.id.bus_id1);
        v1.t2 =(TextView) onelistitem.findViewById(R.id.source1);
        //v1.t3 =(TextView) onelistitem.findViewById(R.id.textView3);
        v1.t3 =(TextView) onelistitem.findViewById(R.id.destination1);
        //v1.t4 =(TextView) onelistitem.findViewById(R.id.textView12);
        v1.t5 =(TextView) onelistitem.findViewById(R.id.departure_time11);
        v1.t6 =(TextView) onelistitem.findViewById(R.id.arrival_time11);
        //v1.t7 =(TextView) onelistitem.findViewById(R.id.textView15);
        v1.t8 =(TextView) onelistitem.findViewById(R.id.departure_time22);
        v1.t9 =(TextView) onelistitem.findViewById(R.id.arrival_time22);
       // v1.t10 =(TextView) onelistitem.findViewById(R.id.textView20);
         v1.t11 =(TextView) onelistitem.findViewById(R.id.toward_date1);
        v1.t12 =(TextView) onelistitem.findViewById(R.id.return_date11);




        //v1.t0.setText(rowitem.get(position).ticket_id);
        v1.t1.setText(rowitem.get(position).bus_id);
        v1.t2.setText(rowitem.get(position).source);
        v1.t3.setText(rowitem.get(position).destination);
        v1.t11.setText(Ticket_Genrate.fromDateEtxt.getText().toString());
        v1.t5.setText(rowitem.get(position).despatch_time);
        v1.t6.setText(rowitem.get(position).arrival_time);
        v1.t12.setText(Ticket_Genrate.toDateEtxt.getText().toString());
        v1.t8.setText(rowitem.get(position).despatch_time1);
        v1.t9.setText(rowitem.get(position).arrival_time1);
       //v1.t10.setText(rowitem.get(position).price);


        return onelistitem;

    }

    private static class ViewHolder {
        static TextView t0,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    }

}
