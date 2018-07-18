package cz.vabalcar.jbot.sensors;

import java.util.concurrent.TimeUnit;

/**
 * The Class SensorReading.
 */
public class SensorReading implements Runnable {
	
	/** The sensor. */
	private Sensor<?> sensor;
	
	/** The Constant INITIAL_DELAY_OF_READING. */
	public static final int INITIAL_DELAY_OF_READING = 0;
	
	/** The Constant PERIOD_UNIT. */
	public static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;
	
	/**
	 * Instantiates a new sensor reading.
	 *
	 * @param sensor the sensor
	 */
	public SensorReading(Sensor<?> sensor) {
		this.sensor = sensor;
	}
	
	/**
	 * Gets the period value.
	 *
	 * @return the period value
	 */
	public long getPeriodValue() {
		return sensor.getPeriod();
	}
	
	/**
	 * Checks if is processed on sensor.
	 *
	 * @param sensor the sensor
	 * @return true, if is processed on sensor
	 */
	public boolean isProcessedOnSensor(Sensor<?> sensor) {
		return this.sensor == sensor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	    sensor.update();
	}
}
