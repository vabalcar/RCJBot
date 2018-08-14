package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.moving.AccelerationChange;
import cz.vabalcar.jbot.moving.Locomotion;
import cz.vabalcar.jbot.moving.MaxSpeedChange;
import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.jbot.moving.MovementVisitor;
import cz.vabalcar.jbot.moving.Rotation;
import cz.vabalcar.jbot.moving.Stop;
import cz.vabalcar.jbot.moving.UnsupportedMovementException;
import cz.vabalcar.jbot.ev3.WheelConfiguration;

public class DifferencialChassisController implements MovementVisitor {
    
    private final SynchronizedMovementController controller;

    public DifferencialChassisController(WheelConfiguration leftWheelConfiguration, WheelConfiguration rightWheelConfiguration) {
        controller = new SynchronizedMovementController(leftWheelConfiguration, rightWheelConfiguration);
    }
    
    @Override
	public void visit(Movement movement) throws UnsupportedMovementException {
		movement.accept(this);
	}
    
    @Override
    public void visit(MotorMovement motorMovement) throws UnsupportedMovementException {
        throw new UnsupportedMovementException(motorMovement);
    }

    @Override
    public void visit(Locomotion locomotion) throws UnsupportedMovementException {
        switch (locomotion.getDirection()) {
        case FORWARD:
            controller.forward();
            break;
        case BACKWARD:
            controller.backward();
            break;
        default:
            throw new UnsupportedMovementException(locomotion);
        }
    }

    @Override
    public void visit(Rotation rotation) {
        if (rotation.isClockwise()) {
            controller.rotateRight();
        } else {
            controller.rotateLeft();
        }
    }

    @Override
    public void visit(Stop stop) {
        controller.stop();
    }

    @Override
    public void visit(MaxSpeedChange maxSpeedChange) {
        controller.setLinearSpeed(maxSpeedChange.getMaxSpeed());
    }
    
    @Override
	public void visit(AccelerationChange accelerationChange) throws UnsupportedMovementException {
		throw new UnsupportedMovementException(accelerationChange);
	}
}
