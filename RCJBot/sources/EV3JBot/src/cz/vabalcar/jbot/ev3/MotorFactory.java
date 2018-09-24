package cz.vabalcar.jbot.ev3;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * A factory for creating Motor objects.
 */
public class MotorFactory {
	
	/** The instance. */
	private static MotorFactory instance;
	
	/**
	 * Instance.
	 *
	 * @return the motor factory
	 */
	public static MotorFactory instance() {
		if (instance == null) {
			instance = new MotorFactory();
		}
		return instance;
	}
	
	/**
	 * Instantiates a new motor factory.
	 */
	private MotorFactory() {
	}
	
	/**
	 * Creates the.
	 *
	 * @param motorConfigration the motor configration
	 * @return the base regulated motor
	 */
	public BaseRegulatedMotor create(MotorConfigration motorConfigration) {
		try {
			return (BaseRegulatedMotor)motorConfigration.getMotorType().getConstructor(Port.class).newInstance(motorConfigration.getMotorPort());
		} catch (Exception e) {
			return null;
		}
	}
}
