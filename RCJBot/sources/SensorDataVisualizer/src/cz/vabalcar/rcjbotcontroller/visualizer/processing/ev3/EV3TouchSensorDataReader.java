package cz.vabalcar.rcjbotcontroller.visualizer.processing.ev3;

import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.SensorDataReader;

public class EV3TouchSensorDataReader implements SensorDataReader {
	
	private static final String TARGET_SENSOR = "EV3TouchSensor";
	
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
		visualizer.setCrashed(data[0] == 1f);
	}

}
