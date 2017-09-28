package com.example.kelvin.my_conductor_app.lost_and_found_files;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kelvin.my_conductor_app.R;

import java.util.ArrayList;

/**
 * Created by Kelvin on 3/9/2017.
 */
public class CustomView_pending_request extends BaseAdapter {

    Context context;
    ArrayList<Pending_requests_l_f.pending_req> value1;

    public CustomView_pending_request(Context context, ArrayList<Pending_requests_l_f.pending_req> value1) {
        this.context = context;
        this.value1 = value1;
    }

    private class ViewHolder
    {
        TextView t1,t2,t3,t4;

    }


    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.customview_pending_req, parent, false);

        ViewHolder v1 = new ViewHolder();


        v1.t1 = (TextView) rowView.findViewById(R.id.pending_ticket_id);

        v1.t2 = (TextView) rowView.findViewById(R.id.pending_bus_id);

        v1.t3 = (TextView) rowView.findViewById(R.id.pending_item_name);

        v1.t4 = (TextView) rowView.findViewById(R.id.pending_item_description);



        v1.t1.setText(value1.get(position).ticket_id);

        v1.t2.setText(value1.get(position).bus_id);

        v1.t3.setText(value1.get(position).item_name);

        v1.t4.setText(value1.get(position).item_description);


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
