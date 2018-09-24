package cz.vabalcar.jbot.ev3;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * The Class MotorConfigration.
 */
public class MotorConfigration {
	
	/** The motor type. */
	private final Class<? extends BaseRegulatedMotor> motorType;
	
	/** The motor port. */
	private final Port motorPort;
	
	/**
	 * Instantiates a new motor configration.
	 *
	 * @param motorType the motor type
	 * @param motorPort the motor port
	 */
	public MotorConfigration(Class<? extends BaseRegulatedMotor> motorType, Port motorPort) {
		this.motorType = motorType;
		this.motorPort = motorPort;
	}
	
	/**
	 * Gets the motor type.
	 *
	 * @return the motor type
	 */
	public Class<? extends BaseRegulatedMotor> getMotorType() {
		return motorType;
	}
	
	/**
	 * Gets the motor port.
	 *
	 * @return the motor port
	 */
	public Port getMotorPort() {
		return motorPort;
	}
}
