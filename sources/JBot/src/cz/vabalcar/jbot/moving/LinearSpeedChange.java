package cz.vabalcar.jbot.moving;

/**
 * The Class LinearSpeedChange.
 */
public class LinearSpeedChange implements MovementProcessorChange {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/** The linear speed. */
	private final double linearSpeed;
	
	/**
	 * Instantiates a new linear speed change.
	 *
	 * @param linearSpeed the linear speed
	 */
	public LinearSpeedChange(double linearSpeed) {
		this.linearSpeed = linearSpeed;
	}
	
	/**
	 * Gets the linear speed.
	 *
	 * @return the linear speed
	 */
	public double getLinearSpeed() {
		return linearSpeed;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessorChange#accept(cz.vabalcar.jbot.moving.MovementProcessor)
	 */
	@Override
	public void accept(MovementProcessor movementProcessor) throws UnsupportedMovementProcessorActionException {
		movementProcessor.apply(this);
	}
}
