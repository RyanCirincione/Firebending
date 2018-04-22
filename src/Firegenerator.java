import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Firegenerator 
{
	static int[][] heat = new int[640][480];
	static boolean flip = true;//test del later
	static ArrayList<Circle> circs = new ArrayList<Circle>();

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
						heat[i][j] = heat[i][j] -10;
					}
				}
			}
		}
		Vertex center = findCenter(vertices);//sets Vertex Center to the centerish of the shape
		for(int i = 1; i < vertices.size()-1; i++)
		{
			if(vertices.get(i).heat > 1)//arbitrary number up to change to determine fire creation
			{
				if(vertices.get(i).distance2(vertices.get(i).x, vertices.get(i).y, center.x, center.y)>300)//kill trues
				{//distance from center is the sqrt of the integer value
				double changex = ((vertices.get(i).x-vertices.get(i-1).x)*-1) + (vertices.get(i).x-vertices.get(i+1).x)*-1;
				double changey = ((vertices.get(i).y-vertices.get(i-1).y)*-1) + ((vertices.get(i).y-vertices.get(i+1).y)*-1);
				double d = Math.sqrt(Vertex.distance2(0, 0, changex, changey));
//				System.out.println("d: " + d + " x: " + changex + " y: " + changey + " " + vertices.get(i) + ",  " +
//				vertices.get(i-1) + ", " + vertices.get(i+1));//TODO delete
				circs.add(new Circle((int)(vertices.get(i).x+changex),(int)(vertices.get(i).y+changey),
						vertices.get(i).heat*10,changex*20/d,changey*20/d));//heer 
					
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
		for(int i = 0; i < circs.size(); i++)
		{
			for(int x = (int) Math.max(0, (circs.get(i).posx - circs.get(i).radius)); 
				x < Math.min(640, circs.get(i).posx+circs.get(i).radius);x++)
			{
				for(int y = (int) Math.max(0, (circs.get(i).posy - circs.get(i).radius)); 
					y < Math.min(480, circs.get(i).posy+circs.get(i).radius);y++)
				{
					heat[x][y] = (int) (circs.get(i).heat*(10-Math.sqrt(Math.sqrt(Vertex.distance2(x, y, circs.get(i).posx, circs.get(i).posy)))));
//					System.out.println(heat[x][y]);
					if(heat[x][y]>125)
					{
						heat[x][y] = 125;
					}
//						System.out.println(heat[x][y]);
//								video.fillOval((int)(circs.get(i).posx -circs.get(i).radius), (int)(circs.get(i).posy -circs.get(i).radius ), 
//							(int)circs.get(i).radius*2, (int)circs.get(i).radius*2);
				}
			}
		}
//		System.out.println(circs.size());
		for(int i = 0; i < heat.length; i++)
		{
			for(int j = 0; j < heat[i].length;j++)
			{
				if(heat[i][j]>30)
				{
					video.setColor(new Color(255,heat[i][j]*2,0));
					video.fillRect(i, j, 1, 1);
				}
			}
		}
		
		
		
		//		for(int x = 0; x < heat.length; x++)
//		{
//			for(int y = 0; y < heat[x].length;y++)
//			{
//				for(int c = 0; c < circs.size(); c++)
//				{
//					if(( Math.pow((x-circs.get(c).posx),2)+(Math.pow((y-circs.get(c).posy), 2)) 
//							< Math.pow((circs.get(c).radius),2)))
//					{
////						heat[x][y] =  heat[x][y] + (int)(circs.get(c).heat * (1 - (Math.pow((x-circs.get(c).posx),2))+
////								(Math.pow((y-circs.get(c).posy), 2))/Math.pow((circs.get(c).radius),2)));
//						if(Vertex.distance2(x, y, circs.get(c).posx, circs.get(c).posy) < Math.pow(circs.get(c).radius,2))
//							heat[x][y] += (int)(10*(circs.get(c).heat/Vertex.distance2(x, y, circs.get(c).posx, circs.get(c).posy)));
//						if(heat[x][y]>125)
//						{
//							heat[x][y]=125;
//						}
//					}
//					
//					if(heat[x][y]>1)//hekpes
//					{
//						video.setColor(new Color(255,heat[x][y]*2,0));
//						video.fillRect(x, y, 1, 1);
//					}
//				}
//			}
//		}	
		for(int i = 0; i <circs.size();i++)
		{
			circs.get(i).update();
//			video.setColor(Color.MAGENTA);
//			video.fillOval((int)circs.get(i).posx, (int)circs.get(i).posy, 20, 20);

//			video.fillOval(circs.get(i).posx -circs.get(i).radius , circs.get(i).posy -circs.get(i).radius , 
//					circs.get(i).radius*2, circs.get(i).radius*2);
//			System.out.println(circs.get(0).posx + "X");
//			System.out.println(circs.get(0).posy + "Y");
			if(circs.get(i).timer <=0)
			{
				circs.remove(i);
				i--;
			} else {
//				System.out.println("t" + circs.get(i).timer);
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
		int counter = 0;
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
