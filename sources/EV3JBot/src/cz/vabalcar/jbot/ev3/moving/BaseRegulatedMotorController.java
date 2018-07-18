package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.moving.AngularAccelerationChange;
import cz.vabalcar.jbot.moving.AngularSpeedChange;
import cz.vabalcar.jbot.moving.ArcTranslation;
import cz.vabalcar.jbot.moving.LinearAccelerationChange;
import cz.vabalcar.jbot.moving.LinearSpeedChange;
import cz.vabalcar.jbot.moving.LinearTranslation;
import cz.vabalcar.jbot.moving.MinRadiusChange;
import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.MovementProcessorImpl;
import cz.vabalcar.jbot.moving.Rotation;
import cz.vabalcar.jbot.moving.Stop;
import cz.vabalcar.jbot.moving.UnsupportedMovementProcessorActionException;
import lejos.hardware.motor.BaseRegulatedMotor;

/**
 * The Class BaseRegulatedMotorController.
 */
public class BaseRegulatedMotorController extends MovementProcessorImpl {
    
    /** The Constant DEFAULT_SPEED_COEF. */
    public static final float DEFAULT_SPEED_COEF = 0.8f;
    
    /** The motor. */
    private final BaseRegulatedMotor motor;

    /**
     * Instantiates a new base regulated motor controller.
     *
     * @param motor the motor
     */
    public BaseRegulatedMotorController(BaseRegulatedMotor motor) {
        this.motor = motor;
        this.motor.setSpeed(Math.round(this.motor.getMaxSpeed() * DEFAULT_SPEED_COEF));
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.AngularAccelerationChange)
     */
    @Override
    public void apply(AngularAccelerationChange angularAccelerationChange) throws UnsupportedMovementProcessorActionException {
        motor.setAcceleration((int)angularAccelerationChange.getAngularAcceleration());
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.AngularSpeedChange)
     */
    @Override
    public void apply(AngularSpeedChange angularSpeedChange) throws UnsupportedMovementProcessorActionException {
        motor.setSpeed((float)angularSpeedChange.getAngularSpeed());
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.LinearAccelerationChange)
     */
    @Override
    public void apply(LinearAccelerationChange linearAccelerationChange) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(linearAccelerationChange);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.LinearSpeedChange)
     */
    @Override
    public void apply(LinearSpeedChange linearSpeedChange) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(linearSpeedChange);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.MinRadiusChange)
     */
    @Override
    public void apply(MinRadiusChange minRadiusChange) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(minRadiusChange);
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.MotorMovement)
     */
    @Override
    public void process(MotorMovement motorMovement) throws UnsupportedMovementProcessorActionException {
        process(motorMovement.getDescription());
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.ArcTranslation)
     */
    @Override
    public void process(ArcTranslation arcTranslation) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(arcTranslation);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.LinearTranslation)
     */
    @Override
    public void process(LinearTranslation linearTranslation) throws UnsupportedMovementProcessorActionException {
        if (linearTranslation.isDefinite()) {
            throw new UnsupportedMovementProcessorActionException(linearTranslation);
        }
        switch (linearTranslation.getDirection()) {
        case FORWARD:
            motor.forward();
            break;
        case BACKWARD:
            motor.backward();
            break;
        default:
            throw new UnsupportedMovementProcessorActionException(linearTranslation);
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.Rotation)
     */
    @Override
    public void process(Rotation rotation) throws UnsupportedMovementProcessorActionException {
        if (!rotation.isDefinite()) {
            throw new UnsupportedMovementProcessorActionException(rotation);
        }
        int direction = rotation.isClockwise() ? 1 : -1;
        motor.rotate(direction * (int)rotation.getAngle(), true);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.Stop)
     */
    @Override
    public void process(Stop stop) throws UnsupportedMovementProcessorActionException {
        motor.stop(true);
    }
}
