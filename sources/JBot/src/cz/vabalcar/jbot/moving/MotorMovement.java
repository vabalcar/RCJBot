package cz.vabalcar.jbot.moving;

/**
 * The Class MotorMovement.
 */
public class MotorMovement implements Movement {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The target. */
    private final int target;
    
    /** The description. */
    private final Movement description;

    /**
     * Instantiates a new motor movement.
     *
     * @param target the target
     * @param description the description
     */
    public MotorMovement(int target, Movement description) {
        this.target = target;
        this.description = description;
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.Movement#isDefinite()
     */
    @Override
    public boolean isDefinite() {
        return description.isDefinite();
    }
    
    /**
     * Gets the target.
     *
     * @return the target
     */
    public int getTarget() {
        return target;
    }
    
    /**
     * Gets the description.
     *
     * @return the description
     */
    public Movement getDescription() {
        return description;
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
        return new StringBuilder().append("motor ").append(target).append(' ').append(description).toString();
    }
}
