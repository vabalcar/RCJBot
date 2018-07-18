package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataProvider;

/**
 * The Interface Sensor.
 *
 * @param <T> the generic type
 */
public interface Sensor<T extends Serializable> extends DataProvider<T> {
	
	/**
	 * Checks if is repeating set.
	 *
	 * @return true, if is repeating set
	 */
	boolean isRepeatingSet();
    
    /**
     * Gets the period.
     *
     * @return the period
     */
    int getPeriod();
	
	/**
	 * Initialize.
	 */
	void initialize();
	
	/**
	 * Read data.
	 *
	 * @return the t
	 */
	T readData();
	
	/**
	 * Update.
	 */
	void update();
}
