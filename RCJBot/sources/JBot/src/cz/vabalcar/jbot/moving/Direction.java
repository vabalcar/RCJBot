package cz.vabalcar.jbot.moving;

/**
 * The Enum Direction.
 */
public enum Direction {
	
	/** The forward. */
	FORWARD("forward"),
	
	/** The backward. */
	BACKWARD("backward"),
	
	/** The left. */
	LEFT("left"),
	
	/** The right. */
	RIGHT("right");
	
	/** The name. */
	private final String name;
	
	/** The inverse. */
	private Direction inverse;
	
	static {
	    FORWARD.inverse = BACKWARD;
	    BACKWARD.inverse = FORWARD;
	    LEFT.inverse = RIGHT;
	    RIGHT.inverse = LEFT;
	}
	
	/**
	 * Instantiates a new direction.
	 *
	 * @param name the name
	 */
	private Direction(String name) {
		this.name = name;
	}
	
	/**
	 * Invert.
	 *
	 * @return the direction
	 */
	public Direction invert() {
	    return inverse;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
