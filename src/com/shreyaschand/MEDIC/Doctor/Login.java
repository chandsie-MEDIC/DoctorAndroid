package com.shreyaschand.MEDIC.Doctor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViewById(R.id.login_submit_button).setOnClickListener(this);
	}

	public void onClick(View v) {
		String uname = ((EditText)findViewById(R.id.login_username)).getText().toString();
		String pword = ((EditText)findViewById(R.id.login_password)).getText().toString();

		Toast.makeText(this, "Logging In...", Toast.LENGTH_SHORT).show();
		String response = "Error";
		try {
			String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(uname, "UTF-8") + "&" 
						+ URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pword, "UTF-8");
			URLConnection conn = new URL("http://chands.dyndns-server.com:4080/auth/").openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
			response = br.readLine();
			br.close();
		} catch (IOException e) {e.printStackTrace();Toast.makeText(this, "Connection Error. Try Again Later.", Toast.LENGTH_SHORT).show();}
		if(response.equals("Error")) {
			Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Welcome, " + response, Toast.LENGTH_SHORT).show();
			//Call next activity
		}
	}
}
