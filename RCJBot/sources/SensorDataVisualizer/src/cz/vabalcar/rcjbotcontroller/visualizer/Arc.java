package cz.vabalcar.rcjbotcontroller.visualizer;

public class Arc {
	private Rectangle bounds;
	private float start;
	private float extent;
	
	public Arc(Rectangle bounds, float start, float extent) {
		this.bounds = bounds;
		this.start = start;
		this.extent = extent;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public float getStart() {
		return start;
	}
	
	public float getExtent() {
		return extent;
	}
}
