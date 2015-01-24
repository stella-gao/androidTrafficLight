package businesslogic;

/*
 * This class is used to maintain the buttonPressed and go variables in an object
 * "param" = "parameter", since buttonPressed and go are parameters of the system
 * param used as an object allows the carlight and the pedlight to use the same go 
 * and buttonPressed variables
 * */

public class Param
{
	public boolean buttonPressed;
	public boolean go;

	public Param()
	{
		buttonPressed = false;
		go = false;
	}
	
	public Param(boolean buttonPressed, boolean go)
	{
		this.buttonPressed = buttonPressed;
		this.go = go;
	}
	
	Param( Param p1 )
	{
		buttonPressed = p1.buttonPressed;
		go = p1.go;
	}
}
