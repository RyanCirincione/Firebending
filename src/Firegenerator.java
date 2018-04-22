import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Firegenerator 
{
	static int[][] heat = new int[640][480];
	static boolean flip = true;//test del later
	static public void paintFire(Graphics video, ArrayList<Vertex> vertices)//verices going clockwise
	{
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
						heat[i][j] = heat[i][j] -20;
					}
				}
			}
		}
		Vertex center = findCenter(vertices);//sets Vertex Center to the centerish of the shape
		ArrayList<Circle> circs = new ArrayList<Circle>();
		for(int i = 1; i < vertices.size()-1; i++)
		{
			if(vertices.get(i).heat > 1)//arbitrary number up to change to determine fire creation
			{
				if(vertices.get(i).distance2(vertices.get(i).x, vertices.get(i).y, center.x, center.y)>300)//kill trues
				{//distance from center is the sqrt of the integer value
				double changex = ((vertices.get(i).x-vertices.get(i-1).x)*-1) + (vertices.get(i).x-vertices.get(i+1).x)*-1;
				double changey = ((vertices.get(i).y-vertices.get(i-1).x)*-1) + ((vertices.get(i).y-vertices.get(i+1).x)*-1);
				circs.add(new Circle((int)(vertices.get(i).x+changex),(int)(vertices.get(i).y+changey),
						vertices.get(i).heat,changex,changey));
					
//					Color fire = new Color(255, 1, 0);
//					int changeX = (int)(current.x - current.px);
//					int changeY = (int)(current.y - current.py);
//					int pixelx = (int)(current.x);
//					int pixely = (int)(current.y);
//					double heatReduction = current.heat;
//					while(pixely > 0 && pixely < 480 && pixelx > 0 && pixelx < 640)
//					{
//						System.out.println(vertices.get(0).x);
//						heat[pixelx][pixely] =  heat[pixelx][pixely]+(int)heatReduction;
//						if(heat[pixelx][pixely]>500)
//						{
//							heat[pixelx][pixely] = 500;
//						}
//						heatReduction = heatReduction - 10;
//						Color temp = new Color (fire.getRed(), (int)(fire.getGreen()*(heat[pixelx][pixely]/2)),0);
//						video.setColor(temp);
//						video.fillOval(pixelx, pixely, 20, 20);
//						pixelx = pixelx + changeX;
//						pixely = pixely + changeY;
//						if(heatReduction <= 0)
//						{
//							break;
//						}
//					}
					
				}
			}
		}
		for(int x = 0; x < heat.length; x++)
		{
			for(int y = 0; y < heat[x].length;y++)
			{
				for(int c = 0; c < circs.size(); c++)
				{
					if(( Math.pow((x-circs.get(c).posy),2)+(Math.pow((y-circs.get(c).posx), 2)) 
							< Math.pow((circs.get(c).radius),2)))
					{
						heat[x][y] =  heat[x][y] + (int)(circs.get(c).heat * (1 - (Math.pow((x-circs.get(c).posy),2))+
								(Math.pow((y-circs.get(c).posx), 2))/Math.pow((circs.get(c).radius),2)));
						System.out.println(heat[x][y]);
						if(heat[x][y]>500)
						{
							heat[x][y]=500;
						}
					}
					
					if(heat[x][y]>1)//hekpes
					{
						System.out.println("ASS BURGERS");
						video.setColor(new Color(255,heat[x][y]/2,0));
						video.fillRect(x, y, 50, 50);
					}
				}
			}
		}	
		for(int i = 0; i <circs.size();i++)
		{
			circs.get(i).update();
			video.fillOval(circs.get(i).posx -circs.get(i).radius , circs.get(i).posy -circs.get(i).radius , 
					circs.get(i).radius*2, circs.get(i).radius*2);
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
