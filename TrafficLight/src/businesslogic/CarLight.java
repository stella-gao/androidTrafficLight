package businesslogic;
import android.graphics.Color;

/*This class manages the car light, similar to the carlight module of NuSMV*/

public class CarLight
{
	private int col;
	private int count;
	private Param param;

	public CarLight(Param p)
	{
		count = 0;
		col = Color.GREEN;
		param = p;
	}
	
	public CarLight(Param p, int c)
	{
		count = 0;
		col = Color.GREEN;
		param = p;
		col = c;
	}

	public void update()
	{
		if(col == Color.GREEN)
		{
			if(count < 9)
			{
				count = count + 1;
				return;
			}
			if(!param.buttonPressed && count >= 9)
			{
				count = 10;
				return;
			}
			if(count >= 9 && param.buttonPressed)
			{
				count = 0;
				param.go = false;
				
				col = Color.YELLOW;
				return;
			}
		}

		if(col == Color.YELLOW)
		{
			if(count < 1)
			{
				count++;
				return;
			}
			if(count == 1)
			{
				count = 0;
				param.go = true;
				
				col = Color.RED;
				return;
			}
		}

		if(col == Color.RED)
		{

			if(count < 4)
			{
				count++;
				return;
			}
			if(count == 4)
			{
				count = 0;
				col = Color.GREEN;
				param.go = false;
				
				return;
			}
		}
	}
	
	public int getCounter()
	{
		return count;
	}
	
	public int getColor()
	{
		return col;
	}
	
	public void setCounter(int count)
	{
		this.count = count;
	}

	public void setColor(int c)
	{
		col = c;
	}
}

