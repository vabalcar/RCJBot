package cz.vabalcar.rcjbotcontroller.visualizer;

/**
 * This class is a visualizer of data from JBot's touch sensor.  
 */
public class CrashVisualizer {

    /** The inner annular sector - geometric realization of the CrashVisualizer. */
    private final AnnularSector annularSector;
    
    /** the crashed - variable which stores whether the CrashVisualizer is visualizing crash or not. */
    private boolean crashed;
    
    /**
     * Instantiates a new CrashVisualizer.
     * 
     * @param center the center of the CrashVisualizer
     * @param radius the radius of the CrashVisualizer
     * @param start the starting angle of the CrashVisualizer in degrees
     * @param extent the angular extent of the CrashVisualizer in degrees
     */
    public CrashVisualizer(Point center, float radius, float start, float extent) {
        annularSector = new AnnularSector(center, radius, getInnerRadius(radius), start, extent);
        annularSector.setColor(IntColor.RED);
    }
    
    /**
     * Gets the inner radius of the crash visualizer.
     *
     * @param outerRadius the outer radius  of the crash visualizer
     * @return the inner radius of the crash visualizer
     */
    private static float getInnerRadius(float outerRadius) {
        return outerRadius / 2;
    }
    
    /**
     * Determines whether the CrashVisualizer is visualizing crash or not.
     *
     * @return <code>true</code> if CrashVisualizer is visualizing crash, <code>false</code> otherwise
     */
    public boolean getCrashed() {
        return crashed;
    }
    
    /**
     * Sets whether the CrashVisualizer is visualizing crash or not.
     *
     * @param crashed new CrashVisualizer's visualization state  
     */
    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }
    
    /**
     * Updates position and size of the circle.
     *
     * @param center the center of the CrashVisualizer
     * @param radius the radius of the CrashVisualizer
     */
    public void updateModel(Point center, float radius) {
        annularSector.update(center, radius, getInnerRadius(radius));
    }
    
    /**
     * Paints the CrashVisualizer.
     *
     * @param g the Graphics2D to paint the CrashVisualizer with
     * @param background the background color to use during painting the CrashVisualizer
     *
     */
    public void paint(Graphics g, IntColor background) {
        if (crashed) {
            annularSector.paint(g, background);
        }
    }

}
