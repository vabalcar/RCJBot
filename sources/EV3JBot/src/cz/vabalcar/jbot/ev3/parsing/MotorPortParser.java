package cz.vabalcar.jbot.ev3.parsing;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

/**
 * The Class MotorPortParser.
 */
public class MotorPortParser implements Parser<Port> {
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
	 */
	@Override
	public Port parse(String motorPort) throws FormatException {
		switch (motorPort) {
        case "A":
            return MotorPort.A;
        case "B":
            return MotorPort.B;
        case "C":
            return MotorPort.C;
        case "D":
            return MotorPort.D;
        default:
            throw new FormatException();
        }
	}
}
