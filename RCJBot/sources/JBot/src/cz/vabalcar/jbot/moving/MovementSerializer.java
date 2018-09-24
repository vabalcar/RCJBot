package cz.vabalcar.jbot.moving;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import cz.vabalcar.jbot.events.DataProviderImpl;

public class MovementSerializer extends DataProviderImpl<Movement> implements MovementVisitor, AutoCloseable {
    
    private final ObjectOutputStream objectOutputStream;
    private static final String NAME = "RMP";

    public MovementSerializer(OutputStream outputStream) throws IOException {
    	super(Movement.class);
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.flush();
        serialize(new SerialiazationBoost());
    }
    
    protected <T extends Movement> void serialize(T movement) {
    	try {
    		objectOutputStream.writeObject(new MovementDataEvent<>(this, movement));
            objectOutputStream.flush();
            objectOutputStream.reset();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void visit(Movement movement) {
		try {
			movement.accept(this);
		} catch (UnsupportedMovementException e) {
			// This cannot happen.
		}
	}
    
    @Override
    public void visit(AccelerationChange linearAccelerationChange) {
    	serialize(linearAccelerationChange);
    }

    @Override
    public void visit(MaxSpeedChange linearSpeedChange) {
    	serialize(linearSpeedChange);
    }
    
    @Override
    public void visit(MotorMovement motorMovement) {
    	serialize(motorMovement);
    }

    @Override
    public void visit(Locomotion linearTranslation) {
    	serialize(linearTranslation);
    }

    @Override
    public void visit(Rotation rotation) {
    	serialize(rotation);
    }

    @Override
    public void visit(Stop stop) {
    	serialize(stop);
    }

    @Override
    public void close() throws IOException {
        objectOutputStream.close();
    }

}
