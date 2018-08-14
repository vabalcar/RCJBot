package cz.vabalcar.jbot.moving;

public class Stop implements Movement {

    private static final long serialVersionUID = 1L;
	
	@Override
	public void accept(MovementVisitor movementProcessor) throws UnsupportedMovementException {
		movementProcessor.visit(this);
	}
	
	@Override
	public String toString() {
		return "stop";
	}
}
