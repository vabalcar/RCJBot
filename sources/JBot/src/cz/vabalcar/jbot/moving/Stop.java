package cz.vabalcar.jbot.moving;

/**
 * The Class Stop.
 */
public class Stop implements Movement {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.Movement#isDefinite()
     */
    @Override
	public boolean isDefinite() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.Movement#accept(cz.vabalcar.jbot.moving.MovementProcessorImpl)
	 */
	@Override
	public void accept(MovementProcessorImpl movementProcessor) throws UnsupportedMovementProcessorActionException {
		movementProcessor.process(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "stop";
	}
}
