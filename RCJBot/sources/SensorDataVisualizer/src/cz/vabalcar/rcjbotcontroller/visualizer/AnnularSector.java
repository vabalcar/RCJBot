package cz.vabalcar.rcjbotcontroller.visualizer;

/**
 * This class represents a paintable <a href = "http://mathcentral.uregina.ca/QQ/database/QQ.09.08/h/ed4.html" >annular sector</a>.<br>
 * <img alt = "annular sector schema" src = "../../../../../../resources/annularSector.png">  
 */
public class AnnularSector {
    
    /** the outer arc */
    private final Arc outerArc;
    
    /** the inner arc */
    private final Arc innerArc;
    
    /** the fill color of AnnularSector */
    private IntColor color;
    
    /**
     * Instantiates a new annular sector.
     * @param center the center of the annular sector
     * @param outerRadius the outer radius of the annular sector
     * @param innerRadius the inner radius of the annular sector
     * @param start the starting angle of the annular sector in degrees
     * @param extent the angular extent of the annular sector in degrees
     */
    public AnnularSector(Point center, float outerRadius, float innerRadius, float start, float extent) {
        outerArc = new Arc(countBounds(center, outerRadius), start, extent);
        innerArc = new Arc(countBounds(center, innerRadius), start - 5, extent + 10);
    }
    
    /**
     * Counts the bounds (outer rectangle) of an annular sector with center at point center and given radius.
     *
     * @param center the center of the annular sector
     * @param radius the radius of the annular sector
     * @return the bounds of the annular sector
     */
    private static Rectangle countBounds(Point center, float radius) {
        float x = center.getX() - radius;
        float y = center.getY() - radius;
        float width = radius * 2;
        float height = radius * 2;
        return new Rectangle(x, y, width, height);
    }
    
    /**
     * Gets the fill color of the annular sector.
     *
     * @return the fill color of the annular sector
     */
    public IntColor getColor() {
        return color;
    }
    
    /**
     * Sets the fill color of the annular sector.
     *
     * @param color the new fill color of annular sector
     */
    public void setColor(IntColor color) {
        this.color = color;
    }
    
    /**
     * Updates position and size of the annular sector.
     *
     * @param center the center of the annular sector
     * @param outerRadius the outer radius of the annular sector
     * @param innerRadius the inner radius of the annular sector
     */
    public void update(Point center, float outerRadius , float innerRadius) {
        outerArc.setBounds(countBounds(center, outerRadius));
        innerArc.setBounds(countBounds(center, innerRadius));
    }
    
    /**
     * Paints the annular sector.
     *
     * @param g the Graphics2D to paint the annular sector with. 
     * @param background the background color to use during painting the annular sector.
     */
    public void paint(Graphics g, IntColor background) {
        IntColor originalColor = g.getColor();
        g.setColor(color);
        g.fill(outerArc);
        g.setColor(background);
        g.fill(innerArc);
        g.setColor(originalColor);
    }
}
