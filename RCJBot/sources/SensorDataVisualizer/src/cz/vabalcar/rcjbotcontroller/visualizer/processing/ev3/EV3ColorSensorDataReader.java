package cz.vabalcar.rcjbotcontroller.visualizer.processing.ev3;

import cz.vabalcar.rcjbotcontroller.visualizer.IntColor;
import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.SensorDataReader;

public class EV3ColorSensorDataReader implements SensorDataReader {
	
	private static final String TARGET_SENSOR = "EV3ColorSensor";
	
	/** brown color */
    private static final IntColor BROWN = new IntColor(139, 69, 19);
	
	private SensorDataVisualizer visualizer;

	@Override
	public String getTargetSensor() {
		return TARGET_SENSOR;
	}

	@Override
	public void setVisualizer(SensorDataVisualizer visualizer) {
		this.visualizer = visualizer;
	}

	@Override
	public void read(float[] data) {
		visualizer.setSurfaceColor(toColor((int) data[0]));
	}

	/**
     * Gets the color from leJOS color ID.
     *
     * @param lejosColorId the leJOS color ID
     * @return the color represented by the leJOS color ID 
     */
    private IntColor toColor(int lejosColorId) {
        switch (lejosColorId) {
        case EV3Color.BLACK:
            return IntColor.BLACK;
        case EV3Color.BLUE:
            return IntColor.BLUE;
        case EV3Color.BROWN:
            return BROWN;
        case EV3Color.CYAN:
            return IntColor.CYAN;
        case EV3Color.DARK_GRAY:
            return IntColor.DARK_GRAY;
        case EV3Color.GRAY:
            return IntColor.GRAY;
        case EV3Color.GREEN:
            return IntColor.GREEN;
        case EV3Color.LIGHT_GRAY:
            return IntColor.LIGHT_GRAY;
        case EV3Color.MAGENTA:
            return IntColor.MAGENTA;
        case EV3Color.NONE:
            return IntColor.BLACK;
        case EV3Color.ORANGE:
            return IntColor.ORANGE;
        case EV3Color.PINK:
            return IntColor.PINK;
        case EV3Color.RED:
            return IntColor.RED;
        case EV3Color.WHITE:
            return IntColor.WHITE;
        case EV3Color.YELLOW:
            return IntColor.YELLOW;
        default:
            return IntColor.BLACK;
        }
    }
	
}
