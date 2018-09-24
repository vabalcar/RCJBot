package cz.vabalcar.jbot.ev3.parsing;

import java.util.HashMap;
import java.util.List;

/**
 * The Class MultiEntityParser.
 */
public class MultiEntityParser extends EntityParser<Void> {

	/** The buffered parsers. */
	private HashMap<String, BufferedEntityParser<?>> bufferedParsers = new HashMap<>();

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.EntityParser#getNeededNumberOfTokens()
	 */
	@Override
	public int getNeededNumberOfTokens() {
		return 0;
	}
	
	/**
	 * Adds the parser.
	 *
	 * @param <T> the generic type
	 * @param parsingEntitySetsName the parsing entity sets name
	 * @param parser the parser
	 */
	public <T> void addParser(String parsingEntitySetsName, EntityParser<T> parser) {
		parser.setEntityDelimiter(getEntityDelimiter());
		parser.setTokenDelimiter(getTokenDelimiter());
		bufferedParsers.put(parsingEntitySetsName, new BufferedEntityParser<>(parsingEntitySetsName, parser));
	}
	
	/**
	 * Gets the parsed entities.
	 *
	 * @param entityTypeName the entity type name
	 * @return the parsed entities
	 */
	public List<?> getParsedEntities(String entityTypeName) {
	    BufferedEntityParser<?> parser = bufferedParsers.get(entityTypeName);
	    if (parser == null) {
	        return null;
	    }
		return parser.getParsedEntities();
	}
	
	/**
	 * Clear.
	 */
	public void clear() {
		for (BufferedEntityParser<?> bufferedParser : bufferedParsers.values()) {
			bufferedParser.clearBuffer();
		}
	}

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.EntityParser#doParse(java.lang.String[], int)
	 */
	@Override
	protected Void doParse(String[] tokens, int offset) throws FormatException {
		bufferedParsers.get(tokens[offset]).parse(tokens, offset + 1);
		return null;
	}

}
