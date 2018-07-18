package cz.vabalcar.jbot.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class DataProviderImpl.
 *
 * @param <T> the generic type
 */
public abstract class DataProviderImpl<T extends Serializable> extends TypeRecogniser<T> implements DataProvider<T> {
	
	/** The started. */
	private boolean started = false;
	
	/** The alive. */
	private boolean alive = false;
	
	/** The listeners. */
	private List<DataListener<T>> listeners = new ArrayList<>();
	
	/**
	 * Instantiates a new data provider impl.
	 *
	 * @param providedType the provided type
	 */
	public DataProviderImpl(Class<T> providedType) {
		super(providedType);
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProvider#hasStarted()
	 */
	@Override
	public boolean hasStarted() {
		return started;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProvider#isAlive()
	 */
	@Override
	public boolean isAlive() {
		return alive;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProvider#getProvidedDataType()
	 */
	@Override
	public Class<T> getProvidedDataType() {
		return getKnownType();
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProvider#addListener(cz.vabalcar.jbot.events.DataListener)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean addListener(DataListener<? extends T> listener) {
		listeners.add((DataListener<T>) listener);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProvider#removeListener(cz.vabalcar.jbot.events.DataListener)
	 */
	@Override
	public boolean removeListener(DataListener<? extends T> listener) {
		return listeners.remove(listener);
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProvider#raiseDataEvent(cz.vabalcar.jbot.events.DataEvent)
	 */
	public boolean raiseDataEvent(DataEvent<? extends T> event) {
		if (!started) {
			started = true;
			alive = true;
		}
		boolean cancelled = false;
		for(DataListener<T> listener : listeners) {
		    cancelled = !listener.processDataEvent(event);
		    if (cancelled) {
				break;
			}
		}
		return cancelled;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		alive = false;
		raiseDataEvent(new DataProviderDeath<>(getInfo()));
	}
}
