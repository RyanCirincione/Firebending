import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Firegenerator 
{
	static int[][] heat = new int[640][480];
	static boolean flip = true;//test del later
	static public void paintFire(Graphics video, ArrayList<Vertex> blah)//verices going clockwise
	{
		//block to remove <block>
		ArrayList<Vertex> vertices =new ArrayList<Vertex>();
		vertices.add(new Vertex(120,120));//hard code test case remove later
		vertices.get(0).px = 115;
		vertices.get(0).py = 115;
		vertices.get(0).heat = 50;
		//blockto remove </block>
		System.out.println(vertices.size());//test delete later
		
		for(int i = 0; i < heat.length; i++)
		{
			for(int j = 0; j < heat[i].length;j++)
			{
				if(heat[i][j]!=0)
				{
					if(heat[i][j] < 20||heat[i][j]<0)
					{
						heat[i][j] =0;
					}
					else if(heat[i][j]>10)
					{
						heat[i][j] = heat[i][j] -2;
					}
				}
			}
		}
		Vertex center = findCenter(vertices);//sets Vertex Center to the centerish of the shape
		
			if(vertices.get(0).x < 590 && flip)
			{
				vertices.get(0).update(vertices.get(0).x+5, vertices.get(0).y);
			}
			else if(vertices.get(0).x > 50&&!flip)
			{
				vertices.get(0).update(vertices.get(0).x-5, vertices.get(0).y);
			}
		for(Vertex current : vertices)
		{
			if(current.heat > 10||true)//arbitrary number up to change to determine fire creation
			{
				if(current.distance2(current.x, current.y, center.x, center.y)>300||true)//kill trues
				{//distance from center is the sqrt of the integer value
					Color fire = new Color(255, 1, 0);
					int changeX = (int)(current.x - current.px);
					int changeY = (int)(current.y - current.py);
					int pixelx = (int)(current.x);
					int pixely = (int)(current.y);
					double heatReduction = current.heat;
					while(pixely > 0 && pixely < 480 && pixelx > 0 && pixelx < 640)
					{
						System.out.println(vertices.get(0).x);
						heat[pixelx][pixely] =  heat[pixelx][pixely]+(int)heatReduction;
						if(heat[pixelx][pixely]>500)
						{
							heat[pixelx][pixely] = 500;
						}
						heatReduction = heatReduction - 10;
						Color temp = new Color (fire.getRed(), (int)(fire.getGreen()*(heat[pixelx][pixely]/2)),0);
						video.setColor(temp);
						video.fillRect(pixelx, pixely, 10, 10);
						pixelx = pixelx + changeX;
						pixely = pixely + changeY;
						if(heatReduction <= 0)
						{
							break;
						}
					}
					
				}
			}
		}
		}
		
	/**
	@param ArrayList<Vertex>: recives an arrayList Of vertexs
	@return Vertex: the center vertex of the shape, used to calculate fire and distance from vertex.
		
	**/
	static public Vertex findCenter (ArrayList<Vertex> vertices)
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
