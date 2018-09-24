package cz.vabalcar.rcjbotcontroller.visualizer.processing;

import java.util.HashMap;
import java.util.Map;

import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;

public class SensorDataProcessor {
	
	private final Map<String, SensorDataReader> readers = new HashMap<>();
	private final SensorDataVisualizer visualizer;
	
	public SensorDataProcessor(SensorDataVisualizer visualizer, boolean resetVisualizer) {
		this.visualizer = visualizer;
		this.visualizer.setAutoRepaint(true);
		if (resetVisualizer) {
			this.visualizer.reset();
		}
	}
	
	public void addReader(SensorDataReader reader) {
		reader.setVisualizer(visualizer);
		readers.put(reader.getTargetSensor(), reader);
	}
	
	public void process(String sourceSensor, float[] data) {
		SensorDataReader reader = readers.get(sourceSensor);
		if (reader != null) {
			reader.read(data);
		}
	}

}
