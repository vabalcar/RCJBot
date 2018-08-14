package cz.vabalcar.jbot.moving;

public class Locomotion implements Travel {
    
    private static final long serialVersionUID = 1L;
	private final Direction direction;
	
	public Locomotion(Direction direction) {
		if (!isValid(direction)) {
			throw new IllegalArgumentException();
		}
		this.direction = direction;
	}
	
	private boolean isValid(Direction direction) {
		return direction == Direction.FORWARD || direction == Direction.BACKWARD;
	}
	
	@Override
	public Direction getDirection() {
		return direction;
	}
	
	@Override
	public void accept(MovementVisitor movementProcessor) throws UnsupportedMovementException {
		movementProcessor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("locomotion ").append(direction);
		return sb.toString();
	}
}
