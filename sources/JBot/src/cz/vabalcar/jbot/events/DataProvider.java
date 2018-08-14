package cz.vabalcar.jbot.events;

import java.io.Serializable;

public interface DataProvider<T extends Serializable> extends AutoCloseable {
	boolean hasStarted();
	boolean isAlive();
	Class<T> getProvidedDataType();
	String getName();
	boolean addListener(DataListener<? extends T> listener);
	boolean removeListener(DataListener<? extends T> listener);
	boolean raiseDataEvent(DataEvent<? extends T> event);
}
