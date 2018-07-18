package cz.vabalcar.jbot.moving;

/**
 * The Interface MovementProcessorChange.
 */
public interface MovementProcessorChange extends MovementProcessorAction {
	
	/**
	 * Accept.
	 *
	 * @param movementProcessor the movement processor
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void accept(MovementProcessor movementProcessor) throws UnsupportedMovementProcessorActionException;
}
