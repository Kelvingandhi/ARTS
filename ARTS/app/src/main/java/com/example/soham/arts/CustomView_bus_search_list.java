package com.example.soham.arts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kelvin on 3/15/2017.
 */
public class CustomView_bus_search_list extends BaseAdapter {

    private final Context context;
    ArrayList<Fare_inquiry_bus_search.fare_bus_search> rowitem;

    public CustomView_bus_search_list(Context context, ArrayList<Fare_inquiry_bus_search.fare_bus_search> rowitem) {

        this.context=context;
        this.rowitem=rowitem;
    }

    private class ViewHolder
    {
        ImageView i1,i2,i3;
        TextView t1,t2,t3,t4,t5,t6,t7,t31,t32;
        LinearLayout lay1;

    }

    @Override
    public View getView(int position, View onelistitem, ViewGroup parent) {

       /*View rowView = convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.custom_buslist, parent, false);

        ViewHolder v1 = new ViewHolder();*/

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);    // inflater = design using coding

        onelistitem = mInflater.inflate(R.layout.custom_buslist, null);

        ViewHolder v1 = new ViewHolder();

        /*v1.i1 = (ImageView) rowView.findViewById(R.id.bus_search_icon);

        v1.i2 = (ImageView) rowView.findViewById(R.id.arrow_icon);

        v1.i3 = (ImageView) rowView.findViewById(R.id.arrow_icon1);

        v1.t1 = (TextView) rowView.findViewById(R.id.bus_no_bbsview);

        v1.t2 = (TextView) rowView.findViewById(R.id.bus_name_bbsview);

        v1.t3 = (TextView) rowView.findViewById(R.id.source_name_bbsview);

        v1.t4 = (TextView) rowView.findViewById(R.id.desti_name_bbsview);

        v1.t5 = (TextView) rowView.findViewById(R.id.days_bbsview);

        v1.t6 = (TextView) rowView.findViewById(R.id.arri_time1_bbsview);

        v1.t7 = (TextView) rowView.findViewById(R.id.arri_time2_bbsview);*/


        v1.lay1 =(LinearLayout) onelistitem.findViewById(R.id.layout1);
        v1.t1 =(TextView) onelistitem.findViewById(R.id.textView1);
        v1.t2 =(TextView) onelistitem.findViewById(R.id.textView2);
        //v1.t3 =(TextView) onelistitem.findViewById(R.id.textView3);
        v1.t31 =(TextView) onelistitem.findViewById(R.id.textView31);
        v1.t32 =(TextView) onelistitem.findViewById(R.id.textView32);
        v1.t4 =(TextView) onelistitem.findViewById(R.id.textView4);
        v1.t5 =(TextView) onelistitem.findViewById(R.id.textView5);
        v1.t6 =(TextView) onelistitem.findViewById(R.id.textView10);
        v1.t7 =(TextView) onelistitem.findViewById(R.id.time_delay);





        /*

        v1.t1.setText(value1.get(position).bus_id);

        String t = (value1.get(position).source)+" to "+(value1.get(position).destination);

        v1.t2.setText(t);

        v1.t3.setText(value1.get(position).source);

        v1.t4.setText(value1.get(position).destination);

        v1.t5.setText(value1.get(position).avail);

        v1.t6.setText(value1.get(position).arrivial_time);

        v1.t7.setText(value1.get(position).departure_time);

        v1.i1.setImageResource(R.drawable.mipmap);

        v1.i2.setImageResource(R.drawable.arrow_down);

        v1.i3.setImageResource(R.drawable.arrow_down);

        // rowView.setTag(v1);

        return rowView;*/

        v1.t1.setText(rowitem.get(position).bus_id);
        v1.t2.setText(rowitem.get(position).avail);
        v1.t31.setText(rowitem.get(position).source);
        v1.t32.setText(rowitem.get(position).destination);
        v1.t4.setText(rowitem.get(position).departure_time);
        v1.t5.setText(rowitem.get(position).arrivial_time);

        v1.t6.setVisibility(View.GONE);
        v1.t7.setVisibility(View.GONE);
        v1.lay1.setPadding(0,-40,0,15);

        return onelistitem;
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

}
