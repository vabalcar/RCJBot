package cz.vabalcar.jbot.ev3.parsing;

/**
 * The Class TypeParser.
 *
 * @param <B> the generic type
 */
public class TypeParser<B> implements Parser<Class<B>> {
	
	/** The base type. */
	private final Class<B> baseType;
	
	/** The type full name prefix. */
	private final String typeFullNamePrefix;
	
	/**
	 * Instantiates a new type parser.
	 *
	 * @param baseType the base type
	 */
	public TypeParser(Class<B> baseType) {
		this.baseType = baseType;
		String baseTypePackage = baseType.getPackage().getName();
		this.typeFullNamePrefix = baseTypePackage == null? "" : baseTypePackage + ".";
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class<B> parse(String type) throws FormatException {
	    Class<?> parsedClass;
		try {
			parsedClass = Class.forName(typeFullNamePrefix + type);
		} catch (ClassNotFoundException e) {
			throw new FormatException();
		}
		if (!baseType.isAssignableFrom(parsedClass)) {
			throw new FormatException();
		}
		return (Class<B>) parsedClass;
	}

}
