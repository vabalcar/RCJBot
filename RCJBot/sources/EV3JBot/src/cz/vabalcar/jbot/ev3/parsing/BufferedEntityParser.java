package cz.vabalcar.jbot.ev3.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class BufferedEntityParser.
 *
 * @param <T> the generic type
 */
public class BufferedEntityParser<T> extends EntityParser<Void> {

	/** The parsed entity name. */
	private final String parsedEntityName;
	
	/** The entity set parser. */
	private final EntityParser<T> entitySetParser;
	
	/** The parsed entities. */
	private final List<T> parsedEntities = new ArrayList<>();
	
	/**
	 * Instantiates a new buffered entity parser.
	 *
	 * @param parsedEntityName the parsed entity name
	 * @param entityParser the entity parser
	 */
	public BufferedEntityParser(String parsedEntityName, EntityParser<T> entityParser) {
		//TODO: check char <-> int conversion
		entityParser.setEntityDelimiter(getEntityDelimiter());
		entityParser.setTokenDelimiter(getTokenDelimiter());
		this.parsedEntityName = parsedEntityName;
		this.entitySetParser = entityParser;
	}
	
	/**
	 * Gets the parsed entity name.
	 *
	 * @return the parsed entity name
	 */
	public String getParsedEntityName() {
		return parsedEntityName;
	}
	
	/**
	 * Gets the parsed entities.
	 *
	 * @return the parsed entities
	 */
	public List<T> getParsedEntities() {
		return parsedEntities;
	}
	
	/**
	 * Clear buffer.
	 */
	public void clearBuffer() {
		parsedEntities.clear();
	}

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.EntityParser#getNeededNumberOfTokens()
	 */
	@Override
	public int getNeededNumberOfTokens() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.EntityParser#doParse(java.lang.String[], int)
	 */
	@Override
	protected Void doParse(String[] tokens, int offset) throws FormatException {
		if (tokens.length >= 2 && tokens[0].equals(parsedEntityName)) {
			T parsedEntitySet = entitySetParser.doParse(tokens, 1);
			if (parsedEntitySet != null) {
				parsedEntities.add(parsedEntitySet);
			}
		}
		return null;
	}

}
