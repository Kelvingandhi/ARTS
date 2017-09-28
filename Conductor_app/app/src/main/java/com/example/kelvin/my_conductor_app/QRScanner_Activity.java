package com.example.kelvin.my_conductor_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


public class QRScanner_Activity extends AppCompatActivity {

    public static int position;

    Button scan_btn;

    public static ArrayList<ticket_value> ticket_value_list = new ArrayList<>();

    static class ticket_value{
        String ticket_id;
        String bus_id;
        String u_name;
        String booking_date;
        String source;
        String destination;
        String journey_date;
        String bus_type;
        String no_of_passenger;
        String single_lady;
        String seat_no;
        String amount;

        public ticket_value(String ticket_id,String bus_id,String u_name,String booking_date,String source, String destination,
                            String journey_date, String bus_type, String no_of_passenger,String single_lady, String seat_no,
                            String amount) {

            this.ticket_id = ticket_id;
            this.bus_id = bus_id;
            this.u_name = u_name;
            this.booking_date = booking_date;
            this.source = source;
            this.destination = destination;
            this.journey_date = journey_date;
            this.bus_type = bus_type;
            this.no_of_passenger = no_of_passenger;
            this.single_lady = single_lady;
            this.seat_no = seat_no;
            this.amount = amount;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner_);

        scan_btn = (Button) findViewById(R.id.scan_button);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QR-Ticket Scanner");


        final Activity activity = this;

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(this,"You cancelled the Scanning",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();

                String strresult = result.getContents();

                String[] values = strresult.split("#");

               // int l=values[0].length();


               // String ch=values[0].substring(26,27);

                //values[0]=ch;
               // Toast.makeText(this,values[0] ,Toast.LENGTH_LONG).show();
               // values[0].replace(values[0],);
                //String[] new_value=values[1].split(":");

                //values[0] = new_value[1];

                if(values.length==12)
                {

                    ticket_value_list.clear();

                    ticket_value temp = null;

                    temp = new ticket_value(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11]);
                    ticket_value_list.add(temp);

                    Intent i1 = new Intent(QRScanner_Activity.this,After_scan_Activity.class);
                    startActivity(i1);

                }
                else
                {
                    Toast.makeText(this, "Ticket is not valid",Toast.LENGTH_LONG).show();
                }

            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }



    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        Intent i1 = new Intent(QRScanner_Activity.this,MainActivity.class);
        startActivity(i1);
    }


}
