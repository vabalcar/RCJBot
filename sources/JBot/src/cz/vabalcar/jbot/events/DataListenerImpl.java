package cz.vabalcar.jbot.events;

/**
 * The Class DataListenerImpl.
 *
 * @param <T> the generic type
 */
public abstract class DataListenerImpl<T> extends UnknownDataEventRecogniser<T> {
	
	/**
	 * Instantiates a new data listener impl.
	 *
	 * @param knownType the known type
	 */
	public DataListenerImpl(Class<T> knownType) {
		super(knownType);
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataListener#processDataEvent(cz.vabalcar.jbot.events.DataEvent)
	 */
	@Override
	public boolean processDataEvent(DataEvent<? extends T> event) {
		if (event instanceof DataProviderDeath<?>) {
			dataProviderDied();
			return true;
		} else {
			return dataReceived(event.getSourceInfo(), event.getData());
		}
	}
	
	/**
	 * Data received.
	 *
	 * @param <U> the generic type
	 * @param source the source
	 * @param data the data
	 * @return true, if successful
	 */
	public abstract <U extends T> boolean dataReceived(DataProviderInfo<? extends T> source, U data);
	
	/**
	 * Data provider died.
	 */
	public abstract void dataProviderDied();
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	public abstract void close() throws Exception;
}
