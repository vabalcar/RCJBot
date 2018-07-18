package cz.vabalcar.jbot.moving;

/**
 * The Class AngularAccelerationChange.
 */
public class AngularAccelerationChange implements MovementProcessorChange {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
	
	/** The angular acceleration. */
	private final double angularAcceleration;
	
	/**
	 * Instantiates a new angular acceleration change.
	 *
	 * @param angularAcceleration the angular acceleration
	 */
	public AngularAccelerationChange(double angularAcceleration) {
		this.angularAcceleration = angularAcceleration;
	}
	
	/**
	 * Gets the angular acceleration.
	 *
	 * @return the angular acceleration
	 */
	public double getAngularAcceleration() {
		return angularAcceleration;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessorChange#accept(cz.vabalcar.jbot.moving.MovementProcessor)
	 */
	@Override
	public void accept(MovementProcessor movementProcessor) throws UnsupportedMovementProcessorActionException {
		movementProcessor.apply(this);
	}
}
