package cz.vabalcar.rcjbotcontroller.visualizer.processing;

import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;

public interface SensorDataReader {
	String getTargetSensor();
	void setVisualizer(SensorDataVisualizer visualizer);
	void read(float[] data);
}
