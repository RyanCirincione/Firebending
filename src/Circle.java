
public class Circle 
{
	double posx;
	double posy;
	double heat;
	double radius;
	double velocityX;
	double velocityY;
	int timer;
	public Circle (double x, double y, double hotboy,double velx, double vely)
	{
		posx = x;
		posy = y;
		heat = hotboy;
		radius = (int) (heat/10);//radius is 1/10 of the heat of the circle
		velocityX = velx;
		velocityY = vely;
		timer = 10;
	}
	public void update()
	{
		heat = heat * 99/100;
		radius = (int) (heat);
		posx += velocityX;
		posy += velocityY;
		timer--;
	}
	
}
