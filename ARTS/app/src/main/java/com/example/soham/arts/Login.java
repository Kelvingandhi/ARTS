package com.example.soham.arts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soham.fragment.HomeFragment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Login extends Activity {
	




	public static String user_id,email,pass;
	public static AutoCompleteTextView e11,e12;
	Button b1;
	TextView b2;
	TextView forget_pass;

	Boolean check_tag;
	public static String name="";
	public static String NAMESPACE="http://tempuri.org/";
	public static String URL="http://192.168.43.162/WebARTS/WebService.asmx";

	public static String METHOD_NAME1="login";
	public static String SOAP_ACTION1="http://tempuri.org/login";

	public static final String PREFS_NAME = "LoginPrefs";

	public static String loginemail;

//	public static String METHOD_NAME2="fatch_user_id";
	//public static String SOAP_ACTION2="http://tempuri.org/fatch_user_id";



	SharedPreferences applicationpreferences;
	SharedPreferences.Editor editor;


	@Override
		protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);




		Boolean flag;


		applicationpreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

		// SharedPreferences applicationpreferences = PreferenceManager
			//	.getDefaultSharedPreferences(PREFS_NAME,MODE_PRIVATE);
		editor = applicationpreferences .edit();

		flag = applicationpreferences .getBoolean("flag", false);

		if (flag) {
///second time activity


			Intent i1 = new Intent(Login.this, com.example.soham.arts.Drawer_Activity.class);
			startActivity(i1);

		}else
		{
			//first time




			setContentView(R.layout.login);

			e11 = (AutoCompleteTextView) findViewById(R.id.editText1);
			e12 = (AutoCompleteTextView) findViewById(R.id.editText2);
			b1 = (Button) findViewById(R.id.button1);
			//b2 = (Button) findViewById(R.id.button2);
			b2 = (TextView) findViewById(R.id.textV);
			forget_pass = (TextView) findViewById(R.id.textforget);

			b1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String email = e11.getText().toString();

					String pass = e12.getText().toString();
					if (email.trim().equals(""))
						e11.setError("Please enter your email");
					else if (pass.trim().equals(""))
						e12.setError("Please enter password");
					else if (!email.contains("@") || !email.contains("."))
						e11.setError("Please enter a valid email Address");


					else {

						loginemail = e11.getText().toString();
						editor.putString("user_email", loginemail);
						editor.putBoolean("flag", true);
						editor.commit();
						new login_new1().execute();
						//new user_id().execute();
					}

				}
			});


			b2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i1 = new Intent(Login.this, Registration.class);
					startActivity(i1);
				}
			});

		forget_pass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(Login.this, Forget_Pass.class);
					startActivity(i1);
				}
			});
		}



	}


	@Override
	public void onResume(){
		super.onResume();
		// put your code here...

		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);

		check_tag=prefs.getBoolean("tagvalue",false);

		if(check_tag==true) {

			Class<?> activityClass;

			try {

				activityClass = Class.forName(
						prefs.getString("lastActivity", Regi_otp.class.getName()));
			} catch (ClassNotFoundException ex) {
				activityClass = Regi_otp.class;
			}

			startActivity(new Intent(this, activityClass));
		}

	}

		public class login_new1 extends AsyncTask<Void, Void, String> {

			ProgressDialog dialog;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();

				dialog=new ProgressDialog(Login.this);
				dialog.setTitle("Loading...");
				dialog.setMessage("Please Wait");
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.show();
			}


			@Override
			protected String doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try
	   			{
	   				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
	   				request.addProperty("email",e11.getText().toString());
	   				request.addProperty("password",e12.getText().toString());

	   				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	   		        envelope.dotNet=true;
	   		        envelope.setOutputSoapObject(request);

	   		        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	   		        androidHttpTransport.call(SOAP_ACTION1, envelope);

	   		        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

	   		        Log.d("message","Message : "+resultstr.toString());
	   		        //Toast.makeText(Login.this, "valid", 0).show();
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


				email = e11.getText().toString();
				pass = e12.getText().toString();

				dialog.dismiss();

				if(!result.toString().equals("false")) {
					user_id = result.toString();
					name = e11.getText().toString();
					Intent i2= new Intent(Login.this, Drawer_Activity.class);
					startActivity(i2);
				}
				else {
					Toast.makeText(Login.this,"plz enter valid email & pass",Toast.LENGTH_SHORT).show();
				}
				/*
				if(result.toString().equals("true"))
				{
				Intent i2= new Intent(Login.this, Homepage.class);
				startActivity(i2);
				}
				else
				{
					Toast.makeText(Login.this, result, Toast.LENGTH_LONG).show();
				}
				*/
			}

		}



	@Override
	public void onBackPressed()
	{
		moveTaskToBack(true);
	}


}
