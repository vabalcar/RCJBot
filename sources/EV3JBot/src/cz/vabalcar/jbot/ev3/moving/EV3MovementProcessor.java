package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.UnsupportedMovementProcessorActionException;
import cz.vabalcar.jbot.ev3.MotorConfigration;
import cz.vabalcar.jbot.ev3.WheelConfiguration;

import java.util.List;

/**
 * The Class EV3MovementProcessor.
 */
public class EV3MovementProcessor extends DifferencialChassisController {
    
    /** The simple motor controller. */
    private final MotorsController simpleMotorController;

    /**
     * Instantiates a new EV 3 movement processor.
     *
     * @param leftWheelConfiguration the left wheel configuration
     * @param rightWheelConfiguration the right wheel configuration
     * @param otherMotorsConfigrations the other motors configrations
     */
    public EV3MovementProcessor(WheelConfiguration leftWheelConfiguration, WheelConfiguration rightWheelConfiguration, 
            List<MotorConfigration> otherMotorsConfigrations) {
        super(leftWheelConfiguration, rightWheelConfiguration);
        simpleMotorController = new MotorsController(otherMotorsConfigrations);
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.ev3.moving.DifferencialChassisController#process(cz.vabalcar.jbot.moving.MotorMovement)
     */
    @Override
    public void process(MotorMovement motorMovement) throws UnsupportedMovementProcessorActionException {
        simpleMotorController.process(motorMovement);
    }

}
