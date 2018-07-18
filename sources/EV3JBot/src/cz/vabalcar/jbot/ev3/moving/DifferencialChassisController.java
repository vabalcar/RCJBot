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
import cz.vabalcar.jbot.ev3.WheelConfiguration;

/**
 * The Class DifferencialChassisController.
 */
public class DifferencialChassisController extends MovementProcessorImpl {
    
    /** The controller. */
    private final SynchronizedMovementController controller;

    /**
     * Instantiates a new differencial chassis controller.
     *
     * @param leftWheelConfiguration the left wheel configuration
     * @param rightWheelConfiguration the right wheel configuration
     */
    public DifferencialChassisController(WheelConfiguration leftWheelConfiguration, WheelConfiguration rightWheelConfiguration) {
        controller = new SynchronizedMovementController(leftWheelConfiguration, rightWheelConfiguration);
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.MotorMovement)
     */
    @Override
    public void process(MotorMovement motorMovement) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(motorMovement);
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
            controller.forward();
            break;
        case BACKWARD:
            controller.backward();
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
        if (rotation.isDefinite()) {
            throw new UnsupportedMovementProcessorActionException(rotation);
        }
        if (rotation.isClockwise()) {
            controller.rotateRight();
        } else {
            controller.rotateLeft();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.Stop)
     */
    @Override
    public void process(Stop stop) {
        controller.stop();
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.AngularAccelerationChange)
     */
    @Override
    public void apply(AngularAccelerationChange change) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(change);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.AngularSpeedChange)
     */
    @Override
    public void apply(AngularSpeedChange change) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(change);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.LinearAccelerationChange)
     */
    @Override
    public void apply(LinearAccelerationChange change) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(change);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.LinearSpeedChange)
     */
    @Override
    public void apply(LinearSpeedChange change) {
        controller.setLinearSpeed(change.getLinearSpeed());
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.MinRadiusChange)
     */
    @Override
    public void apply(MinRadiusChange change) throws UnsupportedMovementProcessorActionException {
        throw new UnsupportedMovementProcessorActionException(change);
    }
}
