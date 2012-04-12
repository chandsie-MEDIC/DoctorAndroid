package com.shreyaschand.MEDIC.Doctor;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MEDIC extends Activity implements OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.home_about_button).setOnClickListener(this);
		findViewById(R.id.home_login_button).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_about_button:
			startActivity(new Intent(this, About.class));
			break;
		case R.id.home_login_button:
			startActivity(new Intent(this, Login.class));
			break;
		}

    }
}