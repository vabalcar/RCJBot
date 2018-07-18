package cz.vabalcar.jbot.moving;

/**
 * The Class ArcTranslation.
 */
public class ArcTranslation implements Movement {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/** The radius. */
	private double radius;
	
	/** The angle. */
	private double angle;
	
	/** The distance. */
	private double distance;
	
	/** The direction. */
	private Direction direction;
	
	/** The angular movement. */
	private final boolean angularMovement;
	
	/** The definite. */
	private final boolean definite;
	
	/**
	 * Instantiates a new arc translation.
	 *
	 * @param radius the radius
	 * @param size the size
	 * @param sizeIsAngle the size is angle
	 */
	public ArcTranslation(double radius, double size, boolean sizeIsAngle) {
		this.radius = radius;
		angularMovement = sizeIsAngle;
		if (sizeIsAngle) {
			this.angle = size;
		} else {
			this.distance = size;
		}
		definite = true;
	}
	
	/**
	 * Instantiates a new arc translation.
	 *
	 * @param direction the direction
	 */
	public ArcTranslation(Direction direction) {
		setDirection(direction);
		angularMovement = false;
		definite = false;
	}
	
	/**
	 * Sets the direction.
	 *
	 * @param direction the new direction
	 */
	private void setDirection(Direction direction) {
		if (!isArcTranslationDirection(direction)) {
			throw new IllegalArgumentException();
		}
		this.direction = direction;
	}
	
	/**
	 * Checks if is arc translation direction.
	 *
	 * @param direction the direction
	 * @return true, if is arc translation direction
	 */
	private boolean isArcTranslationDirection(Direction direction) {
		return direction == Direction.FORWARD || direction == Direction.BACKWARD;
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
	
	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.moving.Movement#isDefinite()
	 */
	@Override
	public boolean isDefinite() {
		return definite;
	}
	
	/**
	 * Checks if is angular movement.
	 *
	 * @return true, if is angular movement
	 */
	public boolean isAngularMovement() {
		return angularMovement;
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
		sb.append("arc translation ");
		if (definite) {
			sb.append(" by ");
			//TODO: add units
			if (angularMovement) {
				sb.append(angle);
			} else {
				sb.append(distance);
			}
		} else {
			sb.append(direction);
		}
		return sb.toString();
	}
}
