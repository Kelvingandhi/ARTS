package com.example.soham.googlemapdemo;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.soham.arts.Login;
import com.example.soham.arts.R;
import com.example.soham.arts.View_between_station;
import com.example.soham.fragment.HomeFragment;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String station;

    TimerTask timerTask2;


    public static String abcd="";

    Timer timer = new Timer();
    Handler handler2 = new Handler();


    public static Marker m2;
    String[] rows;
    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;

    public static String METHOD_NAME2 = "fetch_bus_latlong_for_bus_track";
    public static String SOAP_ACTION2 = "http://tempuri.org/fetch_bus_latlong_for_bus_track";


    public static String METHOD_NAME3 = "current_latlong_user";
    public static String SOAP_ACTION3 = "http://tempuri.org/current_latlong_user";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


       /* for (int i = 0; i < View_between_station.bus_route_list.size(); i++) {
            station = View_between_station.bus_route_list.get(i);
            Toast.makeText(MapActivity.this, station,0).show();
            new map_data().execute();


        }*/


        //Toast.makeText(MapActivity.this, View_between_station.listarray.toString(),0).show();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     * Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        new map_data().execute();


       /* ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                // Hit WebService
                new map_data_user().execute();
            }
        }, 0, 2, TimeUnit.SECONDS);*/


        new map_data_user().execute();


        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                handler2.post(new Runnable() {
                    @Override
                    public void run() {


                        m2.remove();




                        new map_data_user().execute();

                        // Toast.makeText(MapActivity.this, "hi", Toast.LENGTH_LONG).show();

                    }
                });
            }


        };


        timer.scheduleAtFixedRate(timerTask2, 1000, 10000);

    }



    public class map_data extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(MapsActivity.this);
            dialog.setTitle("Loading  ..");
            dialog.setMessage("Please Wait");
            dialog.setIcon(R.drawable.ic_launcher);

            dialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub

            try {
                SoapObject request = new SoapObject(MapsActivity.NAMESPACE, METHOD_NAME2);


                Intent i = getIntent();
                abcd = i.getStringExtra("qr_bus_no");
                //abcd=HomeFragment.resulturl.toString();
                request.addProperty("bus_id",abcd);
                //request.addProperty("bus_id", View_between_station.bus_id.toString());
                //request.addProperty("source", View_between_station.source.toString());
                //request.addProperty("desti", View_between_station.destination.toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MapsActivity.URL);
                androidHttpTransport.call(SOAP_ACTION2, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "Message : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";
            }
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //  GoogleMap googleMap = null;

            Toast.makeText(MapsActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            String rows[] = result.split("`");

            dialog.dismiss();
            //data.clear();
            for (int i = 0; i < rows.length; i++) {

                String cols1[] = rows[i].split("~");
                LatLng a1 = new LatLng(Float.parseFloat(cols1[2]), Float.parseFloat(cols1[3]));
                Log.d("data--------", cols1[2] + "-------" + cols1[3]);
                // googleMap.addMarker(new MarkerOptions().position(a1).title("Water Available").);


                String[] cols2;
                if(i==rows.length-1)
                {
                    cols2 = rows[i].split("~");
                }
                else {
                    cols2 = rows[i + 1].split("~");
                }
                // googleMap.addMarker(new MarkerOptions().position(a1).title("Water Available").);


                //      marker  ==  set position marker at lat , lng ..

                Marker m1;

                m1=mMap.addMarker(new MarkerOptions()
                        .position(a1)
                        .title("Water Available")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(a1, 10));



                /*if ( geoFenceLimits != null )
                    geoFenceLimits.remove();*/

                CircleOptions circleOptions = new CircleOptions()
                        .center( m1.getPosition())
                        .strokeColor(Color.argb(50, 70,70,70))
                        .fillColor( Color.argb(100, 150,150,150) )
                        .radius( 1000 );
                geoFenceLimits = mMap.addCircle( circleOptions );

                //createGeofence(a1, GEOFENCE_RADIUS);


                //geoFenceMarker = mMap.addMarker(markerOptions);


                LatLng origin = new LatLng(Float.parseFloat(cols1[2]), Float.parseFloat(cols1[3]));
                LatLng dest = new LatLng(Float.parseFloat(cols2[2]), Float.parseFloat(cols2[3]));

// Getting URL to the Google Directions API

                DownloadTask downloadTask = new DownloadTask();
                String url = getDirectionsUrl(origin, dest);

                //DownloadTask downloadTask = new DownloadTask();

// Start downloading json data from Google Directions API
                downloadTask.execute(url);

            }
        }


    }


    public class map_data_user extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(MapsActivity.this);
            dialog.setTitle("Loading  ..");
            dialog.setMessage("Please Wait");
            dialog.setIcon(R.drawable.ic_launcher);

            dialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub

            try {
                SoapObject request = new SoapObject(MapsActivity.NAMESPACE, METHOD_NAME3);


                //request.addProperty("bus_id", abcd.toString());
                //abcd= HomeFragment.resulturl.toString();
                request.addProperty("bus_id", abcd.toString());
                //request.addProperty("bus_id", View_between_station.bus_id.toString());
                //request.addProperty("source", View_between_station.source.toString());
                //request.addProperty("desti", View_between_station.destination.toString());


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MapsActivity.URL);
                androidHttpTransport.call(SOAP_ACTION3, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "Message : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";
            }
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //  GoogleMap googleMap = null;

            Toast.makeText(MapsActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            String rows[] = result.split("`");

            dialog.dismiss();
            //data.clear();



            for (int i = 0; i < rows.length; i++) {
                String cols[] = rows[i].split("~");
                LatLng a1 = new LatLng(Float.parseFloat(cols[2]), Float.parseFloat(cols[3]));
                Log.d("data--------", cols[2] + "-------" + cols[3]);
                //      marker  ==  set position marker at lat , lng ..




                m2=mMap.addMarker(new MarkerOptions()
                        .position(a1)
                        .title("Water Available")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));






                //private void drawGeofence() {


                //}

                //private Geofence createGeofence(LatLng a1, float radius) {

                //}
                //(a1, GEOFENCE_RADIUS);

                //geoFenceMarker = mMap.addMarker(markerOptions);


            }


        }



    }

    // Draw Geofence circle on GoogleMap
    private Circle geoFenceLimits;





    private String getDirectionsUrl(LatLng origin, LatLng dest) {


        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            java.net.URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url" , e.toString());

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);

        }
    }

    public class DirectionsJSONParser {

        /**
         * Receives a JSONObject and returns a list of lists containing latitude and longitude
         */
        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<HashMap<String, String>>();

                    /** Traversing all legs */
                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps */
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points */
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                                hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }

            return routes;
        }










/*
    private Marker geoFenceMarker;
    private void markerForGeofence(LatLng latLng) {
        //Log.i(TAG, "markerForGeofence("+latLng+")");
        String title = latLng.latitude + ", " + latLng.longitude;
        // Define marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title(title);
        if ( mMap!=null ) {
            // Remove last geoFenceMarker
            if (geoFenceMarker != null)
                geoFenceMarker.remove();

            geoFenceMarker = mMap.addMarker(markerOptions);

        }
    }


    */




/*
    //private Marker locationMarker;
    // Start Geofence creation process
    private void startGeofence() {
        //Log.i(TAG, "startGeofence()");
        if( geoFenceMarker != null ) {
            Geofence geofence = createGeofence( geoFenceMarker.getPosition(), GEOFENCE_RADIUS );
            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
            addGeofence( geofenceRequest );
        } else {
            Log.e(TAG, "Geofence marker is null");
        }
    }

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 250.0f; // in meters
    // Create a Geofence

    private Circle geoFenceLimits;

    private Geofence createGeofence( LatLng latLng, float radius ) {
        Log.d("TAG", "createGeofence");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration( GEO_DURATION )
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();

        CircleOptions circleOptions = new CircleOptions()
                .center( geoFenceMarker.getPosition())
                .strokeColor(Color.argb(50, 70,70,70))
                .fillColor( Color.argb(100, 150,150,150) )
                .radius( GEOFENCE_RADIUS );
        geoFenceLimits = mMap.addCircle( circleOptions );
    }



    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        //Log.d(TAG, "addGeofence");
        if (checkPermission())
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
    }

*/

    /*
   // private Marker geoFenceMarker;
    private Marker locationMarker;
    private void markerLocation(LatLng latLng) {
        Log.i("latlng  ", "markerLocation("+latLng+")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        if ( mMap!=null ) {
            if ( locationMarker != null )
                locationMarker.remove();
            locationMarker = mMap.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            mMap.animateCamera(cameraUpdate);
        }
    }

    private Marker geoFenceMarker;
    private void markerForGeofence(LatLng latLng) {
        //Log.i(TAG, "markerForGeofence("+latLng+")");
        String title = latLng.latitude + ", " + latLng.longitude;
        // Define marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title(title);
        if ( mMap!=null ) {
            // Remove last geoFenceMarker
            if (geoFenceMarker != null)
                geoFenceMarker.remove();

            geoFenceMarker = mMap.addMarker(markerOptions);

        }
    }

    // Start Geofence creation process
    private void startGeofence() {
        //Log.i(TAG, "startGeofence()");
        if( geoFenceMarker != null ) {
            Geofence geofence = createGeofence( geoFenceMarker.getPosition(), GEOFENCE_RADIUS );
            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
            addGeofence( geofenceRequest );
        } else {
            Log.e("TAG", "Geofence marker is null");
        }
    }

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 250.0f; // in meters

    // Create a Geofence
    private Geofence createGeofence( LatLng latLng, float radius ) {
        Log.d("TAG", "createGeofence");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration( GEO_DURATION )
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
        Log.d("TAG", "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence( geofence )
                .build();
    }

    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    private PendingIntent createGeofencePendingIntent() {
        Log.d("TAG", "createGeofencePendingIntent");
        if ( geoFencePendingIntent != null )
            return geoFencePendingIntent;

        Intent intent = new Intent( this, GeofenceTrasitionService.class);
        return PendingIntent.getService(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");
        if (checkPermission())
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull Status status) {
        //Log.i(TAG, "onResult: " + status);
        if ( status.isSuccess() ) {
            //saveGeofence();
            drawGeofence();
        } else {
            // inform about fail
        }
    }

    // Draw Geofence circle on GoogleMap
    private Circle geoFenceLimits;
    private void drawGeofence() {
        Log.d("TAG", "drawGeofence()");

        if ( geoFenceLimits != null )
            geoFenceLimits.remove();
        // getLastKnownLocation();

        CircleOptions circleOptions = new CircleOptions()
                .center( geoFenceMarker.getPosition())
                .strokeColor(Color.argb(50, 70, 70, 70))
                .fillColor( Color.argb(100, 150,150,150) )
                .radius( GEOFENCE_RADIUS );
        geoFenceLimits = map.addCircle( circleOptions );
    }
    */


    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}


