package com.example.kelvin.my_conductor_app;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomView_QRticket_details extends BaseAdapter {

    Context context;
    ArrayList<MainActivity.ticket_value> value1;

    public CustomView_QRticket_details(Context context, ArrayList<MainActivity.ticket_value> value1) {
        this.context = context;
        this.value1 = value1;
    }

    private class ViewHolder
    {
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;

    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.activity_custom_view__qrticket_details, parent, false);

        ViewHolder v1 = new ViewHolder();


        v1.t1 = (TextView) rowView.findViewById(R.id.qr_tkt_text1);

        v1.t2 = (TextView) rowView.findViewById(R.id.qr_tkt_text2);

        v1.t3 = (TextView) rowView.findViewById(R.id.qr_tkt_text3);

        v1.t4 = (TextView) rowView.findViewById(R.id.qr_tkt_text4);

        v1.t5 = (TextView) rowView.findViewById(R.id.qr_tkt_text5);

        v1.t6 = (TextView) rowView.findViewById(R.id.qr_tkt_text6);

        v1.t7 = (TextView) rowView.findViewById(R.id.qr_tkt_text7);

        v1.t8 = (TextView) rowView.findViewById(R.id.qr_tkt_text8);

        v1.t9 = (TextView) rowView.findViewById(R.id.qr_tkt_text9);

        v1.t10 = (TextView) rowView.findViewById(R.id.qr_tkt_text10);

        v1.t11 = (TextView) rowView.findViewById(R.id.qr_tkt_text11);

        v1.t12 = (TextView) rowView.findViewById(R.id.qr_tkt_text12);



        // Set the values to textviews



        //v1.t1.setText(value1.get(position).source);

        v1.t1.setText(value1.get(position).ticket_id);

        v1.t2.setText(value1.get(position).bus_id);

        //String t = (value1.get(position).source)+" to "+(value1.get(position).destination);

        v1.t3.setText(value1.get(position).u_name);

        v1.t4.setText(value1.get(position).booking_date);

        v1.t5.setText(value1.get(position).source);

        v1.t6.setText(value1.get(position).destination);

        v1.t7.setText(value1.get(position).journey_date);

        v1.t8.setText(value1.get(position).bus_type);

        v1.t9.setText(value1.get(position).no_of_passenger);

        v1.t10.setText(value1.get(position).single_lady);

        v1.t11.setText(value1.get(position).seat_no);

        v1.t12.setText(value1.get(position).amount);

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
