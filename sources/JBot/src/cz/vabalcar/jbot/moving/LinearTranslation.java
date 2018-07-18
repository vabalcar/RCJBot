package cz.vabalcar.jbot.moving;

/**
 * The Class LinearTranslation.
 */
public class LinearTranslation implements Movement {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
	
	/** The definite. */
	private boolean definite = false;
	
	/** The direction. */
	private Direction direction;
	
	/** The distance. */
	private double distance;
	
	/**
	 * Instantiates a new linear translation.
	 *
	 * @param direction the direction
	 */
	public LinearTranslation(Direction direction) {
		setDirection(direction);
		definite = false;
	}
	
	/**
	 * Instantiates a new linear translation.
	 *
	 * @param direction the direction
	 * @param distance the distance
	 */
	public LinearTranslation(Direction direction, double distance) {
		setDirection(direction);
		this.distance = distance;
		definite = true;
	}
	
	/**
	 * Sets the direction.
	 *
	 * @param direction the new direction
	 */
	private void setDirection(Direction direction) {
		if (!isLinearTranslationDirection(direction)) {
			throw new IllegalArgumentException();
		}
		this.direction = direction;
	}
	
	/**
	 * Checks if is linear translation direction.
	 *
	 * @param direction the direction
	 * @return true, if is linear translation direction
	 */
	private boolean isLinearTranslationDirection(Direction direction) {
		return direction == Direction.FORWARD || direction == Direction.BACKWARD;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.Movement#isDefinite()
	 */
	@Override
	public boolean isDefinite() {
		return definite;
	}
	
	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
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
		StringBuilder sb = new StringBuilder();
		if (!definite) {
			sb.append("in");
		}
		sb.append("definite linear traslation ")
			.append(direction);
		if (definite) {
			sb.append(" by ")
				.append(distance);
		}
		return sb.toString();
	}
}
