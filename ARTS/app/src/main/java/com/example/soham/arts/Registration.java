package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Registration extends Activity {

	


	
	  public static EditText e1,e2,e3,e4,e5,e6;

	public static String email,fullname,gender,pass,conpass,mobile;
	  public static RadioButton r1,r2;
	   Button b1;
	   
	   	public static String NAMESPACE="http://tempuri.org/";
		public static String URL=Login.URL;
		
		public static String METHOD_NAME="registration_insert_otp";
		public static String SOAP_ACTION="http://tempuri.org/registration_insert_otp";


	   
	   @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		
			setContentView(R.layout.registration);
			
			
			
			e5= (EditText) findViewById(R.id.name);
			e1= (EditText) findViewById(R.id.email);
			e2= (EditText) findViewById(R.id.password);
			e3= (EditText) findViewById(R.id.conf_password);
			e4= (EditText) findViewById(R.id.mobile_no);
		  // e6= (EditText) findViewById(R.id.promo_code);
			
			r1= (RadioButton) findViewById(R.id.radioButton1);
			r2= (RadioButton) findViewById(R.id.radioButton2);
			
			
		

			
			b1= (Button) findViewById(R.id.button1);
			
			b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub


					new create_otp().execute();
					
				}
			}); 
					
	   }

	@Override
	public void onBackPressed() {

		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		SharedPreferences.Editor otpeditor = prefs.edit();
		otpeditor.putBoolean("tagvalue",false);
		otpeditor.commit();

		finish();
		Intent intent = new Intent(Registration.this, Login.class);
		startActivity(intent);
	}


	private class create_otp  extends AsyncTask<Void , Void, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog=new ProgressDialog(Registration.this);
			dialog.setTitle("Loading");
			dialog.setMessage("Please  wait...");
			dialog.setIcon(R.drawable.ic_launcher);
			dialog.show();

		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			try
			{
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);



				request.addProperty("email_id", e1.getText().toString());





				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);

				SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

				Log.d("message","Message : "+resultstr.toString());
				return resultstr.toString();


			}
			catch(Exception e)
			{
				Log.e("ERROR", e.toString());
				return "fail";

			}


		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			dialog.dismiss();

			fullname = e5.getText().toString();
			email = e1.getText().toString();

			pass = e2.getText().toString();
			conpass = e3.getText().toString();
			mobile = e4.getText().toString();

			if(r1.isChecked())
			{
				gender=r1.getText().toString();
			}
			else
			{
				gender=r2.getText().toString();
			}

			Toast.makeText(Registration.this, result, Toast.LENGTH_LONG).show();
			Intent intent = new Intent(Registration.this,Regi_otp.class);

			intent.putExtra("email",email);

			startActivity(intent);

			Intent intent1 = new Intent(Registration.this,Invite_code.class);
			intent1.putExtra("full_name",fullname);
			intent1.putExtra("email",email);
			intent1.putExtra("pass",pass);
			intent1.putExtra("con_pass",conpass);
			intent1.putExtra("gender",gender);
			intent1.putExtra("mobile_no",mobile);





		}

	}
}
