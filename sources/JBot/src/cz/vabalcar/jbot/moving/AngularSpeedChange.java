package cz.vabalcar.jbot.moving;

/**
 * The Class AngularSpeedChange.
 */
public class AngularSpeedChange implements MovementProcessorChange {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/** The angular speed. */
	private final double angularSpeed;
	
	/**
	 * Instantiates a new angular speed change.
	 *
	 * @param angularSpeed the angular speed
	 */
	public AngularSpeedChange(double angularSpeed) {
		this.angularSpeed = angularSpeed;
	}
	
	/**
	 * Gets the angular speed.
	 *
	 * @return the angular speed
	 */
	public double getAngularSpeed() {
		return angularSpeed;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessorChange#accept(cz.vabalcar.jbot.moving.MovementProcessor)
	 */
	@Override
	public void accept(MovementProcessor movementProcessor) throws UnsupportedMovementProcessorActionException {
		movementProcessor.apply(this);
	}

}
