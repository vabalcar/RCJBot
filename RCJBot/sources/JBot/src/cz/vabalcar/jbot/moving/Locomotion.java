package cz.vabalcar.jbot.moving;

public class Locomotion implements Travel {
    
    private static final long serialVersionUID = 1L;
	
    private final Direction direction;
    private final float motorsRatio;
    private final int speedPercentage;
	
	public Locomotion(Direction direction, float motorsRatio, int speedPercentage) {
		if (!isValid(direction)
				|| motorsRatio < -1 || motorsRatio > 1
				|| speedPercentage < 0 || speedPercentage > 100) {
			throw new IllegalArgumentException();
		}		
		
		this.direction = direction;
		this.motorsRatio = motorsRatio;
		this.speedPercentage = speedPercentage;
	}
	
	public Locomotion(Direction direction) {
		this(direction, 0, 100);
	}
	
	private boolean isValid(Direction direction) {
		return direction == Direction.FORWARD || direction == Direction.BACKWARD;
	}
	
	@Override
	public Direction getDirection() {
		return direction;
	}
	
	public float getMotorsRatio() {
		return motorsRatio;
	}
	
	public int getSpeedPercentage() {
		return speedPercentage;
	}
	
	@Override
	public void accept(MovementVisitor movementProcessor) throws UnsupportedMovementException {
		movementProcessor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("locomotion ")
		  .append(direction)
		  .append(" at ")
		  .append(speedPercentage)
		  .append("% of max speed (ratio ")
		  .append(motorsRatio)
		  .append(')');
		return sb.toString();
	}
}
