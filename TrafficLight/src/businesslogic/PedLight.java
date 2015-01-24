package businesslogic;
import android.graphics.Color;

/*This class manages the pedestrian light, similar to the pedlight module of NuSMV*/

public class PedLight
{
	public int col;
	Param param;
	public PedLight(Param p)
	{
		param = p;
		if(param.go)
			col = Color.GREEN;
		else
			col = Color.RED;
	}

	public void update()
	{
		if(col == Color.GREEN && !param.go)
			col = Color.RED;
		else if(col == Color.RED && param.go)
			col = Color.GREEN;
	}
	
	public int getColor()
	{
		return col;
	}
}
