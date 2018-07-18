package cz.vabalcar.jbot.events;

import java.io.Serializable;

/**
 * The Class DataEventRaiser.
 *
 * @param <T> the generic type
 */
public class DataEventRaiser<T extends Serializable> extends UnknownDataEventRecogniser<T> {

    /** The data provider. */
    private final DataProvider<T> dataProvider;
    
    /**
     * Instantiates a new data event raiser.
     *
     * @param knownType the known type
     * @param dataProvider the data provider
     */
    public DataEventRaiser(Class<T> knownType, DataProvider<T> dataProvider) {
        super(knownType);
        this.dataProvider = dataProvider;
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataListener#processDataEvent(cz.vabalcar.jbot.events.DataEvent)
     */
    @Override
    public boolean processDataEvent(DataEvent<? extends T> event) {
        return dataProvider.raiseDataEvent(event);
    }

    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() throws Exception {
        dataProvider.close();
    }


}
