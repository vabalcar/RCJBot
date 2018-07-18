package cz.vabalcar.jbot.events;

import java.io.Serializable;

/**
 * The Interface DataEvent.
 *
 * @param <T> the generic type
 */
public interface DataEvent<T extends Serializable> extends Serializable {
	
	/**
	 * Gets the source info.
	 *
	 * @return the source info
	 */
	DataProviderInfo<T> getSourceInfo();
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	T getData();
}
