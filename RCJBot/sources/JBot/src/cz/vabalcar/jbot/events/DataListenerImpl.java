package cz.vabalcar.jbot.events;

public abstract class DataListenerImpl<T> extends TypeRecogniser<T> implements DataListener<T> {
	
	public DataListenerImpl(Class<T> knownType) {
		super(knownType);
	}
	
	@SuppressWarnings("unchecked")
    public boolean processUnknownDataEvent(DataEvent<?> event) {
        if (knownType.isAssignableFrom(event.getData().getClass())) {
            DataEventImpl<? extends T> discoveredEvent = (DataEventImpl<? extends T>) event;
            return processDataEvent(discoveredEvent);
        }
        return false;
    }
}
