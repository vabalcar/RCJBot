package cz.vabalcar.rcjbotcontroller.visualizer;

/**
 * This class provides visualizer of data received from the RemoteJBot. It's Swing compatible 
 * visualisable Java Bean and can be treated just like any other GUI element from Swing, for instance
 * it can be used in Eclipse's WindowBuilder to create GUI containing the SensorDataVisualizer easily.
 */
public class SensorDataVisualizer {
	
	private final ViewContainer view;
    
    /** the inner ProximityVisualizer */
    private final ProximityVisualizer proximityVisualizer;
    
    /** the inner CrashVisualizer */
    private final CrashVisualizer crashVisualizer;
    
    /** the inner SurfaceColorVisualizer */
    private final SurfaceColorVisualizer surfaceColorVisualizer;
    
    /** the center of the SensorDataVisualizer */
    private Point center = new Point();
    
    /** the radius of the SensorDataVisualizer */
    private float radius = 0;
    
    /** the boolean which stores whether repaint happens automatically after change of visualized data or not */
    private boolean autoRepaint = false;
    
    /**
     * Instantiates a new SensorDataVisualizer.
     */
    public SensorDataVisualizer(ViewContainer view) {
    	this.view = view;
    	
        proximityVisualizer = new ProximityVisualizer(center, radius, 0.5f, 45f, 90f, 3);
        crashVisualizer = new CrashVisualizer(center, radius, 180f, 180f);
        surfaceColorVisualizer = new SurfaceColorVisualizer(center, radius);
        
        setProximity(1);
        setCrashed(true);
        setSurfaceColor(IntColor.BLACK);
    }
    
    /**
     * Gets the visualized proximity.
     *
     * @return the visualized proximity
     */
    public double getProximity() {
        return proximityVisualizer.getProximity();
    }

    /**
     * Sets the proximity value to visualize.
     *
     * @param proximity double which is normalized 
     * (value grater than 1 becomes 1 and the value lower then 0 becomes 0) 
     * to [0,1], where 0 means that nearest object is far and 1 means that it's very near
     */
    public void setProximity(double proximity) {
        if (proximity < 0) {
            proximity = 0;
        } else if (proximity > 1) {
            proximity = 1;
        }
        this.proximityVisualizer.setProximity(proximity);
        updateView();
    }

    /**
     * Determines whether the CrashVisualizer is visualizing crash or not.
     *
     * @return <code>true</code> if CrashVisualizer is visualizing crash, <code>false</code> otherwise
     */
    public boolean getCrashed() {
        return crashVisualizer.getCrashed();
    }

    /**
     * Sets whether the inner CrashVisualizer is visualizing crash or not.
     *
     * @param crashed new inner CrashVisualizer's visualization state  
     */
    public void setCrashed(boolean crashed) {
        crashVisualizer.setCrashed(crashed);
        updateView();
    }

    /**
     * Gets the fill color of the inner SurfaceColorVisualizer.
     *
     * @return the fill color of the inner SurfaceColorVisualizer
     */
    public IntColor getSurfaceColor() {
        return surfaceColorVisualizer.getColor();
    }

    /**
     * Sets the fill color of the inner SurfaceColorVisualizer.
     *
     * @param surfaceColor the fill color of the SurfaceColorVisualizer
     */
    public void setSurfaceColor(IntColor surfaceColor) {
        surfaceColorVisualizer.setColor(surfaceColor);
        updateView();
    }
    
    /**
     * Determines whether repaint happens automatically or not.
     *
     * @return autoRepaint state
     */
    public boolean getAutoRepaint() {
        return autoRepaint;
    }
    
    /**
     * Sets whether repaint happens automatically or not.
     *
     * @param autoRepaint the new auto repaint
     */
    public void setAutoRepaint(boolean autoRepaint) {
        this.autoRepaint = autoRepaint;
    }
    
    /**
     * Turns SensorDataVisualizer to its init state (bound doesn't change).
     */
    public void reset() {
        boolean autoRepaintCancelled = autoRepaint;
        autoRepaint = false;
        
        setProximity(0);
        setCrashed(false);
        setSurfaceColor(IntColor.BLACK);
        
        autoRepaint = autoRepaintCancelled;
        updateView();
    }
    
    /**
     * Updates model.
     */
    public void updateModel() {
        Point center = new Point(view.getWidth() * 0.5f, view.getHeight() * 0.5625f);
        float radius = (float) Math.min(view.getWidth() * 0.5, view.getHeight() * 0.5);
        if (center.equals(this.center) && radius == this.radius) {
            return;
        }
        this.center = center;
        this.radius = radius;
        proximityVisualizer.updateModel(center, radius);
        crashVisualizer.updateModel(center, radius * 0.75f);
        surfaceColorVisualizer.updateModel(center, radius * 0.25f);
    }
    
    /**
     * Updates view.
     */
    private void updateView() {
        if (autoRepaint) {
            view.repaint();
        }
    }
    
    public void paint(Graphics g) {
        IntColor background = view.getBackgroundColor();
        proximityVisualizer.paint(g, background);
        crashVisualizer.paint(g, background);
        surfaceColorVisualizer.paint(g);
    }    
}
