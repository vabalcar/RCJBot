package cz.vabalcar.jbot.moving;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataListenerImpl;

public class MovementListener extends DataListenerImpl<Movement> {
    
    private final MovementVisitor movementProcessor;

    public MovementListener(MovementVisitor movementVisitor) {
        super(Movement.class);
        this.movementProcessor = movementVisitor;
    }

	@Override
	public boolean processDataEvent(DataEvent<? extends Movement> event) {
		try {
			movementProcessor.visit(event.getData());
			return true;
		} catch (UnsupportedMovementException e) {
			return false;
		}
	}

	@Override
	public void close() {

	}

}
