package cz.vabalcar.jbot.ev3.parsing;

/**
 * The Class DoubleParser.
 */
public class DoubleParser implements Parser<Double> {

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
	 */
	@Override
	public Double parse(String s) throws FormatException {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new FormatException();
		}
	}
}
