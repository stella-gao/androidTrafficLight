package com.example.trafficlight;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import businesslogic.TrafficWrapper;

public class CarLightActivity extends Activity 
{
	TrafficWrapper trafficElem;
	Timer timer;
	boolean paused;
	SharedPreferences sharedpreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		sharedpreferences = getSharedPreferences("CarLight", Context.MODE_PRIVATE);	
		trafficElem = new TrafficWrapper(getIntent().getExtras().getBoolean("buttonPressed"), getIntent().getExtras().getBoolean("go"), getIntent().getExtras().getInt("carCounter"));
		Log.d("carCount!", trafficElem.getCarCounter()+"");
		final SecondView sv = new SecondView(this, trafficElem);
		paused = false;

		setContentView(sv);

		timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run()
			{
				if(!paused)
				{
					tick();
					sv.postInvalidate();
				}			
			}
		},1500,1000);


	}

	void tick()
	{
		trafficElem.update();

		if(trafficElem.getGo())
			gotoPed();
	}

	void gotoPed()
	{
		Intent myIntent = new Intent(this, PedLightActivity.class);
		myIntent.putExtra("buttonPressed1", trafficElem.getButtonPressed());
		myIntent.putExtra("go1", trafficElem.getGo());
		startActivity(myIntent);
	}

	@Override
	protected void onSaveInstanceState (Bundle outState)
	{
		super.onSaveInstanceState(outState);
		Editor editor = sharedpreferences.edit();
		editor.putInt("carCounter", trafficElem.getCarCounter());
		editor.putBoolean("go", trafficElem.getGo());
		editor.putInt("carCol", trafficElem.getCarCol());
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
			trafficElem.setCarCol(sharedpreferences.getInt("carCol", 0));
			trafficElem.setParameters(go, buttonPressed);
		}	
	}

	@Override
	protected void onPause ()
	{
		super.onPause();
		paused = true;
	}

	@Override
	protected void onResume ()
	{
		super.onResume();
		paused = false;
	}



	@Override
	protected void onDestroy ()
	{
		super.onDestroy();
		timer.cancel();
	}

}

