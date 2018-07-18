package cz.vabalcar.jbot.events;

/**
 * The listener interface for receiving simpleData events.
 * The class that is interested in processing a simpleData
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSimpleDataListener</code> method. When
 * the simpleData event occurs, that object's appropriate
 * method is invoked.
 *
 * @param <T> the generic type
 */
public abstract class SimpleDataListener<T> extends DataListenerImpl<T> {

    /**
     * Instantiates a new simple data listener.
     *
     * @param knownType the known type
     */
    public SimpleDataListener(Class<T> knownType) {
        super(knownType);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataListenerImpl#dataReceived(cz.vabalcar.jbot.events.DataProviderInfo, java.lang.Object)
     */
    @Override
    public abstract <U extends T> boolean dataReceived(DataProviderInfo<? extends T> source, U data);

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataListenerImpl#dataProviderDied()
     */
    @Override
    public void dataProviderDied() {
        // Nothing to do here
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataListenerImpl#close()
     */
    @Override
    public void close() throws Exception {
        // Nothing to do here
    }

}
