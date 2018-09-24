package cz.vabalcar.jbot.ev3;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

/**
 * A factory for creating Wheel objects.
 */
public class WheelFactory {
	
	/** The instance. */
	private static WheelFactory instance;
	
	/**
	 * Instance.
	 *
	 * @return the wheel factory
	 */
	public static WheelFactory instance() {
		if (instance == null) {
			instance = new WheelFactory();
		}
		return instance;
	}
	
	/**
	 * Instantiates a new wheel factory.
	 */
	private WheelFactory() {
	}
	
	/**
	 * Creates the.
	 *
	 * @param wheelConfiguration the wheel configuration
	 * @return the wheel
	 */
	public Wheel create(WheelConfiguration wheelConfiguration) {
		BaseRegulatedMotor motiveMotor = MotorFactory.instance().create(wheelConfiguration.getMotiveMotorConfiguration());
		if (motiveMotor == null) {
			return null;
		}
		return new WheeledChassis.Modeler(motiveMotor, wheelConfiguration.getDiameter())
				.offset(wheelConfiguration.getOffset())
				.gearRatio(wheelConfiguration.getGearRatio())
				.invert(wheelConfiguration.isDirectionInverted());
	}
}
