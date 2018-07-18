package cz.vabalcar.jbot.sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * The Class SensorScheduler.
 */
public class SensorScheduler implements AutoCloseable {
	
	/** The thread pool. */
	private ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
	
	/** The sensor readings. */
	private List<SensorReading> sensorReadings = new ArrayList<>();
	
	/**
	 * Adds the sensor.
	 *
	 * @param sensor the sensor
	 */
	public void addSensor(Sensor<?> sensor) {
		sensorReadings.add(new SensorReading(sensor));
	}
	
	/**
	 * Removes the sensor reading.
	 *
	 * @param sensor the sensor
	 */
	public void removeSensorReading(SensorImpl<?> sensor) {
		for(int i = 0; i < sensorReadings.size(); i++) {
			if (sensorReadings.get(i).isProcessedOnSensor(sensor)) {
				sensorReadings.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Schedule sensor reading.
	 *
	 * @param sensorReading the sensor reading
	 */
	private void scheduleSensorReading(SensorReading sensorReading) {
	    threadPool.scheduleAtFixedRate(sensorReading, SensorReading.INITIAL_DELAY_OF_READING, sensorReading.getPeriodValue(), SensorReading.PERIOD_UNIT);
	}
	
	/**
	 * Start.
	 */
	public void start() {
		for(SensorReading sensorReading : sensorReadings) {
			scheduleSensorReading(sensorReading);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	public void close() {
		threadPool.shutdownNow();
	}
}
