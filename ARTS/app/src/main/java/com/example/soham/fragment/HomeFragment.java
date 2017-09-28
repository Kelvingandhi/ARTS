package com.example.soham.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.soham.arts.Between_station;
import com.example.soham.arts.Booking;
import com.example.soham.arts.Cancelled_bus_action;
import com.example.soham.arts.Fare_inquiry_bus_search;
import com.example.soham.arts.Live_bus_status;
import com.example.soham.arts.Live_station_status;
import com.example.soham.arts.Login;
import com.example.soham.arts.Lost_item_request;
import com.example.soham.arts.New_bus_action;
import com.example.soham.arts.R;
import com.example.soham.arts.Ticket_Genrate;
import com.example.soham.arts.backgroundservice;
import com.example.soham.googlemapdemo.MapActivity;
import com.example.soham.googlemapdemo.MapsActivity;
import com.google.android.gcm.GCMRegistrar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Button live_bus_status;
    Button between_station;
    Button ticket_gen;
    Button booking,New_bus_action,fair_inquiry,live_station_status;
    Button logout,wlt_racharge,cancel_bus_action,lost_item_request,profilebtn;
    public static String resulturl;

    public static String email;
    public static String regId;

    public static String NAMESPACE = "http://tempuri.org/";

    public static String URL = Login.URL;




    public static String METHOD_NAME1 = "Delete_pushid";
    public static String SOAP_ACTION1 = "http://tempuri.org/Delete_pushid";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final HomeFragment activity = this;
        View v = inflater.inflate(R.layout.home, container, false);


        New_bus_action = (Button) v.findViewById(R.id.button6);
        live_bus_status = (Button) v.findViewById(R.id.button3);
        live_station_status = (Button) v.findViewById(R.id.station_status);
        between_station = (Button) v.findViewById(R.id.button2);

        cancel_bus_action = (Button) v.findViewById(R.id.button5);
        ticket_gen = (Button) v.findViewById(R.id.button7);
        booking = (Button) v.findViewById(R.id.button8);
        fair_inquiry = (Button) v.findViewById(R.id.button9);
        lost_item_request = (Button) v.findViewById(R.id.lost_item_req);



        if(!isMyServiceRunning(backgroundservice.class))
        {
            getActivity().startService(new Intent(getActivity(),backgroundservice.class));
        }

        live_bus_status.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(getActivity(), Live_bus_status.class);
                startActivity(i1);

            }
        });


        live_station_status.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent i1 = new Intent(getActivity(), Live_station_status.class);
                startActivity(i1);

            }
        });







        lost_item_request.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(getActivity(),Lost_item_request.class);
                startActivity(i1);

            }
        });
        cancel_bus_action.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(getActivity(),Cancelled_bus_action.class);
                startActivity(i1);

            }
        });
        fair_inquiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(getActivity(),Fare_inquiry_bus_search.class);
                startActivity(i1);

            }
        });


        between_station.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(getActivity(), Between_station.class);
                startActivity(i1);

            }
        });

        ticket_gen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent i1 = new Intent(getActivity(), Ticket_Genrate.class);
                startActivity(i1);

            }
        });

        booking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(getActivity(), Booking.class);
                startActivity(i1);

            }
        });

        New_bus_action.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(getActivity(), com.example.soham.arts.New_bus_action.class);
                startActivity(i1);

            }
        });




        return  v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





    /*@Override
    public void onBackPressed()
    {
       getActivity().moveTaskToBack(true);
    }*/

  /*  @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            getActivity().finish();
        }
    }*/

/*
    private class OnBackPressedListener {
        void onBackPressed();
    }
    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getActivity().getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            //TODO: Perform your logic to pass back press here
            for(Fragment fragment : fragmentList){
                if(fragment instanceof OnBackPressedListener){
                    ((OnBackPressedListener)fragment).onBackPressed();
                }
            }
        }
    }

*/


/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(getActivity(),"You cancelled the Scanning",Toast.LENGTH_LONG).show();
            }
            else
            {

                // Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
               /* String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                */

                //resulturl = "";
                //resulturl = result.getContents();
                //Toast.makeText(getActivity(),resulturl.toString(),Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(getActivity(), Live_bus_status.class);
                //intent.putExtra("qr_bus_no",resulturl);
                //startActivity(intent);

                /*Uri uri = Uri.parse(resulturl);

                ////Check if scanned result is valid URL or not...........
                //////if not then simply Toast it
                //if(URLUtil.isValidUrl(String.valueOf(uri)) ){
                if(Patterns.WEB_URL.matcher(String.valueOf(uri)).matches()) {


                    //Intent intent = new Intent(QR_Scanner.this,after_scan.class);
                    //startActivity(intent);

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getActivity(),result.getContents(),Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
*/
    public boolean isMyServiceRunning(Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }




}
