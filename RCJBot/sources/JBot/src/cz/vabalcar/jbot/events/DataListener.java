package cz.vabalcar.jbot.events;

public interface DataListener<T> extends AutoCloseable {
	boolean processUnknownDataEvent(DataEvent<?> event);
	boolean processDataEvent(DataEvent<? extends T> event);
}
