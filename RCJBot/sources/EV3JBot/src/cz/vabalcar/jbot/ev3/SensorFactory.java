package cz.vabalcar.jbot.ev3;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;

/**
 * A factory for creating Sensor objects.
 */
public class SensorFactory {
	
	/** The instance. */
	private static SensorFactory instance;
	
	/**
	 * Instance.
	 *
	 * @return the sensor factory
	 */
	public static SensorFactory instance() {
		if (instance == null) {
			instance = new SensorFactory();
		}
		return instance;
	}
	
	/**
	 * Instantiates a new sensor factory.
	 */
	private SensorFactory() {
	}
	
	/**
	 * Creates the.
	 *
	 * @param sensorConfiguration the sensor configuration
	 * @return the base sensor
	 */
	public BaseSensor create(SensorConfiguration sensorConfiguration) {
		BaseSensor sensor;
		try {
			sensor = (BaseSensor) sensorConfiguration.getSensorType().getConstructor(Port.class).newInstance(sensorConfiguration.getSensorPort());
			sensor.setCurrentMode(sensorConfiguration.getSensorMode());
			return sensor;
		} catch (Exception e) {
			return null;
		}
	}
}
