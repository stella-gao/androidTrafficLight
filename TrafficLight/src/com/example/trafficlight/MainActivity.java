package com.example.trafficlight;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import businesslogic.TrafficWrapper;

public class MainActivity extends ActionBarActivity {

	SharedPreferences sharedpreferences;
	static TrafficWrapper trafficElem;
	Timer timer;
	boolean paused;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		trafficElem = new TrafficWrapper();
		paused = false;

		sharedpreferences = getSharedPreferences("TrafficButton", Context.MODE_PRIVATE);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment())
			.commit();
		}
		timer = new Timer();

		timer.schedule(new TimerTask(){
			@Override
			public void run()
			{
				if(!paused)
				{
					trafficElem.update();

				}			
			}
		},1500,1000);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	@Override
	protected void onPause() {
		super.onPause();
		paused = true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		paused = false;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState (Bundle outState)
	{
		super.onSaveInstanceState(outState);
		Editor editor = sharedpreferences.edit();
		editor.putInt("carCounter", trafficElem.getCarCounter());
		editor.putInt("carCol", trafficElem.getCarCol());
		editor.putBoolean("go", trafficElem.getGo());
		editor.putBoolean("buttonPressed", trafficElem.getButtonPressed());
		editor.commit();

	}

	@Override
	protected void onRestoreInstanceState (Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		if (sharedpreferences.contains("carCol") && sharedpreferences.contains("carCounter") && sharedpreferences.contains("go") && sharedpreferences.contains("buttonPressed"))
		{
			int count = sharedpreferences.getInt("carCounter", -1);
			boolean go = sharedpreferences.getBoolean("go", false);
			boolean buttonPressed = sharedpreferences.getBoolean("buttonPressed", false);
			trafficElem.setCarCounter(count);
			trafficElem.setParameters(go, buttonPressed);
			trafficElem.setCarCol(sharedpreferences.getInt("carCol", 0));
		}
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			final Button backButton = (Button) rootView.findViewById(R.id.myTestButton1);

			backButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					trafficElem.tapButton();
					trafficElem.update();

					Intent myIntent = new Intent(getActivity(),CarLightActivity.class);
					myIntent.putExtra("buttonPressed", trafficElem.getButtonPressed());
					myIntent.putExtra("go", trafficElem.getGo());
					myIntent.putExtra("carCounter",trafficElem.getCarCounter());

					startActivity(myIntent);
				}
			});

			return rootView;
		}
	}

}
