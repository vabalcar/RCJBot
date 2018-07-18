package cz.vabalcar.jbot.moving;

/**
 * The Class UnsupportedMovementProcessorActionException.
 */
public class UnsupportedMovementProcessorActionException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new unsupported movement processor action exception.
     *
     * @param action the action
     */
    public UnsupportedMovementProcessorActionException(MovementProcessorAction action) {
        super(action + " is not supported");
    }
}
