package cz.vabalcar.rcjbotcontroller.visualizer;

/**
 * This class is a visualizer of data from JBot's color sensor.
 */
public class SurfaceColorVisualizer {
    
    /** the circle - the geometric realization of the SurfaceColorVisualizer */
    private final Circle circle;
    
    private IntColor color;
    
    /**
     * Instantiates a new SurfaceColorVisualizer.
     *
     * @param center the center of the SurfaceColorVisualizer
     * @param radius the radius of the SurfaceColorVisualizer
     */
    public SurfaceColorVisualizer(Point center, float radius) {
        circle = new Circle(center, radius);
    }
    
    /**
     * Gets the fill color of the SurfaceColorVisualizer.
     *
     * @return the fill color of the SurfaceColorVisualizer
     */
    public IntColor getColor() {
        return color;
    }
    
    /**
     * Sets the fill color of the SurfaceColorVisualizer.
     *
     * @param color the fill color of the SurfaceColorVisualizer
     */
    public void setColor(IntColor color) {
        this.color = color;
    }
    
    /**
     * Updates position and size of the SurfaceColorVisualizer.
     *
     * @param center the center of the SurfaceColorVisualizer
     * @param radius the radius of the SurfaceColorVisualizer
     */
    public void updateModel(Point center, float radius) {
        circle.setCenter(center);
        circle.setRadius(radius);
    }
    
    /**
     * Paints the SurfaceColorVisualizer.
     *
     * @param g the Graphics2D to paint the SurfaceColorVisualizer with
     */
    public void paint(Graphics g) {
    	IntColor originalColor = g.getColor();
    	g.setColor(color);
        g.fill(circle);
        g.setColor(originalColor);
    }

}
