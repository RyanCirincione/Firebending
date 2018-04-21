
public class Vertex {
	public double x, y, heat;
	
	public Vertex(double x, double y) {
		this(x, y, 0);
	}
	
	public Vertex(double x, double y, double heat) {
		this.x = x;
		this.y = y;
		this.heat = heat;
	}
}
