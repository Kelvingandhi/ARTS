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
 * Created by 100HM on 16/04/2017.
 */
public class CustomAdapter_Avail_bus extends BaseAdapter {
    Context context;
    //upcoming_booking upcoming_booking;
    ArrayList<All_Availability.Buses_Time> rowitem;
    LayoutInflater inflter;
    public CustomAdapter_Avail_bus(Context context, ArrayList<All_Availability.Buses_Time> rowitem) {

        this.context = context;
        this.rowitem = rowitem;

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



        onelistitem = mInflater.inflate(R.layout.custom_avail_bus, null);

        ViewHolder v1 = new ViewHolder();

        //v1.t1 =(TextView) onelistitem.findViewById(R.id.textView23);

        v1.t2 =(TextView) onelistitem.findViewById(R.id.sourr);
        v1.t3 =(TextView) onelistitem.findViewById(R.id.dest);
        //v1.t3 =(TextView) onelistitem.findViewById(R.id.textView3);
        v1.t4 =(TextView) onelistitem.findViewById(R.id.dep_tm);
        //v1.t4 =(TextView) onelistitem.findViewById(R.id.textView12);
        v1.t5 =(TextView) onelistitem.findViewById(R.id.arri_tm);
        v1.t6 =(TextView) onelistitem.findViewById(R.id.new_dep_tm);
        v1.t7 =(TextView) onelistitem.findViewById(R.id.new_arr_tm);




        //toward_time=v1.t4.getText().toString();

        //v1.t0.setText(rowitem.get(position).ticket_id);

        v1.t2.setText(rowitem.get(position).source);
        v1.t3.setText(rowitem.get(position).destination);

        v1.t4.setText(rowitem.get(position).despatch_time);
        v1.t5.setText(rowitem.get(position).arrival_time);
        v1.t6.setText(rowitem.get(position).new_dep_time );

        v1.t7.setText(rowitem.get(position).new_arr_time);



        //toward_time=v1.t4.getText().toString();
        //onelistitem.setTag(holder);

        //ticket_id=rowitem.get(position).ticket_id;

        return onelistitem;

    }

    private static class ViewHolder {
        static TextView t0,t1,t2,t3,t4,t5,t6,t7;
    }

}
