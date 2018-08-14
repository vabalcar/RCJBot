package cz.vabalcar.jbot.moving;

public class Rotation implements Travel {
    
    private static final long serialVersionUID = 1L;
	private Direction direction;
	
	public Rotation(Direction direction) {
		if (!isValid(direction)) {
			throw new IllegalArgumentException();
		}
		this.direction = direction;
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
	
	@Override
	public void accept(MovementVisitor movementProcessor) throws UnsupportedMovementException {
		movementProcessor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(direction).append(" rotation");
		return sb.toString();
	}
}
