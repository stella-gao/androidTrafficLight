package com.example.trafficlight;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import businesslogic.TrafficWrapper;

public class PedLightActivity extends Activity 
{
	SharedPreferences sharedpreferences;
	TrafficWrapper trafficElem;
	Timer timer;
	boolean paused;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		sharedpreferences = getSharedPreferences("PedLight", Context.MODE_PRIVATE);	
		paused = false;
		trafficElem = new TrafficWrapper(getIntent().getExtras().getBoolean("buttonPressed1"), getIntent().getExtras().getBoolean("go1"));
		trafficElem.setCarCol(Color.RED);

		final ThirdView tv = new ThirdView(this, trafficElem);
		setContentView(tv);

		timer = new Timer();

		timer.schedule(new TimerTask(){
			@Override
			public void run()
			{
				if(!paused)
				{
					tick();
					tv.postInvalidate();
				}

			}
		},1500,1000);
	}

	void tick()
	{
		trafficElem.update();

		if(trafficElem.getPedCol() == Color.RED)
			gotoButtonActivity();		
	}

	void gotoButtonActivity()
	{
		Intent myIntent = new Intent(this, MainActivity.class);
		paused = true;
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
	protected void onResume()
	{
		super.onResume();
		paused = false;		
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		paused = true;		
	}

	@Override
	protected void onDestroy ()
	{
		super.onDestroy();
		timer.cancel();		
	}
}
