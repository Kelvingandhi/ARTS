package com.example.soham.arts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kelvin on 4/2/2017.
 */
public class Customview_cancelled_bus_action extends BaseAdapter{

    private final Context context;
    ArrayList<Cancelled_bus_action.cancelled_buses> value1;

    public Customview_cancelled_bus_action(Context context, ArrayList<Cancelled_bus_action.cancelled_buses> value1) {

        this.context=context;
        this.value1=value1;
    }

    private class ViewHolder
    {
        ImageView i1,i2,i3;
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.customview_cancel_bus_action, parent, false);

        ViewHolder v1 = new ViewHolder();

        v1.i1 = (ImageView) rowView.findViewById(R.id.bus_search_icon);

        //v1.i2 = (ImageView) rowView.findViewById(R.id.arrow_icon);

        //v1.i3 = (ImageView) rowView.findViewById(R.id.arrow_icon1);

        v1.t1 = (TextView) rowView.findViewById(R.id.bus_no_cbaview);

        //v1.t2 = (TextView) rowView.findViewById(R.id.bus_name_cbaview);

        v1.t3 = (TextView) rowView.findViewById(R.id.source_name_cbaview);

        v1.t4 = (TextView) rowView.findViewById(R.id.desti_name_cbaview);

        v1.t5 = (TextView) rowView.findViewById(R.id.status);

        v1.t6 = (TextView) rowView.findViewById(R.id.arri_time1_cbaview);

        v1.t7 = (TextView) rowView.findViewById(R.id.dep_time_cbaview);

        v1.t8 = (TextView) rowView.findViewById(R.id.starting_cbaview);

        v1.t9 = (TextView) rowView.findViewById(R.id.cancel_date_cbaview);




        v1.t1.setText(value1.get(position).bus_id);

        //String t = (value1.get(position).source)+" to "+(value1.get(position).destination);

        //v1.t2.setText(t);

        v1.t3.setText(value1.get(position).source);

        v1.t4.setText(value1.get(position).destination);

        v1.t5.setText(value1.get(position).avail);

        v1.t6.setText(value1.get(position).arrivial_time);

        v1.t7.setText(value1.get(position).departure_time);

        v1.i1.setImageResource(R.drawable.mipmap);

        //v1.i2.setImageResource(R.drawable.arrow_down);

        //v1.i3.setImageResource(R.drawable.arrow_down);

        v1.t9.setText(value1.get(position).cancelling_date);


        // rowView.setTag(v1);

        return rowView;
    }


    @Override
    public int getCount() {
        return value1.size();
    }

    @Override
    public Object getItem(int position) {
        return value1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



}

