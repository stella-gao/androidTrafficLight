package businesslogic;

/*This class manages remembering the button being pressed, similar to the memory module of NuSMV*/
public class TrafficButton
{
	boolean buttonTapped;
	Param param;

	public TrafficButton(Param p)
	{
		buttonTapped = false;
		param = p;
	}

	public void tapButton()
	{
		if(!param.go)
			buttonTapped = true;
	}

	public void update()
	{
		if(!param.buttonPressed && buttonTapped && !param.go)
		{
			param.buttonPressed = true;
			
		}

		else if(param.buttonPressed && param.go)
		{
			buttonTapped = false;
			param.buttonPressed = false;
		}
	}
}
