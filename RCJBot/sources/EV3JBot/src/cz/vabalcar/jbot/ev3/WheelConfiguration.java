package cz.vabalcar.jbot.ev3;

/**
 * The Class WheelConfiguration.
 */
public class WheelConfiguration {
	
	/** The motive motor configuration. */
	private final MotorConfigration motiveMotorConfiguration;
	
	/** The diameter. */
	private final double diameter;
	
	/** The offset. */
	private final double offset;
	
	/** The gear ratio. */
	private final double gearRatio;
	
	/** The invert direction. */
	private final boolean invertDirection;
	
	/**
	 * Instantiates a new wheel configuration.
	 *
	 * @param motiveMotorConfiguration the motive motor configuration
	 * @param diameter the diameter
	 * @param offset the offset
	 * @param gearRatio the gear ratio
	 * @param invertDirection the invert direction
	 */
	public WheelConfiguration(MotorConfigration motiveMotorConfiguration, 
			double diameter, double offset, double gearRatio, boolean invertDirection) {
		this.motiveMotorConfiguration = motiveMotorConfiguration;
		this.diameter = diameter;
		this.offset = offset;
		this.gearRatio = gearRatio;
		this.invertDirection = invertDirection;
	}

	/**
	 * Gets the motive motor configuration.
	 *
	 * @return the motive motor configuration
	 */
	public MotorConfigration getMotiveMotorConfiguration() {
		return motiveMotorConfiguration;
	}

	/**
	 * Gets the diameter.
	 *
	 * @return the diameter
	 */
	public double getDiameter() {
		return diameter;
	}
	
	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * Gets the gear ratio.
	 *
	 * @return the gear ratio
	 */
	public double getGearRatio() {
		return gearRatio;
	}

	/**
	 * Checks if is direction inverted.
	 *
	 * @return true, if is direction inverted
	 */
	public boolean isDirectionInverted() {
		return invertDirection;
	}
	
}
