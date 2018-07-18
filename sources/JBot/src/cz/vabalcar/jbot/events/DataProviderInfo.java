package cz.vabalcar.jbot.events;

import java.io.Serializable;

/**
 * The Interface DataProviderInfo.
 *
 * @param <T> the generic type
 */
public interface DataProviderInfo<T> extends Serializable {
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
}
