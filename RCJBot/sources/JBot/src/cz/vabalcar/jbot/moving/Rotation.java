package cz.vabalcar.jbot.moving;

public class Rotation implements Travel {
    
    private static final long serialVersionUID = 1L;
	
    private final Direction direction;
	private final int speedPercentage;
    
	public Rotation(Direction direction, int speedPercentage) {
		if (!isValid(direction)
				|| speedPercentage < 0 || speedPercentage > 100) {
			throw new IllegalArgumentException();
		}
		this.direction = direction;
		this.speedPercentage = speedPercentage;
	}
	
	public Rotation(Direction direction) {
		this(direction, 100);
	}
	
	public boolean isClockwise() {
		return direction == Direction.LEFT;
	}
	
	private boolean isValid(Direction direction) {
		return direction == Direction.LEFT || direction == Direction.RIGHT;
	}
	
	@Override
	public Direction getDirection() {
		return direction;
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
		sb.append(direction)
		  .append(" rotation ")
		  .append(" at ")
		  .append(speedPercentage)
		  .append("% of max speed");
		return sb.toString();
	}
}
