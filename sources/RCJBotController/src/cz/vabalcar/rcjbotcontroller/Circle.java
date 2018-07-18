package cz.vabalcar.rcjbotcontroller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

/**
 * This class represents a paintable circle.
 */
public class Circle {
    
    /** the "real" circle */
    private final RoundRectangle2D circle = new RoundRectangle2D.Double();
    
    /** the fill color of the circle  */
    private Color color;

    /**
     * Instantiates a new circle.
     *
     * @param center the center of the circle
     * @param radius the radius of the circle
     */
    public Circle(Point2D center, double radius) {
        update(center, radius);
    }
    
    /**
     * Counts the bounds (outer rectangle) of a circle with center at point center and given radius.
     *
     * @param center the center of the circle
     * @param radius the radius of the circle
     * @return the bounds of the circle
     */
    private static RoundRectangle2D countBounds(Point2D center, double radius) {
        double x = center.getX() - radius;
        double y = center.getY() - radius;
        double width = radius * 2;
        return new RoundRectangle2D.Double(x, y, width, width, width, width);
    }
    
    /**
     * Gets the fill color of the circle.
     *
     * @return the fill color of the circle
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Sets the fill color of the circle.
     *
     * @param color the new fill color of the circle
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Updates position and size of the circle.
     *
     * @param center the center of the circle
     * @param radius the radius of the circle
     */
    public void update(Point2D center, double radius) {
        circle.setRoundRect(countBounds(center, radius));
    }
    
    /**
     * Paints the circle.
     *
     * @param g the Graphics2D to paint the circle with.
     */
    public void paint(Graphics2D g) {
        Color originalColor = g.getColor();
        g.setColor(color);
        g.fill(circle);
        g.setColor(originalColor);
    }
}
