package cz.vabalcar.jbot.moving;

/**
 * The Class MovementProcessorImpl.
 */
public abstract class MovementProcessorImpl implements MovementProcessor {
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.MovementProcessorAction)
	 */
	@Override
	public void process(MovementProcessorAction movementProcessorAction) throws UnsupportedMovementProcessorActionException {
	    if (movementProcessorAction instanceof Movement) {
	        process((Movement) movementProcessorAction);
	    } else if (movementProcessorAction instanceof MovementProcessorChange) {
	        apply((MovementProcessorChange) movementProcessorAction);
	    }
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.Movement)
	 */
	@Override
	public void process(Movement movement) throws UnsupportedMovementProcessorActionException {
		movement.accept(this);
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.MovementProcessorChange)
	 */
	@Override
	public void apply(MovementProcessorChange change) throws UnsupportedMovementProcessorActionException {
		change.accept(this);
	}
}
