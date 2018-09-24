package cz.vabalcar.jbot.ev3.parsing;

import cz.vabalcar.jbot.ev3.SensorConfiguration;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;

/**
 * The Class SensorConfigurationParser.
 */
public class SensorConfigurationParser extends EntityParser<SensorConfiguration> {
	
	/** The Constant NEEDED_NUMBER_OF_TOKENS. */
	private static final int NEEDED_NUMBER_OF_TOKENS = 4;
	
	/** The sensor type parser. */
	private TypeParser<BaseSensor> sensorTypeParser = new TypeParser<>(BaseSensor.class);
	
	/** The sensor port parser. */
	private SensorPortParser sensorPortParser = new SensorPortParser();
	
	/** The int parser. */
	private IntParser intParser = new IntParser();
	
	/**
	 * Instantiates a new sensor configuration parser.
	 *
	 * @param entityDelimiter the entity delimiter
	 * @param tokenDelimiter the token delimiter
	 */
	public SensorConfigurationParser(char entityDelimiter, char tokenDelimiter) {
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
	public SensorConfiguration doParse(String[] tokens, int offset) throws FormatException {
		return parse(tokens[offset], tokens[offset + 1], tokens[offset + 2], tokens[offset + 3]);
	}
	
	/**
	 * Parses the.
	 *
	 * @param sensorType the sensor type
	 * @param sensorMode the sensor mode
	 * @param sensorPort the sensor port
	 * @param readingFrequency the reading frequency
	 * @return the sensor configuration
	 * @throws FormatException the format exception
	 */
	private SensorConfiguration parse(String sensorType, String sensorMode, String sensorPort, String readingFrequency) throws FormatException {
		Class<BaseSensor> parsedSensorType = sensorTypeParser.parse(sensorType);
	    Port parsedSensorPort = sensorPortParser.parse(sensorPort);
		int parsedReadingFrequency = intParser.parse(readingFrequency);
		
		return new SensorConfiguration(parsedSensorType, sensorMode, parsedSensorPort, parsedReadingFrequency);
	}
}
