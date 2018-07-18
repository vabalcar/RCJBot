package cz.vabalcar.rcjbotcontroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

/**
 * This class provides visualizer of data received from the RemoteJBot. It's Swing compatible 
 * visualisable Java Bean and can be treated just like any other GUI element from Swing, for instance
 * it can be used in Eclipse's WindowBuilder to create GUI containing the SensorDataVisualizer easily.
 */
public class SensorDataVisualizer extends JPanel {

    /** the constant serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /** the inner ProximityVisualizer */
    private final ProximityVisualizer proximityVisualizer;
    
    /** the inner CrashVisualizer */
    private final CrashVisualizer crashVisualizer;
    
    /** the inner SurfaceColorVisualizer */
    private final SurfaceColorVisualizer surfaceColorVisualizer;
    
    /** the center of the SensorDataVisualizer */
    private Point2D center = new Point2D.Double();
    
    /** the radius of the SensorDataVisualizer */
    private double radius = 0;
    
    /** the boolean which stores whether repaint happens automatically after change of visualized data or not */
    private boolean autoRepaint = false;
    
    /**
     * Instantiates a new SensorDataVisualizer.
     */
    public SensorDataVisualizer() {
        proximityVisualizer = new ProximityVisualizer(center, radius, 0.5, 45, 90, 3);
        crashVisualizer = new CrashVisualizer(center, radius, 180, 180);
        surfaceColorVisualizer = new SurfaceColorVisualizer(center, radius);
        
        setProximity(1);
        setCrashed(true);
        setSurfaceColor(Color.BLACK);
    }
    
    /**
     * @see java.awt.Component#setBounds(int, int, int, int)
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        updateModel();
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
    public Color getSurfaceColor() {
        return surfaceColorVisualizer.getColor();
    }

    /**
     * Sets the fill color of the inner SurfaceColorVisualizer.
     *
     * @param surfaceColor the fill color of the SurfaceColorVisualizer
     */
    public void setSurfaceColor(Color surfaceColor) {
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
    public void toNotDataReceivedState() {
        boolean autoRepaintCancelled = false;
        if (autoRepaint) {
            autoRepaint = false;
            autoRepaintCancelled = true;
        }
        
        setProximity(0);
        setCrashed(false);
        setSurfaceColor(Color.BLACK);
        
        if (autoRepaintCancelled) {
            repaint();
            autoRepaint = true;
        }
    }
    
    /**
     * Updates model.
     */
    private void updateModel() {
        Point2D center = new Point2D.Double(getSize().width * 0.5, getSize().height * 0.5625);
        double radius = Math.min(getSize().width * 0.5, getSize().height * 0.5);
        if (center.equals(this.center) && radius == this.radius) {
            return;
        }
        this.center = center;
        this.radius = radius;
        proximityVisualizer.update(center, radius);
        crashVisualizer.update(center, radius * 0.75);
        surfaceColorVisualizer.update(center, radius * 0.25);
    }
    
    /**
     * Updates view.
     */
    private void updateView() {
        if (autoRepaint) {
            repaint();
        }
    }
    
    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        Color background = getBackground();
        proximityVisualizer.paint(g, background);
        crashVisualizer.pain(g, background);
        surfaceColorVisualizer.paint(g);
    }    
}
