package cz.vabalcar.jbot.events;

import java.io.Serializable;

/**
 * The Interface DataProvider.
 *
 * @param <T> the generic type
 */
public interface DataProvider<T extends Serializable> extends AutoCloseable {
	
	/**
	 * Checks for started.
	 *
	 * @return true, if successful
	 */
	boolean hasStarted();
	
	/**
	 * Checks if is alive.
	 *
	 * @return true, if is alive
	 */
	boolean isAlive();
	
	/**
	 * Gets the provided data type.
	 *
	 * @return the provided data type
	 */
	Class<T> getProvidedDataType();
	
	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	DataProviderInfo<T> getInfo();
	
	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 * @return true, if successful
	 */
	boolean addListener(DataListener<? extends T> listener);
	
	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 * @return true, if successful
	 */
	boolean removeListener(DataListener<? extends T> listener);
	
	/**
	 * Raise data event.
	 *
	 * @param event the event
	 * @return true, if successful
	 */
	boolean raiseDataEvent(DataEvent<? extends T> event);
}
