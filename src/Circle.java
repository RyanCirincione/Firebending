
public class Circle 
{
	int posx;
	int posy;
	double heat;
	int radius;
	double velocityX;
	double velocityY;
	public Circle (int x, int y, double hotboy,double velx, double vely)
	{
		posx = x;
		posy = y;
		heat = hotboy;
		radius = (int) (heat/10);//radius is 1/10 of the heat of the circle
		velocityX = velx;
		velocityY = vely;
	}
	public void update()
	{
		heat = heat * 3/4;
		radius = (int) (heat);
		posx += velocityX;
		posy += velocityY;
	}
	
}
