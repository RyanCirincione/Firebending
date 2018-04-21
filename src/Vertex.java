

public class Vertex {
	public double x, y, px, py, heat;
	
	public Vertex(double x, double y) {
		this(x, y, 0);
	}
	
	public Vertex(double x, double y, double heat) {
		this.x = this.px = x;
		this.y = this.py = y;
		this.heat = heat;
	}
	
	public void update(double x, double y) {
		px = this.x;
		this.x = x;
		py = this.y;
		this.y = y;
		
		this.heat -= 0.05;
		double d = distance2(x, y, px, py);
		if(d < 10*10) {
			this.heat += d / 100; 
		}
	}
	
	public double distance2(double x1, double y1, double x2, double y2) {
		return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
	}
}
