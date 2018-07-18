package cz.vabalcar.jbot.moving;

/**
 * The Interface Movement.
 */
public interface Movement extends MovementProcessorAction {
	
	/**
	 * Checks if is definite.
	 *
	 * @return true, if is definite
	 */
	boolean isDefinite();
	
	/**
	 * Accept.
	 *
	 * @param movementProcessor the movement processor
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void accept(MovementProcessorImpl movementProcessor) throws UnsupportedMovementProcessorActionException;
}
