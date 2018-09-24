package cz.vabalcar.jbot.ev3;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;

/**
 * The Class SensorConfiguration.
 */
public class SensorConfiguration {	
	
	/** The sensor type. */
	private final Class<? extends BaseSensor> sensorType;
	
	/** The sensor port. */
	private final Port sensorPort;
	
	/** The sensor mode. */
	private final String sensorMode;
	
	/** The reading frequency. */
	private final int readingFrequency;
	
	/**
	 * Instantiates a new sensor configuration.
	 *
	 * @param sensorType the sensor type
	 * @param sensorMode the sensor mode
	 * @param sensorPort the sensor port
	 * @param readingFrequency the reading frequency
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public SensorConfiguration(Class<? extends BaseSensor> sensorType, String sensorMode, Port sensorPort, int readingFrequency) throws IllegalArgumentException {
		this.sensorType = sensorType;
		this.sensorMode = sensorMode;
		this.sensorPort = sensorPort;
		this.readingFrequency = readingFrequency;
	}
	
	/**
	 * Gets the sensor type.
	 *
	 * @return the sensor type
	 */
	public Class<? extends BaseSensor> getSensorType() {
		return sensorType;
	}
	
	/**
	 * Gets the sensor port.
	 *
	 * @return the sensor port
	 */
	public Port getSensorPort() {
		return sensorPort;
	}
	
	/**
	 * Gets the sensor mode.
	 *
	 * @return the sensor mode
	 */
	public String getSensorMode() {
		return sensorMode;
	}
	
	/**
	 * Gets the reading frequency.
	 *
	 * @return the reading frequency
	 */
	public int getReadingFrequency() {
        return readingFrequency;
    }
}
