package cz.vabalcar.jbot.moving;

/**
 * The Class LinearAccelerationChange.
 */
public class AccelerationChange implements MovementPropertyChange {
    private static final long serialVersionUID = 1L;
	
    private final double acceleration;
	
	public AccelerationChange(double acceleration) {
		this.acceleration = acceleration;
	}
	
	public double getAcceleration() {
		return acceleration;
	}
	
	@Override
	public void accept(MovementVisitor movementProcessor) throws UnsupportedMovementException {
		movementProcessor.visit(this);
	}
}
