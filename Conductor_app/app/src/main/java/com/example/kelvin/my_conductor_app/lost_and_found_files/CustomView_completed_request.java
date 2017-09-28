package com.example.kelvin.my_conductor_app.lost_and_found_files;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kelvin.my_conductor_app.R;

import java.util.ArrayList;

/**
 * Created by Kelvin on 3/9/2017.
 */
public class CustomView_completed_request extends BaseAdapter {

    Context context;
    ArrayList<Completed_requests_l_f.completed_req> value1;

    public CustomView_completed_request(Context context, ArrayList<Completed_requests_l_f.completed_req> value1) {
        this.context = context;
        this.value1 = value1;
    }

    private class ViewHolder
    {
        TextView t1,t2,t3,t4,t5;

    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.customview_complete_req, parent, false);

        ViewHolder v1 = new ViewHolder();


        v1.t1 = (TextView) rowView.findViewById(R.id.comp_ticket_id);

        v1.t2 = (TextView) rowView.findViewById(R.id.comp_bus_id);

        v1.t3 = (TextView) rowView.findViewById(R.id.comp_item_name);

        v1.t4 = (TextView) rowView.findViewById(R.id.comp_item_description);

        v1.t5 = (TextView) rowView.findViewById(R.id.comp_item_status);



        v1.t1.setText(value1.get(position).ticket_id);

        v1.t2.setText(value1.get(position).bus_id);

        v1.t3.setText(value1.get(position).item_name);

        v1.t4.setText(value1.get(position).item_description);

        v1.t5.setText(value1.get(position).status);


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
