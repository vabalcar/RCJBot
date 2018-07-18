package cz.vabalcar.jbot.events;

/**
 * The listener interface for receiving data events.
 * The class that is interested in processing a data
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDataListener</code> method. When
 * the data event occurs, that object's appropriate
 * method is invoked.
 *
 * @param <T> the generic type
 */
public interface DataListener<T> extends AutoCloseable {
	
	/**
	 * Process unknown data event.
	 *
	 * @param event the event
	 * @return true, if successful
	 */
	boolean processUnknownDataEvent(DataEvent<?> event);
	
	/**
	 * Process data event.
	 *
	 * @param event the event
	 * @return true, if successful
	 */
	boolean processDataEvent(DataEvent<? extends T> event);
}
