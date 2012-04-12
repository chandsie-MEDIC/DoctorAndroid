package com.shreyaschand.MEDIC.Doctor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class About extends Activity implements OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        findViewById(R.id.about_back_button).setOnClickListener(this);
	}

	public void onClick(View arg0) {
		finish();
	}
}
