package cz.vabalcar.rcjbotcontroller.visualizer.processing.ev3;

import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.SensorDataProcessor;

public class EV3SensorDataProcessor extends SensorDataProcessor {

	public EV3SensorDataProcessor(SensorDataVisualizer visualizer, boolean resetVisualizer) {
		super(visualizer, resetVisualizer);
		addReader(new EV3ColorSensorDataReader());
		addReader(new EV3IRSensorDataReader());
		addReader(new EV3TouchSensorDataReader());
	}

}
