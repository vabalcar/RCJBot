package cz.vabalcar.jbot.ev3.parsing;

/**
 * The Class BooleanParser.
 */
public class BooleanParser implements Parser<Boolean> {

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
	 */
	@Override
	public Boolean parse(String s) throws FormatException {
		String sLower = s.toLowerCase();
		if (!sLower.equals("true") && !sLower.equals("false")) {
			throw new FormatException();
		}
		return Boolean.parseBoolean(s);
	}

}
