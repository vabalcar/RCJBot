package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.jbot.moving.UnsupportedMovementProcessorActionException;
import cz.vabalcar.jbot.ev3.MotorConfigration;
import cz.vabalcar.jbot.ev3.MotorFactory;
import lejos.hardware.motor.BaseRegulatedMotor;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class MotorsController.
 */
public class MotorsController {
    
    /** The motors. */
    private final List<BaseRegulatedMotorController> motors = new ArrayList<>();

    /**
     * Instantiates a new motors controller.
     *
     * @param motorConfigrations the motor configrations
     */
    public MotorsController(List<MotorConfigration> motorConfigrations) {
        for(MotorConfigration motorConfigration : motorConfigrations) {
            BaseRegulatedMotor motor = MotorFactory.instance().create(motorConfigration);
            motors.add(new BaseRegulatedMotorController(motor));
        }
    }
    
    /**
     * Process.
     *
     * @param motorMovement the motor movement
     * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
     */
    public void process(MotorMovement motorMovement) throws UnsupportedMovementProcessorActionException {
        process(motorMovement.getTarget(), motorMovement.getDescription());
    }
    
    /**
     * Process.
     *
     * @param <M> the generic type
     * @param target the target
     * @param action the action
     * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
     */
    public <M extends Movement> void process(int target, M action) throws UnsupportedMovementProcessorActionException {
        int motorIndex = target - 1;
        if (motorIndex < motors.size()) {
            motors.get(motorIndex).process(action);
        }
    }
}
