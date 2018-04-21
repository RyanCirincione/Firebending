import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Firegenerator 
{
	public void paintFire(Graphics video, ArrayList<Vertex> vertices)//verices going clockwise
	{
		Vertex center = findCenter(vertices);//sets Vertex Center to the centerish of the shape
		for(Vertex current : vertices)
		{
			if(current.heat > 10)//arbitrary number up to change to determine fire creation
			{
				if(current.distance2(current.x, current.y, center.x, center.y)>300)
				{//distance from center is the sqrt of the integer value
					int changeX = (int)(current.x - current.px);
					int changeY = (int)(current.y - current.py);
					int pixelx = (int)(current.x);
					int pixely = (int)(current.y);
					while(pixely>0&&pixely>480&&pixelx<0&&pixelx<480)
					{
						video.setColor(Color.RED);
						video.drawRect(pixelx, pixely, 10, 10);
					}
					
				}
			}
		}
		
	}/**
	@param ArrayList<Vertex>: recives an arrayList Of vertexs
	@return Vertex: the center vertex of the shape, used to calculate fire and distance from vertex.
		
	**/
	public Vertex findCenter (ArrayList<Vertex> vertices)
	{//temp method of finding center will be replaced later with better method
		double sumx = 0;
		double sumy = 0;
		int counter =0;
		Vertex center = new Vertex(0,0,0);
		for(Vertex current : vertices)
		{
			sumx = sumx + current.x;
			sumy = sumy + current.y;
			counter++;
		}
		center.x = sumx/counter;
		center.y = sumy/counter;
		return center;
	}
}
