package cz.vabalcar.rcjbotcontroller.visualizer;

/**
 * This class represents a paintable circle.
 */
public class Circle {
    private Point center;
    private float radius;

    /**
     * Instantiates a new circle.
     *
     * @param center the center of the circle
     * @param radius the radius of the circle
     */
    public Circle(Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }
    
    public Point getCenter() {
		return center;
	}
    
    public void setCenter(Point center) {
		this.center = center;
	}
    
    public float getRadius() {
		return radius;
	}
    
    public void setRadius(float radius) {
		this.radius = radius;
	}
}
