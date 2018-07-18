package cz.vabalcar.jbot.ev3.parsing;

import cz.vabalcar.jbot.ev3.MotorConfigration;
import cz.vabalcar.jbot.ev3.WheelConfiguration;

/**
 * The Class WheelConfigurationParser.
 */
public class WheelConfigurationParser extends EntityParser<WheelConfiguration> {
	
	/** The Constant NEEDED_NUMBER_OF_TOKENS. */
	private static final int NEEDED_NUMBER_OF_TOKENS = 6;
	
	/** The motor configuration parser. */
	private MotorConfigurationParser motorConfigurationParser;
	
	/** The double parser. */
	private DoubleParser doubleParser = new DoubleParser();
	
	/** The boolean parser. */
	private BooleanParser booleanParser = new BooleanParser();

	/**
	 * Instantiates a new wheel configuration parser.
	 *
	 * @param entityDelimiter the entity delimiter
	 * @param tokenDelimiter the token delimiter
	 */
	public WheelConfigurationParser(char entityDelimiter, char tokenDelimiter) {
		super(entityDelimiter, tokenDelimiter);
		motorConfigurationParser = new MotorConfigurationParser(entityDelimiter, tokenDelimiter);
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
	public WheelConfiguration doParse(String[] tokens, int offset) throws FormatException {
	    int motorConfigurationParserNeededTokens = motorConfigurationParser.getNeededNumberOfTokens();
		return parse(motorConfigurationParser.parse(tokens, offset),
				tokens[offset + motorConfigurationParserNeededTokens], 
				tokens[offset + motorConfigurationParserNeededTokens + 1], 
				tokens[offset + motorConfigurationParserNeededTokens + 2], 
				tokens[offset + motorConfigurationParserNeededTokens + 3]);
	}

	/**
	 * Parses the.
	 *
	 * @param parsedMotiveMotorConfiguration the parsed motive motor configuration
	 * @param diameter the diameter
	 * @param offset the offset
	 * @param gearRatio the gear ratio
	 * @param invert the invert
	 * @return the wheel configuration
	 * @throws FormatException the format exception
	 */
	private WheelConfiguration parse(MotorConfigration parsedMotiveMotorConfiguration,
			String diameter, String offset, String gearRatio, String invert) throws FormatException {
		double 	diameterValue = doubleParser.parse(diameter),
				offsetValue = doubleParser.parse(offset),
				gearRatioValue = doubleParser.parse(gearRatio);
		boolean invertValue = booleanParser.parse(invert).booleanValue();
		return new WheelConfiguration(parsedMotiveMotorConfiguration, diameterValue, offsetValue, gearRatioValue, invertValue);
	}
}
