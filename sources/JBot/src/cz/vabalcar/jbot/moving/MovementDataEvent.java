package cz.vabalcar.jbot.moving;

import cz.vabalcar.jbot.events.DataEventImpl;
import cz.vabalcar.jbot.events.DataProviderInfo;

/**
 * The Class MovementDataEvent.
 *
 * @param <T> the generic type
 */
public class MovementDataEvent<T extends MovementProcessorAction> extends DataEventImpl<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new movement data event.
     *
     * @param source the source
     * @param data the data
     */
    public MovementDataEvent(DataProviderInfo<T> source, T data) {
        super(source, data);
    }

}