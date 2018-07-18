package cz.vabalcar.jbot;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataProvider;
import cz.vabalcar.jbot.moving.MovementProcessor;
import cz.vabalcar.jbot.sensors.Sensor;

/**
 * The Interface JBot.
 *
 * @param <T> the generic type
 */
public interface JBot<T extends Serializable> extends DataProvider<T>  {
	
	/**
	 * Gets the movement processor.
	 *
	 * @return the movement processor
	 */
	MovementProcessor getMovementProcessor();
	
	/**
	 * Gets the sensor.
	 *
	 * @param <R> the generic type
	 * @param sensor the sensor
	 * @param sensorDataType the sensor data type
	 * @return the sensor
	 */
	<R extends T> Sensor<R> getSensor(String sensor, Class<R> sensorDataType);
}
