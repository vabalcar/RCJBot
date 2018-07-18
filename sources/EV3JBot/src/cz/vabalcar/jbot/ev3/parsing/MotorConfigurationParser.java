package cz.vabalcar.jbot.ev3.parsing;

import cz.vabalcar.jbot.ev3.MotorConfigration;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * The Class MotorConfigurationParser.
 */
public class MotorConfigurationParser extends EntityParser<MotorConfigration> {
	
	/** The Constant NEEDED_NUMBER_OF_TOKENS. */
	private static final int NEEDED_NUMBER_OF_TOKENS = 2;
	
	/** The motor type parser. */
	private TypeParser<BaseRegulatedMotor> motorTypeParser = new TypeParser<>(BaseRegulatedMotor.class);
	
	/** The motor port parser. */
	private MotorPortParser motorPortParser = new MotorPortParser();

	/**
	 * Instantiates a new motor configuration parser.
	 *
	 * @param entityDelimiter the entity delimiter
	 * @param tokenDelimiter the token delimiter
	 */
	public MotorConfigurationParser(char entityDelimiter, char tokenDelimiter) {
		super(entityDelimiter, tokenDelimiter);
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.EntityParser#getNeededNumberOfTokens()
	 */
	@Override
	public int getNeededNumberOfTokens() {
		return NEEDED_NUMBER_OF_TOKENS;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.EntityParser#doParse(java.lang.String[], int)
	 */
	@Override
	public MotorConfigration doParse(String[] tokens, int offset) throws FormatException {
		return parse(tokens[offset], tokens[offset + 1]);
	}
	
	/**
	 * Parses the.
	 *
	 * @param motorType the motor type
	 * @param motorPort the motor port
	 * @return the motor configration
	 * @throws FormatException the format exception
	 */
	private MotorConfigration parse(String motorType, String motorPort) throws FormatException {
		Class<BaseRegulatedMotor> parsedMotorType = motorTypeParser.parse(motorType);
		Port parsedMotorPort = motorPortParser.parse(motorPort);
		
		return new MotorConfigration(parsedMotorType, parsedMotorPort);
	}
}
