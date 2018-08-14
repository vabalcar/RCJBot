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

import lejos.hardware.motor.BaseRegulatedMotor;

public class BaseRegulatedMotorController implements MovementVisitor {
    
    public static final float DEFAULT_SPEED_COEF = 0.8f;
    
    private final BaseRegulatedMotor motor;

    public BaseRegulatedMotorController(BaseRegulatedMotor motor) {
        this.motor = motor;
        this.motor.setSpeed(Math.round(this.motor.getMaxSpeed() * DEFAULT_SPEED_COEF));
    }
    
    @Override
    public void visit(MotorMovement motorMovement) throws UnsupportedMovementException {
        visit(motorMovement.getDescription());
    }

	@Override
	public void visit(Movement movement) throws UnsupportedMovementException {
		movement.accept(this);
	}

	@Override
	public void visit(Locomotion locomotion) throws UnsupportedMovementException {
		switch (locomotion.getDirection()) {
        case FORWARD:
            motor.forward();
            break;
        case BACKWARD:
            motor.backward();
            break;
        default:
            throw new UnsupportedMovementException(locomotion);
        }
		
	}

	@Override
	public void visit(Rotation rotation) throws UnsupportedMovementException {
		if (rotation.isClockwise()) {
			motor.forward();
		} else {
			motor.backward();
		}
	}

	@Override
	public void visit(Stop stop) throws UnsupportedMovementException {
		motor.stop(true);
	}

	@Override
	public void visit(AccelerationChange accelerationChange) throws UnsupportedMovementException {
		motor.setAcceleration((int)accelerationChange.getAcceleration());
	}

	@Override
	public void visit(MaxSpeedChange maxSpeedChange) throws UnsupportedMovementException {
		motor.setSpeed((int)maxSpeedChange.getMaxSpeed());
		
	}
}
