package cz.vabalcar.jbot.events;

/**
 * The Class UnknownDataEventRecogniser.
 *
 * @param <T> the generic type
 */
public abstract class UnknownDataEventRecogniser<T> extends TypeRecogniser<T> implements DataListener<T> {
    
    /**
     * Instantiates a new unknown data event recogniser.
     *
     * @param knownType the known type
     */
    public UnknownDataEventRecogniser(Class<T> knownType) {
        super(knownType);
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataListener#processUnknownDataEvent(cz.vabalcar.jbot.events.DataEvent)
     */
    @SuppressWarnings("unchecked")
    public boolean processUnknownDataEvent(DataEvent<?> event) {
        if (knownType.isAssignableFrom(event.getData().getClass())) {
            DataEventImpl<? extends T> discoveredEvent = (DataEventImpl<? extends T>) event;
            return processDataEvent(discoveredEvent);
        }
        return false;
    }
}
