package cz.vabalcar.jbot.ev3.parsing;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

/**
 * The Class SensorPortParser.
 */
public class SensorPortParser implements Parser<Port> {
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
	 */
	@Override
	public Port parse(String sensorPort) throws FormatException {
		switch (sensorPort) {
        case "S1":
            return SensorPort.S1;
        case "S2":    
            return SensorPort.S2;
        case "S3":
            return SensorPort.S3;
        case "S4":
            return SensorPort.S4;
        default:
            throw new FormatException();
        }
	}
}
