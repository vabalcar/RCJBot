package cz.vabalcar.jbot.events;

/**
 * The Class TypeRecogniser.
 *
 * @param <T> the generic type
 */
public abstract class TypeRecogniser<T> {
	
	/** The known type. */
	protected Class<T> knownType;
	
	/**
	 * Gets the known type.
	 *
	 * @return the known type
	 */
	public Class<T> getKnownType() {
		return knownType;
	}
	
	/**
	 * Instantiates a new type recogniser.
	 *
	 * @param knownType the known type
	 */
	public TypeRecogniser(Class<T> knownType) {
		this.knownType = knownType;
	}
}
