package cz.vabalcar.jbot.ev3.parsing;

/**
 * The Interface Parser.
 *
 * @param <T> the generic type
 */
public interface Parser<T> {
	
	/**
	 * Parses the.
	 *
	 * @param s the s
	 * @return the t
	 * @throws FormatException the format exception
	 */
	T parse(String s) throws FormatException;
}
