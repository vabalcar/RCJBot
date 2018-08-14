package cz.vabalcar.jbot.moving;

public class MotorMovement implements Movement {
	private static final long serialVersionUID = 1L;
    
    private final int target;
    private final Movement description;

    public MotorMovement(int target, Movement description) {
        this.target = target;
        this.description = description;
    }
    
    public int getTarget() {
        return target;
    }
    
    public Movement getDescription() {
        return description;
    }
    
    @Override
	public void accept(MovementVisitor visitor) throws UnsupportedMovementException {
		visitor.visit(this);
	}

    @Override
    public String toString() {
        return new StringBuilder().append("motor ").append(target).append(' ').append(description).toString();
    }
}
