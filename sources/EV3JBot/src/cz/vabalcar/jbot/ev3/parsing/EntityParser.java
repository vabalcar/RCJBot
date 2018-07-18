package cz.vabalcar.jbot.ev3.parsing;

import java.io.IOException;
import java.io.Reader;

import cz.vabalcar.util.Util;

/**
 * The Class EntityParser.
 *
 * @param <T> the generic type
 */
public abstract class EntityParser<T> implements ReadingParser<T> {
	
	/** The entity delimiter. */
	private char entityDelimiter = '\n';
	
	/** The token delimiter. */
	private char tokenDelimiter = ' ';
	
	/**
	 * Instantiates a new entity parser.
	 *
	 * @param entityDelimiter the entity delimiter
	 * @param tokenDelimiter the token delimiter
	 */
	public EntityParser(char entityDelimiter, char tokenDelimiter) {
	    this.entityDelimiter = entityDelimiter;
	    this.tokenDelimiter = tokenDelimiter;
    }
	
	/**
	 * Instantiates a new entity parser.
	 */
	public EntityParser() {
    }
	
	/**
	 * Gets the entity delimiter.
	 *
	 * @return the entity delimiter
	 */
	public char getEntityDelimiter() {
		return entityDelimiter;
	}
	
	/**
	 * Sets the entity delimiter.
	 *
	 * @param entityDelimiter the new entity delimiter
	 */
	public void setEntityDelimiter(char entityDelimiter) {
		this.entityDelimiter = entityDelimiter;
	}

	/**
	 * Gets the token delimiter.
	 *
	 * @return the token delimiter
	 */
	public char getTokenDelimiter() {
		return tokenDelimiter;
	}
	
	/**
	 * Sets the token delimiter.
	 *
	 * @param tokenDelimiter the new token delimiter
	 */
	public void setTokenDelimiter(char tokenDelimiter) {
		this.tokenDelimiter = tokenDelimiter;
	}

	/**
	 * Gets the needed number of tokens.
	 *
	 * @return the needed number of tokens
	 */
	public abstract int getNeededNumberOfTokens();
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
	 */
	@Override
	public final T parse(String s) throws FormatException {
		return parse(Util.split(s, tokenDelimiter), 0);
	}
	
	/**
	 * Parses the.
	 *
	 * @param tokens the tokens
	 * @param offset the offset
	 * @return the t
	 * @throws FormatException the format exception
	 */
	public T parse(String[] tokens, int offset) throws FormatException {
		if (tokens == null || tokens.length - offset < getNeededNumberOfTokens()) {
			throw new FormatException();
		}
		return doParse(tokens, offset);
	}
	
	/**
	 * Do parse.
	 *
	 * @param tokens the tokens
	 * @param offset the offset
	 * @return the t
	 * @throws FormatException the format exception
	 */
	protected abstract T doParse(String[] tokens, int offset) throws FormatException;
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.ReadingParser#parse(java.io.Reader)
	 */
	@Override
	public final T parse(Reader reader) throws IOException, FormatException {
		StringBuilder sb = new StringBuilder();
		int readValue;
		int entityDelimiterValue = (int)entityDelimiter;
		while((readValue = reader.read()) != -1 && readValue != entityDelimiterValue) {
			sb.append((char) readValue);
		}
		return parse(sb.toString());
	}
}
