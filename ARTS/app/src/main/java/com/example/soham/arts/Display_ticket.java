package com.example.soham.arts;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.soham.booking.upcoming_booking;
import com.example.soham.fragment.HomeFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by 100HM on 02-03-2017.
 */
public class Display_ticket extends AppCompatActivity {


     TextView ticket_id,bus_id,source,destination,toward_date,arrival_time_1,departure_time_1,return_date,arrival_time_2,departure_time_2,single_lady1,passenger,seat_no,price;
    String qr_ticket_id,qr_bus_id,qr_source,bus_type,qr_destination,qr_toward_date,qr_arrival_time_1,qr_departure_time_1,qr_return_date,qr_arrival_time_2,qr_departure_time_2,qr_single_lady,qr_passenger,qr_seat_no,qr_price;

    String name;
    String sigle_lady;
    ImageView image;
    LinearLayout return_trip_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_ticket);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Booking");

       // TextView ticket_id,bus_id,source,destination,toward_date,arrival_time_1,departure_time_1,return_date,arrival_time_2,departure_time_2,single_lady,passenger,price;

        ticket_id=(TextView)findViewById(R.id.tkt_id);
        bus_id=(TextView)findViewById(R.id.bus_id);
        source=(TextView)findViewById(R.id.source);
        destination=(TextView)findViewById(R.id.destination);
        toward_date=(TextView)findViewById(R.id.toward);
        arrival_time_1=(TextView)findViewById(R.id.arrival_1);
        departure_time_1=(TextView)findViewById(R.id.departure_1);

        return_trip_layout=(LinearLayout)findViewById(R.id.return_trip_layout);
        return_date=(TextView)findViewById(R.id.return_date);
        arrival_time_2=(TextView)findViewById(R.id.arrival_2);
        departure_time_2=(TextView)findViewById(R.id.departure_2);
        single_lady1=(TextView)findViewById(R.id.single_lady);
        passenger=(TextView)findViewById(R.id.passenger);
        seat_no=(TextView)findViewById(R.id.seat_no);
        price=(TextView)findViewById(R.id.price);

        image = (ImageView) findViewById(R.id.qr_image);

        ticket_id.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).ticket_id);
        bus_id.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).bus_id);
        source.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).source);
        destination.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).destination);
        toward_date.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).journey_date);
        arrival_time_1.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).journey_arrivial_time);
        departure_time_1.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).journey_departure_time);
        return_date.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).return_date);
        arrival_time_2.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).return_arrivial_time);
        departure_time_2.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).return_departure_time);
        //String sigle_lady=upcoming_booking.tkt_Lists.get(upcoming_booking.posi).single_lady;
        if((upcoming_booking.tkt_Lists.get(upcoming_booking.posi).single_lady.equals("single lady")))
        {
            single_lady1.setText("single lady");
            //sigle_lady="single lady";
            passenger.setText(" 1 ");
        }
        else
        {
            //sigle_lady=" - ";
            single_lady1.setText(" - ");
            //single_lady.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).single_lady);
            passenger.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).no_of_passenger);

        }

        //single_lady1.setText(sigle_lady);



        seat_no.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).seat_no);
        price.setText(upcoming_booking.tkt_Lists.get(upcoming_booking.posi).amount);


        if(return_date.getText().toString().equals(""))
        {
            return_trip_layout.setVisibility(View.GONE);

        }




         QR_gen();


    }

    private void QR_gen() {


        qr_ticket_id = ticket_id.getText().toString().trim();
        qr_bus_id = bus_id.getText().toString().trim();
        name= Drawer_Activity.email.toString().trim();
        String booking_date=upcoming_booking.booking_date;
        qr_source = source.getText().toString().trim();
        qr_destination = destination.getText().toString().trim();
        qr_toward_date = toward_date.getText().toString().trim();
        if(qr_bus_id.equals("1234")) {
           bus_type = "express";
        }
        else
        {
           bus_type = "local";
        }
        qr_arrival_time_1 = arrival_time_1.getText().toString().trim();
        qr_departure_time_1 = departure_time_1.getText().toString().trim();
        qr_return_date = return_date.getText().toString().trim();
        qr_arrival_time_2 = arrival_time_2.getText().toString().trim();
        qr_departure_time_2 = departure_time_2.getText().toString().trim();
        qr_single_lady = single_lady1.getText().toString().trim();
        //qr_single_lady = "-";
        qr_passenger = passenger.getText().toString().trim();
        qr_seat_no = seat_no.getText().toString().trim();
        qr_price = price.getText().toString().trim();



        String concate=qr_ticket_id+"#"+qr_bus_id+"#"+name+"#"+booking_date+"#"+qr_source+"#"+qr_destination+"#"+qr_toward_date+"#"+bus_type+"#"+qr_passenger+"#"+qr_single_lady+"#"+qr_seat_no+"#"+qr_price;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix= multiFormatWriter.encode(concate, BarcodeFormat.QR_CODE,100,100);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


}
