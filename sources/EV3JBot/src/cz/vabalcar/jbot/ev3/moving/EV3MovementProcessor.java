package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.UnsupportedMovementException;
import cz.vabalcar.jbot.ev3.MotorConfigration;
import cz.vabalcar.jbot.ev3.WheelConfiguration;

import java.util.List;

public class EV3MovementProcessor extends DifferencialChassisController {
    
    private final MotorsController simpleMotorController;

    public EV3MovementProcessor(WheelConfiguration leftWheelConfiguration, WheelConfiguration rightWheelConfiguration, 
            List<MotorConfigration> otherMotorsConfigrations) {
        super(leftWheelConfiguration, rightWheelConfiguration);
        simpleMotorController = new MotorsController(otherMotorsConfigrations);
    }
    
    @Override
    public void visit(MotorMovement motorMovement) throws UnsupportedMovementException {
        simpleMotorController.process(motorMovement);
    }

}
