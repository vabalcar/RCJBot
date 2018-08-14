package cz.vabalcar.jbot.moving;

public interface MovementVisitor {
    void visit(Movement movementProcessorAction) throws UnsupportedMovementException;
	
    void visit(Locomotion locomotion) throws UnsupportedMovementException;
	void visit(Rotation rotation) throws UnsupportedMovementException;
	void visit(Stop stop) throws UnsupportedMovementException;
	void visit(MotorMovement motorMovement) throws UnsupportedMovementException;
	void visit(AccelerationChange linearAccelerationChange) throws UnsupportedMovementException;
	void visit(MaxSpeedChange linearSpeedChange) throws UnsupportedMovementException;
}
