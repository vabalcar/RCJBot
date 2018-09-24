package cz.vabalcar.jbot.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class DataProviderImpl<T extends Serializable> extends TypeRecogniser<T> implements DataProvider<T> {
	
	private final List<DataListener<T>> listeners = new ArrayList<>();
	
	private boolean started = false;
	private boolean alive = false;
	
	public DataProviderImpl(Class<T> providedType) {
		super(providedType);
	}
	
	@Override
	public boolean hasStarted() {
		return started;
	}
	
	@Override
	public boolean isAlive() {
		return alive;
	}
	
	@Override
	public Class<T> getProvidedDataType() {
		return getKnownType();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean addListener(DataListener<? extends T> listener) {
		listeners.add((DataListener<T>) listener);
		return true;
	}
	
	@Override
	public boolean removeListener(DataListener<? extends T> listener) {
		return listeners.remove(listener);
	}
	
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
	
	@Override
	public void close() throws Exception {
		alive = false;
	}
}
