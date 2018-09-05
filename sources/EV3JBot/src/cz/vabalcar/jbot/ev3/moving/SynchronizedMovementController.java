package cz.vabalcar.jbot.ev3.moving;

import cz.vabalcar.jbot.ev3.WheelConfiguration;
import cz.vabalcar.jbot.ev3.WheelFactory;
import cz.vabalcar.jbot.moving.Direction;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;

public class SynchronizedMovementController {
    private final double RATIO_MAX = 1.0;
    private final double RATIO_MIN = 0.0;
    private final double RATIO_MIDDLE = (RATIO_MAX - RATIO_MIN) / 2.0;
    private final double DEFAULT_SPEED_MULTIPLIER = 1.5;
    public final int DEFAULT_MOTOR_ACCELERATION = 720;//degrees/s/s
    public final int DEFAULT_ROTATION_SPEED_COEF = 4;

    private final WheelConfiguration leftWheelConfiguration;
    private final WheelConfiguration rightWheelConfiguration;
    private final Wheel leftWheel;
    private final Wheel rightWheel;
    
    private int leftMotorSetSpeed;
    private int leftMotorSpeed;
    private int rightMotorSetSpeed;
    private int rightMotorSpeed;
    private double rotationRatio = RATIO_MIDDLE;
    private int rotationSpeedCoef = DEFAULT_ROTATION_SPEED_COEF;
    
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
    
    public void setLinearSpeed(double speed) {
        setMotorsSpeed(speed);
    }

    private void setLeftMotorSpeed(int speed) {
        speed *= (1 - Math.abs(RATIO_MIDDLE - rotationRatio) / RATIO_MIDDLE);
        leftMotorSetSpeed = speed;
        leftMotorSpeed = countAngularSpeed(speed, leftWheelConfiguration.getDiameter());
    }
    
    private void setLeftMotorSpeed(double speed) {
        setLeftMotorSpeed((int)Math.round(speed));
    }
    
    private void setRightMotorSpeed(int speed) {
        speed *= (1 - Math.abs(RATIO_MIDDLE - rotationRatio) / RATIO_MIDDLE);
        rightMotorSetSpeed = speed;
        rightMotorSpeed = countAngularSpeed(speed, rightWheelConfiguration.getDiameter());
    }
    
    private void setRightMotorSpeed(double speed) {
        setRightMotorSpeed((int)Math.round(speed));
    }
    
    private void setMotorsSpeed(double speed) {
        setLeftMotorSpeed(speed);
        setRightMotorSpeed(speed);
    }
    
    private void updateMotorsSpeed() {
        setLeftMotorSpeed(leftMotorSetSpeed);
        setRightMotorSpeed(rightMotorSetSpeed);
    }
    
    private static int countAngularSpeed(int linearSpeed, double wheelDiameter) {
        return (int)Math.round((linearSpeed / (wheelDiameter * Math.PI)) * 360);
    }
    
    public void setRotationRatio(double rotationRatio) {
        if (rotationRatio < RATIO_MIN || rotationRatio > RATIO_MAX) {
            throw new IllegalArgumentException();
        }
        this.rotationRatio = rotationRatio;
        updateMotorsSpeed();
    }
    
    public int getRotationSpeedCoef() {
        return rotationSpeedCoef;
    }
    
    public void setRotationSpeedCoef(int rotationSpeedCoef) {
        this.rotationSpeedCoef = rotationSpeedCoef;
    }

    public void forward() {
        runMotors(Direction.FORWARD, Direction.FORWARD);
    }

    public void backward() {
        runMotors(Direction.BACKWARD, Direction.BACKWARD);
    }
    
    public void rotateLeft() {
        runMotors(Direction.FORWARD, Direction.BACKWARD);
    }

    public void rotateRight() {
        runMotors(Direction.BACKWARD, Direction.FORWARD);
    }
    
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
    
    public void stop() {
        leftWheel.getMotor().stop(true);
        rightWheel.getMotor().stop(true);
    }
}
