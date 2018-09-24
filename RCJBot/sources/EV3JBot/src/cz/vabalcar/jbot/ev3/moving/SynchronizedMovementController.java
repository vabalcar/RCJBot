package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.ev3.WheelConfiguration;
import cz.vabalcar.jbot.ev3.WheelFactory;
import cz.vabalcar.jbot.moving.Direction;
import lejos.robotics.RegulatedMotor;

public class SynchronizedMovementController {
	
	private final RegulatedMotor leftMotor;
	private final WheelConfiguration leftWheel;
	private final RegulatedMotor rightMotor;
	private final WheelConfiguration rightWheel;
	
	private int leftMotorMaxSpeed;
	private int rightMotorMaxSpeed;
    
    public SynchronizedMovementController(WheelConfiguration leftWheel, WheelConfiguration rightWheel) {
    	this.leftWheel = leftWheel;
    	this.rightWheel = rightWheel;
        
        leftMotor = WheelFactory.instance().create(leftWheel).getMotor();
        rightMotor = WheelFactory.instance().create(rightWheel).getMotor();
        
        initialize(leftMotor, leftWheel);
        initialize(rightMotor, rightWheel);
    }
    
    private int toAngularProperty(int linearProperty, double d) {
        return (int)Math.round((linearProperty / (d * Math.PI)) * 360);
    }
    
    private void initialize(RegulatedMotor motor, WheelConfiguration wheel) {
    	motor.setAcceleration(720);
    	setMaxSpeed((int) (wheel.getDiameter() * wheel.getGearRatio() * 1.5)); 
    }
    
    private void runMotors(Direction leftMotorDirection, Direction rightMotorDirection, float motorsRatio, int speedPercentage) {
    	if (motorsRatio < -1 || motorsRatio > 1
    			|| speedPercentage < 0 || speedPercentage > 100) {
    		throw new IllegalArgumentException();
    	}
    	
        if (leftWheel.isDirectionInverted()) {
            leftMotorDirection = leftMotorDirection.invert();
        }
        
        if (rightWheel.isDirectionInverted()) {
            rightMotorDirection = rightMotorDirection.invert();
        }
        
        leftMotor.synchronizeWith(new RegulatedMotor[] {rightMotor});
        leftMotor.startSynchronization();
        
        int leftMotorSpeed = (int) ((leftMotorMaxSpeed * speedPercentage / 100) * (motorsRatio < 0 ? 1 + motorsRatio : 1f ));
        int rightMotorSpeed = (int) ((rightMotorMaxSpeed * speedPercentage / 100)  * (motorsRatio > 0 ? 1 - motorsRatio : 1f ));
        runMotor(leftMotor, leftMotorDirection, leftMotorSpeed);
        runMotor(rightMotor, rightMotorDirection, rightMotorSpeed);
        
        leftMotor.endSynchronization();
    }
    
    private void runMotor(RegulatedMotor motor, Direction direction, int angularSpeed) {
    	motor.setSpeed(angularSpeed);
        switch (direction) {
        case FORWARD:
            motor.forward();
            break;
        case BACKWARD:
            motor.backward();
            break;
        default:
            throw new IllegalArgumentException();
        }
    }
    
    public void setAcceleration(int linearAcceleration) {
    	leftMotor.setAcceleration(toAngularProperty(linearAcceleration, leftWheel.getDiameter()));
    	rightMotor.setAcceleration(toAngularProperty(linearAcceleration, rightWheel.getDiameter()));
    }
    
    public void setMaxSpeed(int linearSpeed) {
    	leftMotorMaxSpeed = toAngularProperty(linearSpeed, leftWheel.getDiameter());
    	rightMotorMaxSpeed = toAngularProperty(linearSpeed, rightWheel.getDiameter());
    }

    public void forward(float motorsRatio, int speedPercentage) {
        runMotors(Direction.FORWARD, Direction.FORWARD, motorsRatio, speedPercentage);
    }
    
    public void backward(float motorsRatio, int speedPercentage) {
        runMotors(Direction.BACKWARD, Direction.BACKWARD, motorsRatio, speedPercentage);
    }
    
    public void rotateLeft(int speedPercentage) {
        runMotors(Direction.FORWARD, Direction.BACKWARD, 0, speedPercentage / 2);
    }

    public void rotateRight(int speedPercentage) {
        runMotors(Direction.BACKWARD, Direction.FORWARD, 0, speedPercentage / 2);
    }
    
    public void stop() {
        leftMotor.stop(true);
        rightMotor.stop(true);
    }
}
