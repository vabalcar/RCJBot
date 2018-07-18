package cz.vabalcar.jbot.events;

import java.io.Serializable;

/**
 * The Class DataProviderDeath.
 *
 * @param <T> the generic type
 */
public class DataProviderDeath<T extends Serializable> extends DataEventImpl<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
	/**
	 * Instantiates a new data provider death.
	 *
	 * @param source the source
	 */
	public DataProviderDeath(DataProviderInfo<T> source) {
		this(source, null);
	}
	
	/**
	 * Instantiates a new data provider death.
	 *
	 * @param source the source
	 * @param data the data
	 */
	private DataProviderDeath(DataProviderInfo<T> source, T data) {
		super(source, data);
	}
}
