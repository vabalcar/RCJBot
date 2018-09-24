package cz.vabalcar.jbot.events;

public abstract class TypeRecogniser<T> {
	
	protected final Class<T> knownType;
	
	public TypeRecogniser(Class<T> knownType) {
		this.knownType = knownType;
	}
	
	public Class<T> getKnownType() {
		return knownType;
	}
}
