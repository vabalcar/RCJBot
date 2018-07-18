package cz.vabalcar.rcjbotcontroller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * This class is a visualizer of data from JBot's color sensor.
 */
public class SurfaceColorVisualizer {
    
    /** the circle - the geometric realization of the SurfaceColorVisualizer */
    private final Circle circle;
    
    /**
     * Instantiates a new SurfaceColorVisualizer.
     *
     * @param center the center of the SurfaceColorVisualizer
     * @param radius the radius of the SurfaceColorVisualizer
     */
    public SurfaceColorVisualizer(Point2D center, double radius) {
        circle = new Circle(center, radius);
    }
    
    /**
     * Gets the fill color of the SurfaceColorVisualizer.
     *
     * @return the fill color of the SurfaceColorVisualizer
     */
    public Color getColor() {
        return circle.getColor();
    }
    
    /**
     * Sets the fill color of the SurfaceColorVisualizer.
     *
     * @param color the fill color of the SurfaceColorVisualizer
     */
    public void setColor(Color color) {
        circle.setColor(color);
    }
    
    /**
     * Updates position and size of the SurfaceColorVisualizer.
     *
     * @param center the center of the SurfaceColorVisualizer
     * @param radius the radius of the SurfaceColorVisualizer
     */
    public void update(Point2D center, double radius) {
        circle.update(center, radius);
    }
    
    /**
     * Paints the SurfaceColorVisualizer.
     *
     * @param g the Graphics2D to paint the SurfaceColorVisualizer with
     */
    public void paint(Graphics2D g) {
        circle.paint(g);
    }

}
