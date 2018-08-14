package cz.vabalcar.jbot.moving;

/**
 * The Class LinearSpeedChange.
 */
public class MaxSpeedChange implements MovementPropertyChange {
    
    private static final long serialVersionUID = 1L;
    
    private final double maxSpeed;
	
	public MaxSpeedChange(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	@Override
	public void accept(MovementVisitor movementProcessor) throws UnsupportedMovementException {
		movementProcessor.visit(this);
	}
}
