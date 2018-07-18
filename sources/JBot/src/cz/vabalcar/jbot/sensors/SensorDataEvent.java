package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataEventImpl;
import cz.vabalcar.jbot.events.DataProviderInfo;

/**
 * The Class SensorDataEvent.
 *
 * @param <T> the generic type
 */
public class SensorDataEvent<T extends Serializable> extends DataEventImpl<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
	/**
	 * Instantiates a new sensor data event.
	 *
	 * @param source the source
	 * @param data the data
	 */
	public SensorDataEvent(DataProviderInfo<T> source, T data) {
		super(source, data);
	}
}
