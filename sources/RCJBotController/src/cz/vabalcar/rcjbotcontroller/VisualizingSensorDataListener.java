package cz.vabalcar.rcjbotcontroller;

import java.awt.Color;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataListenerImpl;
import cz.vabalcar.util.FloatArray;

/**
 * This class is pusher of read data from a DataProvider and if the data are read from 
 * EV3IRSensor, EV3TouchSensor or EV3ColorSensor, the data gets visualized by a SensorDataVisualizer.
 */
public class VisualizingSensorDataListener extends DataListenerImpl<FloatArray> {
    
    /** brown color */
    private static final Color BROWN = new Color(139, 69, 19);
    
    /** max real value read by EV3IRSensor */
    private static final float EV3_IR_SENSOR_MAX_VALUE = 50f;
    
    /** The visualizer. */
    private final SensorDataVisualizer visualizer;

    /**
     * Instantiates a new VisualizingSensorDataListener.
     *
     * @param visualizer the visualizer to work with
     */
    public VisualizingSensorDataListener(SensorDataVisualizer visualizer) {
        super(FloatArray.class);
        this.visualizer = visualizer;
        this.visualizer.setAutoRepaint(true);
        this.visualizer.toNotDataReceivedState();        
    }

    @Override
    public boolean processDataEvent(DataEvent<? extends FloatArray> event) {
    	float[] data = event.getData().getArray();
        switch (event.getSourceName()) {
        case "EV3IRSensor":
            visualizer.setProximity(1f - data[0] / EV3_IR_SENSOR_MAX_VALUE);
            break;
        case "EV3TouchSensor":
            visualizer.setCrashed(data[0] == 1f);
            break;
        case "EV3ColorSensor":
            visualizer.setSurfaceColor(getColor((int)data[0]));
            break;
        default:
            break;
        }
        return true;
    }
    
    /**
     * Gets the color from leJOS color ID.
     *
     * @param lejosColorId the leJOS color ID
     * @return the color represented by the leJOS color ID 
     */
    private Color getColor(int lejosColorId) {
        switch (lejosColorId) {
        case LejosColor.BLACK:
            return Color.BLACK;
        case LejosColor.BLUE:
            return Color.BLUE;
        case LejosColor.BROWN:
            return BROWN;
        case LejosColor.CYAN:
            return Color.CYAN;
        case LejosColor.DARK_GRAY:
            return Color.DARK_GRAY;
        case LejosColor.GRAY:
            return Color.GRAY;
        case LejosColor.GREEN:
            return Color.GREEN;
        case LejosColor.LIGHT_GRAY:
            return Color.LIGHT_GRAY;
        case LejosColor.MAGENTA:
            return Color.MAGENTA;
        case LejosColor.NONE:
            return Color.BLACK;
        case LejosColor.ORANGE:
            return Color.ORANGE;
        case LejosColor.PINK:
            return Color.PINK;
        case LejosColor.RED:
            return Color.RED;
        case LejosColor.WHITE:
            return Color.WHITE;
        case LejosColor.YELLOW:
            return Color.YELLOW;
        default:
            return Color.BLACK;
        }
    }

	@Override
	public void close() {
		// Nothing to do here.
	}

}
