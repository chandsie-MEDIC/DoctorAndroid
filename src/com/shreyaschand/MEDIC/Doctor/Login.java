package com.shreyaschand.MEDIC.Doctor;

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
		 
		//Do stuff with uname and pword
		//Call next activity
		
	}
}
