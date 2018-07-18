package cz.vabalcar.rcjbotcontroller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * This class represents a paintable <a href = "http://mathcentral.uregina.ca/QQ/database/QQ.09.08/h/ed4.html" >annular sector</a>.<br>
 * <img alt = "annular sector schema" src = "../../../../../resources/annularSector.png">  
 */
public class AnnularSector {
    
    /** the outer arc */
    private final Arc2D outerArc;
    
    /** the inner arc */
    private final Arc2D innerArc;
    
    /** the fill color of AnnularSector */
    private Color color;
    
    /**
     * Instantiates a new annular sector.
     * @param center the center of the annular sector
     * @param outerRadius the outer radius of the annular sector
     * @param innerRadius the inner radius of the annular sector
     * @param start the starting angle of the annular sector in degrees
     * @param extent the angular extent of the annular sector in degrees
     */
    public AnnularSector(Point2D center, double outerRadius, double innerRadius, double start, double extent) {
        outerArc = new Arc2D.Double(countBounds(center, outerRadius), start, extent, Arc2D.PIE);
        innerArc = new Arc2D.Double(countBounds(center, innerRadius), start - 5, extent + 10, Arc2D.PIE);
    }
    
    /**
     * Counts the bounds (outer rectangle) of an annular sector with center at point center and given radius.
     *
     * @param center the center of the annular sector
     * @param radius the radius of the annular sector
     * @return the bounds of the annular sector
     */
    private static Rectangle2D countBounds(Point2D center, double radius) {
        double x = center.getX() - radius;
        double y = center.getY() - radius;
        double width = radius * 2;
        double height = radius * 2;
        return new Rectangle2D.Double(x, y, width, height);
    }
    
    /**
     * Gets the fill color of the annular sector.
     *
     * @return the fill color of the annular sector
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Sets the fill color of the annular sector.
     *
     * @param color the new fill color of annular sector
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Updates position and size of the annular sector.
     *
     * @param center the center of the annular sector
     * @param outerRadius the outer radius of the annular sector
     * @param innerRadius the inner radius of the annular sector
     */
    public void update(Point2D center, double outerRadius , double innerRadius) {
        outerArc.setFrame(countBounds(center, outerRadius));
        innerArc.setFrame(countBounds(center, innerRadius));
    }
    
    /**
     * Paints the annular sector.
     *
     * @param g the Graphics2D to paint the annular sector with. 
     * @param background the background color to use during painting the annular sector.
     */
    public void paint(Graphics2D g, Color background) {
        Color originalColor = g.getColor();
        g.setColor(color);
        g.fill(outerArc);
        g.setColor(background);
        g.fill(innerArc);
        g.setColor(originalColor);
    }
}
