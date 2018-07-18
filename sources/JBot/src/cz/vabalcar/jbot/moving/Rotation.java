package cz.vabalcar.jbot.moving;

/**
 * The Class Rotation.
 */
public class Rotation implements Movement {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
	/** The definite. */
	private boolean definite = true;
	
	/** The angle. */
	private double angle;
	
	/** The direction. */
	private Direction direction;
	
	/**
	 * Instantiates a new rotation.
	 *
	 * @param direction the direction
	 */
	public Rotation(Direction direction) {
		setDirection(direction);
		definite = false;
	}
	
	/**
	 * Instantiates a new rotation.
	 *
	 * @param direction the direction
	 * @param angle the angle
	 */
	public Rotation(Direction direction, double angle) {
		setDirection(direction);
		this.angle = angle;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.Movement#isDefinite()
	 */
	@Override
	public boolean isDefinite() {
		return definite;
	}
	
	/**
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Checks if is clockwise.
	 *
	 * @return true, if is clockwise
	 */
	public boolean isClockwise() {
		return direction == Direction.LEFT;
	}
	
	/**
	 * Sets the direction.
	 *
	 * @param direction the new direction
	 */
	private void setDirection(Direction direction) {
		if (!isRotationDirection(direction)) {
			throw new IllegalArgumentException();
		}
		this.direction = direction;
	}
	
	/**
	 * Checks if is rotation direction.
	 *
	 * @param direction the direction
	 * @return true, if is rotation direction
	 */
	private boolean isRotationDirection(Direction direction) {
		return direction == Direction.LEFT || direction == Direction.RIGHT;
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
		return sb.append("definite ")
				.append(direction)
				.append(" rotation")
				.toString();
	}
}
