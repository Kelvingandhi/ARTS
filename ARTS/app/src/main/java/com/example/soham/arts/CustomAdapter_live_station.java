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
 * Created by 100HM on 10/04/2017.
 */

public class CustomAdapter_live_station extends BaseAdapter {

    static String toward_time;

    Context context;
    ArrayList<Live_station_status.Buses> rowitem;
    LayoutInflater inflter;
    public CustomAdapter_live_station(Context context, ArrayList<Live_station_status.Buses> rowitem) {

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

        onelistitem = mInflater.inflate(R.layout.custom_buslist, null);

        ViewHolder v1 = new ViewHolder();


        v1.t1 =(TextView) onelistitem.findViewById(R.id.textView1);
        v1.t2 =(TextView) onelistitem.findViewById(R.id.textView2);
        v1.t31 =(TextView) onelistitem.findViewById(R.id.textView31);
        v1.t32 =(TextView) onelistitem.findViewById(R.id.textView32);
        v1.t4 =(TextView) onelistitem.findViewById(R.id.textView4);
        v1.t5 =(TextView) onelistitem.findViewById(R.id.textView5);
        v1.t6 =(TextView) onelistitem.findViewById(R.id.textView10);
        v1.t7 =(TextView) onelistitem.findViewById(R.id.time_delay);



        //toward_time=v1.t4.getText().toString();

        v1.t1.setText(rowitem.get(position).bus_id);
        v1.t2.setText(rowitem.get(position).status);
        v1.t31.setText(rowitem.get(position).source);
        v1.t32.setText(rowitem.get(position).destination);

        v1.t4.setText(rowitem.get(position).despatch_time);
        v1.t5.setText(rowitem.get(position).arrival_time);
        v1.t6.setVisibility(View.GONE);
        v1.t7.setVisibility(View.GONE);
        //v1.t6.setText(rowitem.get(position).status);


        //toward_time=v1.t4.getText().toString();
        //onelistitem.setTag(holder);


        return onelistitem;

    }

    private static class ViewHolder {
        static TextView t1,t2,t31,t32,t4,t5,t6,t7;
    }
}


