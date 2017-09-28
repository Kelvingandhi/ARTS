package com.example.kelvin.my_conductor_app.lost_and_found_files;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kelvin.my_conductor_app.Login;
import com.example.kelvin.my_conductor_app.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Kelvin on 3/6/2017.
 */
public class Pending_requests_l_f extends Fragment {

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;
    public static String METHOD_NAME = "lost_found_data_conductor";
    public static String SOAP_ACTION = "http://tempuri.org/lost_found_data_conductor";

    ListView pending_req_list;
    TextView no_data;
    public static int posi;

    public static ArrayList<pending_req> pending_req_array_list = new ArrayList<>();


    class pending_req {
        String req_id;
        String ticket_id;
        String bus_id;
        String item_name;
        String item_description;

        public pending_req(String req_id,String ticket_id, String bus_id, String item_name, String item_description) {
            this.req_id = req_id;
            this.ticket_id = ticket_id;
            this.bus_id = bus_id;
            this.item_name = item_name;
            this.item_description = item_description;
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pending_requests, container, false);

        pending_req_list = (ListView) rootView.findViewById(R.id.pending_req_listView);
        no_data = (TextView) rootView.findViewById(R.id.No_data_textView2);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerForContextMenu(pending_req_list);

        new lost_found_data().execute();


    }

    private class lost_found_data extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity().getApplicationContext());
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait....");
            dialog.setIcon(R.mipmap.ic_launcher);
        }


        @Override
        protected String doInBackground(Void... arg0) {

            try {
                SharedPreferences applicationpreferences = getActivity().getSharedPreferences(Login.PREFS_NAME,getActivity().MODE_PRIVATE);
                //SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
                String emailaddr=applicationpreferences.getString("user_email","");

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("c_email",emailaddr);
                //request.addProperty("pass",Login.single_login_pass);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "Message : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";

            }
            // return null;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();

            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            if(!result.equals("fail")) {

                no_data.setVisibility(View.GONE);

                pending_req_array_list.clear();

                String[] rows = result.split("`");


                String[] cols = new String[0];
                pending_req temp = null;


                for (int i = 0; i < rows.length; i++) {
                    cols = rows[i].split("~");


                    temp = new pending_req(cols[0], cols[1], cols[2], cols[3], cols[4]);
                    pending_req_array_list.add(temp);


                }


                CustomView_pending_request adapter = new CustomView_pending_request(getActivity().getApplicationContext(), pending_req_array_list);
                pending_req_list.setAdapter(adapter);
            }
            else if(result.equals(""))
            {
                no_data.setVisibility(View.VISIBLE);
                no_data.setText("No Requests");
            }
            else
            {
                no_data.setVisibility(View.VISIBLE);
                no_data.setText("No Requests");
            }

           /* pending_req_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    posi=position;
                }
            });*/

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.add("Give Response");



        menu.setHeaderTitle("ARTS "+pending_req_array_list.get(info.position).bus_id);//...set header title name as selected item name
        menu.setHeaderIcon(android.R.drawable.ic_menu_add);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();  // make a final

        posi = info.position;

        if (item.getTitle().equals("Give Response"))
        {
            Intent i1= new Intent(getActivity().getApplicationContext(), Give_response.class);
            i1.putExtra("position",posi);
            startActivity(i1);
        }


        return super.onContextItemSelected(item);
    }



}
