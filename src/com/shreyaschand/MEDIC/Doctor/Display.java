package com.shreyaschand.MEDIC.Doctor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

public class Display extends Activity implements OnClickListener {

	private TextView output = null;
	private ScrollView scroller = null;
	private Socket socket = null;
	private String user;
	private ArrayList<String> data;
	private TextView button;
	private SoundPool sounds;
	private int thumpy;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);

		button = ((TextView) findViewById(R.id.display_back_button));
		button.setText("Play");
		button.setEnabled(false);
		
		output = ((TextView) findViewById(R.id.test_output));
		scroller = (ScrollView) findViewById(R.id.display_scroller);
		user = getIntent().getExtras().getString("com.shreyaschand.MEDIC.Doctor.user");
	}

	public void onStart() {
		super.onStart();
		new ConnectSocket().execute();
	}

	public void onClick(View arg0) {
		new SamplePlayer().execute();
	}

	private class ConnectSocket extends AsyncTask<Void, String, Boolean> {

		protected Boolean doInBackground(Void... params) {
			try {
				publishProgress("Establishing connection...\n");
				socket = new Socket("chands.dyndns-server.com", 1028);
				publishProgress("Connected.\n");
				return true;
			} catch (IOException e) {return false;}
		}

		protected void onProgressUpdate(String... updates) {
			output.append(updates[0]);
			scroller.fullScroll(ScrollView.FOCUS_DOWN);
		}

		protected void onPostExecute(Boolean result) {
			if (result) {
				new SocketCommunicator().execute(socket);
			} else {
				output.append("Error connecting.");
				scroller.fullScroll(ScrollView.FOCUS_DOWN);
			}
		}
	}

	private class SocketCommunicator extends AsyncTask<Socket, String, Boolean> {
		protected Boolean doInBackground(Socket... socket) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket[0].getInputStream()));
				PrintWriter out = new PrintWriter(socket[0].getOutputStream());
				out.println("$DOC$" + user);
				out.flush();
				data = new ArrayList<String>(2700);
				String message = in.readLine();
				while (message != null) {
					publishProgress(new String[] { message });
					data.add(message);
					message = in.readLine();
				}
			} catch (IOException e) {e.printStackTrace();return false;}
			return true;
		}

		protected void onProgressUpdate(String... update) {
			output.append("\n" + update[0]);
			scroller.fullScroll(ScrollView.FOCUS_DOWN);
		}
		
		protected void onPostExecute(Boolean result) {
			if(result) {
				output.append("\nConnection closed.\nNow Ready to Play Sample...\n");
				scroller.fullScroll(ScrollView.FOCUS_DOWN);
								
				 for(int i = 0; i != data.size(); i++) {
						String curr = data.get(i);
						if(!curr.equals("0")) {
							for(int j = i+1; !data.get(j).equals("0"); j++) {
								data.set(j, "0");
							}
						}		
					}
				
				button.setEnabled(true);
			}else {
				output.append("\nConnection lost unexepectedly.");
				scroller.fullScroll(ScrollView.FOCUS_DOWN);
			}
		}
	}

	private class SamplePlayer extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... nothing) {
			sounds = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
			thumpy = sounds.load(getApplicationContext(), R.raw.beat, 1);
			int interval = 5000/data.size();
			
			for(int i = 0; i != data.size(); i++) {
				if(!data.get(i).equals("0")) {
					sounds.play(thumpy, 1.0f, 1.0f, 0, 0, 1.0f);
				}
				long time = System.currentTimeMillis();
				while(System.currentTimeMillis() - time < interval) {}
			}
			
			
			return null;			
		}

		protected void onProgressUpdate(Void... update) {}
		
		protected void onPostExecute(Void result) {}
	}

}
