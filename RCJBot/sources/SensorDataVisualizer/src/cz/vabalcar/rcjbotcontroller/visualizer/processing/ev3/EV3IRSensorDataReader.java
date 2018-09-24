package cz.vabalcar.rcjbotcontroller.visualizer.processing.ev3;

import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.SensorDataReader;

public class EV3IRSensorDataReader implements SensorDataReader {
	
	private static final String TARGET_SENSOR = "EV3IRSensor";
	
	/** max real value read by EV3IRSensor */
    private static final float EV3_IR_SENSOR_MAX_VALUE = 50f;
	
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
		visualizer.setProximity(1f - data[0] / EV3_IR_SENSOR_MAX_VALUE);
	}

}
