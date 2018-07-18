package cz.vabalcar.jbot.moving;

/**
 * The Class LinearAccelerationChange.
 */
public class LinearAccelerationChange implements MovementProcessorChange {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/** The linear acceleration. */
	private final double linearAcceleration;
	
	/**
	 * Instantiates a new linear acceleration change.
	 *
	 * @param linearAcceleration the linear acceleration
	 */
	public LinearAccelerationChange(double linearAcceleration) {
		this.linearAcceleration = linearAcceleration;
	}
	
	/**
	 * Gets the linear acceleration.
	 *
	 * @return the linear acceleration
	 */
	public double getLinearAcceleration() {
		return linearAcceleration;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessorChange#accept(cz.vabalcar.jbot.moving.MovementProcessor)
	 */
	@Override
	public void accept(MovementProcessor movementProcessor) throws UnsupportedMovementProcessorActionException {
		movementProcessor.apply(this);
	}
}
