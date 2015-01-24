package businesslogic;

import android.graphics.Color;

/*This is a wrapper class through which the elements of the traffic light system are accessed
 * The android application uses objects of this class and the getter and setter methods to
 * access the multiple parameters of the traffic light system*/

public class TrafficWrapper 
{
	private CarLight cLight;
	private PedLight pLight; // param used as an object allows the carlight and the pedlight to use the same go and buttonPressed variables
	private TrafficButton mem;
	private Param param;

	public TrafficWrapper()
	{
		param = new Param(false, false);
		cLight = new CarLight(param);
		pLight = new PedLight(param);
		mem = new TrafficButton(param);
	}
	
	public TrafficWrapper(boolean buttonPressed, boolean go)
	{
		param = new Param(buttonPressed, go);
		cLight = new CarLight(param);
		pLight = new PedLight(param);
		mem = new TrafficButton(param);
	}
	
	public TrafficWrapper(boolean buttonPressed, boolean go, int carCount)
	{
		param = new Param(buttonPressed, go);
		cLight = new CarLight(param);
		cLight.setCounter(carCount);
		pLight = new PedLight(param);
		mem = new TrafficButton(param);
	}
	
	public void update()
	{
		cLight.update();
		pLight.update();
		mem.update();
	}
	
	public boolean getGo()
	{
		return param.go;
	}
	
	public boolean getButtonPressed()
	{
		return param.buttonPressed;
	}
	
	public int getPedCol()
	{
		return pLight.getColor();
	}
	
	public int getCarCol()
	{
		return cLight.getColor();
	}
	
	public void setCarCol(int color)
	{
		cLight.setColor(color);
	}
	
	public void setCarCounter(int count)
	{
		cLight.setCounter(count);
	}
	
	public int getCarCounter()
	{
		return cLight.getCounter();
	}
	
	public void setParameters(boolean go, boolean buttonPressed)
	{
		param.go = go;
		param.buttonPressed = buttonPressed;
	}
	
	public void tapButton() 
	{
		mem.tapButton();	
	}
	
	public String getCarColString()
	{
		return colDef(cLight.getColor());
	}
	
	public String getPedColString()
	{
		return colDef(pLight.getColor());
	}
	
	protected String colDef(int r)
	{
		if (r == Color.RED) return "Red";
		if (r == Color.GREEN) return "Green";
		if (r == Color.YELLOW) return "Yellow";
		return null;
	}
}
