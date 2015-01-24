package com.example.trafficlight;

import businesslogic.TrafficWrapper;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

public class SecondView extends View {

	TrafficWrapper trafficElem;
	private Paint paint = new Paint(); // reuse
	public SecondView(Context context, TrafficWrapper trafficElem) {
		super(context);
		this.trafficElem = trafficElem;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		paint.setColor(trafficElem.getCarCol());
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(getWidth ()/ 2, getHeight()/2, 80, paint);

		paint.setColor(Color.BLACK);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(20);
		canvas.drawText("Car Light ", getWidth ()/ 2, 20, paint);

		paint.setColor(Color.BLACK);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(15);
		canvas.drawText("Counter "+ (trafficElem.getCarCounter() + 1), getWidth ()/ 2, 40, paint);

		paint.setColor(Color.BLACK);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(11);
		canvas.drawText("Current PedLight Colour: "+ colDef(trafficElem.getPedCol()), getWidth ()/ 2, getHeight()/2 + 150, paint);
	}
	protected String colDef(int r)
	{
		if (r == Color.RED) return "Red";
		if (r == Color.GREEN) return "Green";
		if (r == Color.YELLOW) return "Yellow";
		return null;
	}
}