package cz.vabalcar.jbot.moving;

/**
 * The Class UnsupportedMovementProcessorActionException.
 */
public class UnsupportedMovementException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new unsupported movement processor action exception.
     *
     * @param action the action
     */
    public UnsupportedMovementException(Movement action) {
        super(action + " is not supported");
    }
}
