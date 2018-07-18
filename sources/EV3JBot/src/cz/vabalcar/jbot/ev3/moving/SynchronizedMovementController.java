package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.ev3.WheelConfiguration;
import cz.vabalcar.jbot.ev3.WheelFactory;
import cz.vabalcar.jbot.moving.Direction;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;

/**
 * The Class SynchronizedMovementController.
 */
public class SynchronizedMovementController {
    
    /** The ratio max. */
    private final double RATIO_MAX = 1;
    
    /** The ratio min. */
    private final double RATIO_MIN = 0;
    
    /** The ratio middle. */
    private final double RATIO_MIDDLE = (RATIO_MAX - RATIO_MIN) / 2;
    
    /** The default speed multiplier. */
    private final double DEFAULT_SPEED_MULTIPLIER = 1.5;
    
    /** The default motor acceleration. */
    public final int DEFAULT_MOTOR_ACCELERATION = 720;//degrees/s/s
    
    /** The default rotation speed coef. */
    public final int DEFAULT_ROTATION_SPEED_COEF = 4;

    /** The left wheel configuration. */
    private final WheelConfiguration leftWheelConfiguration;
    
    /** The right wheel configuration. */
    private final WheelConfiguration rightWheelConfiguration;
    
    /** The left wheel. */
    private final Wheel leftWheel;
    
    /** The right wheel. */
    private final Wheel rightWheel;
    
    /** The left motor set speed. */
    private int leftMotorSetSpeed;
    
    /** The left motor speed. */
    private int leftMotorSpeed;
    
    /** The right motor set speed. */
    private int rightMotorSetSpeed;
    
    /** The right motor speed. */
    private int rightMotorSpeed;
    
    /** The rotation ratio. */
    private double rotationRatio = RATIO_MIDDLE;
    
    /** The rotation speed coef. */
    private int rotationSpeedCoef = DEFAULT_ROTATION_SPEED_COEF;
    
    /**
     * Instantiates a new synchronized movement controller.
     *
     * @param leftWheelConfiguration the left wheel configuration
     * @param rightWheelConfiguration the right wheel configuration
     */
    public SynchronizedMovementController(WheelConfiguration leftWheelConfiguration, WheelConfiguration rightWheelConfiguration) {
        this.leftWheelConfiguration = leftWheelConfiguration;
        this.rightWheelConfiguration = rightWheelConfiguration;
        Wheel parsedLeftWheel = WheelFactory.instance().create(leftWheelConfiguration);
        Wheel parsedRightWheel = WheelFactory.instance().create(rightWheelConfiguration);
        leftWheel = parsedLeftWheel;
        rightWheel = parsedRightWheel;
        setMotorsSpeed(Math.min(leftWheelConfiguration.getDiameter() * leftWheelConfiguration.getGearRatio(), 
                           rightWheelConfiguration.getDiameter() * rightWheelConfiguration.getGearRatio()) 
                * DEFAULT_SPEED_MULTIPLIER);
        leftWheel.getMotor().setAcceleration(DEFAULT_MOTOR_ACCELERATION);
        rightWheel.getMotor().setAcceleration(DEFAULT_MOTOR_ACCELERATION);
    }
    
    /**
     * Sets the linear speed.
     *
     * @param speed the new linear speed
     */
    public void setLinearSpeed(double speed) {
        setMotorsSpeed(speed);
    }

    /**
     * Sets the left motor speed.
     *
     * @param speed the new left motor speed
     */
    private void setLeftMotorSpeed(int speed) {
        speed *= (1 - Math.abs(RATIO_MIDDLE - rotationRatio) / RATIO_MIDDLE);
        leftMotorSetSpeed = speed;
        leftMotorSpeed = countAngularSpeed(speed, leftWheelConfiguration.getDiameter());
    }
    
    /**
     * Sets the left motor speed.
     *
     * @param speed the new left motor speed
     */
    private void setLeftMotorSpeed(double speed) {
        setLeftMotorSpeed((int)Math.round(speed));
    }
    
    /**
     * Sets the right motor speed.
     *
     * @param speed the new right motor speed
     */
    private void setRightMotorSpeed(int speed) {
        speed *= (1 - Math.abs(RATIO_MIDDLE - rotationRatio) / RATIO_MIDDLE);
        rightMotorSetSpeed = speed;
        rightMotorSpeed = countAngularSpeed(speed, rightWheelConfiguration.getDiameter());
    }
    
    /**
     * Sets the right motor speed.
     *
     * @param speed the new right motor speed
     */
    private void setRightMotorSpeed(double speed) {
        setRightMotorSpeed((int)Math.round(speed));
    }
    
    /**
     * Sets the motors speed.
     *
     * @param speed the new motors speed
     */
    private void setMotorsSpeed(double speed) {
        setLeftMotorSpeed(speed);
        setRightMotorSpeed(speed);
    }
    
    /**
     * Update motors speed.
     */
    private void updateMotorsSpeed() {
        setLeftMotorSpeed(leftMotorSetSpeed);
        setRightMotorSpeed(rightMotorSetSpeed);
    }
    
    /**
     * Count angular speed.
     *
     * @param linearSpeed the linear speed
     * @param wheelDiameter the wheel diameter
     * @return the int
     */
    private static int countAngularSpeed(int linearSpeed, double wheelDiameter) {
        return (int)Math.round((linearSpeed / (wheelDiameter * Math.PI)) * 360);
    }
    
    /**
     * Sets the rotation ratio.
     *
     * @param rotationRatio the new rotation ratio
     */
    public void setRotationRatio(double rotationRatio) {
        if (rotationRatio < RATIO_MIN || rotationRatio > RATIO_MAX) {
            throw new IllegalArgumentException();
        }
        this.rotationRatio = rotationRatio;
        updateMotorsSpeed();
    }
    
    /**
     * Gets the rotation speed coef.
     *
     * @return the rotation speed coef
     */
    public int getRotationSpeedCoef() {
        return rotationSpeedCoef;
    }
    
    /**
     * Sets the rotation speed coef.
     *
     * @param rotationSpeedCoef the new rotation speed coef
     */
    public void setRotationSpeedCoef(int rotationSpeedCoef) {
        this.rotationSpeedCoef = rotationSpeedCoef;
    }

    /**
     * Forward.
     */
    public void forward() {
        runMotors(Direction.FORWARD, Direction.FORWARD);
    }

    /**
     * Backward.
     */
    public void backward() {
        runMotors(Direction.BACKWARD, Direction.BACKWARD);
    }
    
    /**
     * Rotate left.
     */
    public void rotateLeft() {
        runMotors(Direction.FORWARD, Direction.BACKWARD);
    }

    /**
     * Rotate right.
     */
    public void rotateRight() {
        runMotors(Direction.BACKWARD, Direction.FORWARD);
    }
    
    /**
     * Run motors.
     *
     * @param leftMotorDirection the left motor direction
     * @param rightMotorDirection the right motor direction
     */
    private void runMotors(Direction leftMotorDirection, Direction rightMotorDirection) {
        if (leftWheelConfiguration.isDirectionInverted()) {
            leftMotorDirection = leftMotorDirection.invert();
        }
        if (rightWheelConfiguration.isDirectionInverted()) {
            rightMotorDirection = rightMotorDirection.invert();
        }
        leftWheel.getMotor().synchronizeWith(new RegulatedMotor[] {rightWheel.getMotor()});
        leftWheel.getMotor().startSynchronization();
        int currentRotationSpeedCoef = leftMotorDirection == rightMotorDirection ? 1 : rotationSpeedCoef;
        runMotor(leftWheel.getMotor(), leftMotorSpeed / currentRotationSpeedCoef, leftMotorDirection);
        runMotor(rightWheel.getMotor(), rightMotorSpeed / currentRotationSpeedCoef, rightMotorDirection);
        leftWheel.getMotor().endSynchronization();
    }
    
    /**
     * Run motor.
     *
     * @param motor the motor
     * @param speed the speed
     * @param direction the direction
     */
    private void runMotor(RegulatedMotor motor, int speed, Direction direction) {
        motor.setSpeed(speed);
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
    
    /**
     * Stop.
     */
    public void stop() {
        leftWheel.getMotor().stop(true);
        rightWheel.getMotor().stop(true);
    }
}
