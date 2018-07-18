package cz.vabalcar.jbot.moving;

/**
 * The Class MinRadiusChange.
 */
public class MinRadiusChange implements MovementProcessorChange {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/** The min radius. */
	private final double minRadius;
	
	/**
	 * Instantiates a new min radius change.
	 *
	 * @param minRadius the min radius
	 */
	public MinRadiusChange(double minRadius) {
		this.minRadius = minRadius;
	}
	
	/**
	 * Gets the min radius.
	 *
	 * @return the min radius
	 */
	public double getMinRadius() {
		return minRadius;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.MovementProcessorChange#accept(cz.vabalcar.jbot.moving.MovementProcessor)
	 */
	@Override
	public void accept(MovementProcessor movementProcessor) throws UnsupportedMovementProcessorActionException {
		movementProcessor.apply(this);
	}
}
