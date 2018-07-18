package cz.vabalcar.jbot.moving;

import cz.vabalcar.jbot.events.DataProviderInfo;
import cz.vabalcar.jbot.events.SimpleDataListener;

/**
 * The listener interface for receiving movementProcessorAction events.
 * The class that is interested in processing a movementProcessorAction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMovementProcessorActionListener</code> method. When
 * the movementProcessorAction event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class MovementProcessorActionListener extends SimpleDataListener<MovementProcessorAction> {
    
    /** The movement processor. */
    private final MovementProcessor movementProcessor;

    /**
     * Instantiates a new movement processor action listener.
     *
     * @param movementProcessor the movement processor
     */
    public MovementProcessorActionListener(MovementProcessor movementProcessor) {
        super(MovementProcessorAction.class);
        this.movementProcessor = movementProcessor;
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.SimpleDataListener#dataReceived(cz.vabalcar.jbot.events.DataProviderInfo, java.lang.Object)
     */
    @Override
    public <U extends MovementProcessorAction> boolean dataReceived(DataProviderInfo<? extends MovementProcessorAction> source, U movementProcessorAction) {
        try {
            movementProcessor.process(movementProcessorAction);
            return true;
        } catch (UnsupportedMovementProcessorActionException e) {
            return false;
        }
    }

}
