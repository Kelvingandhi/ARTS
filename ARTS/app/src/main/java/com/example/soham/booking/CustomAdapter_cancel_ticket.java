package com.example.soham.booking;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soham.arts.R;

import java.util.ArrayList;

/**
 * Created by 100HM on 02-03-2017.
 */
public class CustomAdapter_cancel_ticket extends BaseAdapter {

    // static String ticket_id,bus_id,source,destination,toward_date,arrival_time_1,departure_time_1,return_date,arrival_time_2,departure_time_2,passenger,price;
    Context context;
    //upcoming_booking upcoming_booking;
    ArrayList<cancel_bookng.Cancel_Ticket_Lists> rowitem;
    LayoutInflater inflter;
    public CustomAdapter_cancel_ticket(Context context, ArrayList<cancel_bookng.Cancel_Ticket_Lists> rowitem) {

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


        onelistitem = mInflater.inflate(R.layout.custom_cancel, null);

        ViewHolder v1 = new ViewHolder();

        //v1.t1 =(TextView) onelistitem.findViewById(R.id.textView23);

        //v1.t2 =(TextView) onelistitem.findViewById(R.id.can_id);
        v1.t3 =(TextView) onelistitem.findViewById(R.id.can_tktid);
        //v1.t3 =(TextView) onelistitem.findViewById(R.id.textView3);
        v1.t4 =(TextView) onelistitem.findViewById(R.id.can_busid);
        //v1.t4 =(TextView) onelistitem.findViewById(R.id.textView12);
        v1.t5 =(TextView) onelistitem.findViewById(R.id.can_date);
        v1.t6 =(TextView) onelistitem.findViewById(R.id.can_sour);
        v1.t7 =(TextView) onelistitem.findViewById(R.id.can_desti);
        v1.t8 =(TextView) onelistitem.findViewById(R.id.can_journy);
        v1.t9 =(TextView) onelistitem.findViewById(R.id.can_departure);
        v1.t10 =(TextView) onelistitem.findViewById(R.id.can_arrival);
        v1.t11 =(TextView) onelistitem.findViewById(R.id.can_seat);
        v1.t12 =(TextView) onelistitem.findViewById(R.id.can_price);

        v1.t13 =(TextView) onelistitem.findViewById(R.id.textView32);
        v1.t14 =(TextView) onelistitem.findViewById(R.id.textView38);



        v1.t14.setVisibility(View.GONE);

        //toward_time=v1.t4.getText().toString();

        //v1.t0.setText(rowitem.get(position).ticket_id);
        v1.t4.setText(rowitem.get(position).bus_id);
        //v1.t2.setText(rowitem.get(position).del_ticket_id);
        v1.t3.setText(rowitem.get(position).ticket_id);
        v1.t11.setText(rowitem.get(position).seat_no);
        v1.t5.setText(rowitem.get(position).cancelled_date);
        v1.t6.setText(rowitem.get(position).source );
        v1.t12.setText(rowitem.get(position).amount);
        v1.t8.setText(rowitem.get(position).journey_date);
        v1.t9.setText(rowitem.get(position).journey_departure_time);

        v1.t10.setText(rowitem.get(position).journey_arrivial_time);

        v1.t7.setText(rowitem.get(position).destination);



        //toward_time=v1.t4.getText().toString();
        //onelistitem.setTag(holder);

        //ticket_id=rowitem.get(position).ticket_id;

        return onelistitem;

    }

    private static class ViewHolder {
        static TextView t0,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14;
    }


}

