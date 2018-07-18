package cz.vabalcar.jbot.ev3.parsing;

import java.io.IOException;
import java.io.Reader;

/**
 * The Interface ReadingParser.
 *
 * @param <T> the generic type
 */
public interface ReadingParser<T> extends Parser<T> {
	
	/**
	 * Parses the.
	 *
	 * @param reader the reader
	 * @return the t
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FormatException the format exception
	 */
	T parse(Reader reader) throws IOException, FormatException;
}
