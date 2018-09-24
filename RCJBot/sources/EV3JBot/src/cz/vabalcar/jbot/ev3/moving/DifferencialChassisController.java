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
	public void visit(AccelerationChange accelerationChange) throws UnsupportedMovementException {
		controller.setAcceleration((int) accelerationChange.getAcceleration());
	}

    @Override
    public void visit(MaxSpeedChange maxSpeedChange) {
        controller.setMaxSpeed((int) maxSpeedChange.getMaxSpeed());
    }

    @Override
    public void visit(Locomotion locomotion) throws UnsupportedMovementException {
        switch (locomotion.getDirection()) {
        case FORWARD:
            controller.forward(locomotion.getMotorsRatio(), locomotion.getSpeedPercentage());
            break;
        case BACKWARD:
            controller.backward(locomotion.getMotorsRatio(), locomotion.getSpeedPercentage());
            break;
        default:
            throw new UnsupportedMovementException(locomotion);
        }
    }

    @Override
    public void visit(Rotation rotation) {
        if (rotation.isClockwise()) {
            controller.rotateRight(rotation.getSpeedPercentage());
        } else {
            controller.rotateLeft(rotation.getSpeedPercentage());
        }
    }

    @Override
    public void visit(Stop stop) {
        controller.stop();
    }
}
