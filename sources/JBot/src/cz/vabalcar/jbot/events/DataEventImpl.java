package cz.vabalcar.jbot.events;

import java.io.Serializable;

/**
 * The Class DataEventImpl.
 *
 * @param <T> the generic type
 */
public abstract class DataEventImpl<T extends Serializable> implements DataEvent<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
	/** The source. */
	private DataProviderInfo<T> source;
	
	/** The data. */
	private T data;
	
	/**
	 * Instantiates a new data event impl.
	 *
	 * @param source the source
	 * @param data the data
	 */
	public DataEventImpl(DataProviderInfo<T> source, T data) {
		this.source = source;
		this.data = data;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataEvent#getSourceInfo()
	 */
	@Override
	public DataProviderInfo<T> getSourceInfo() {
		return source;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataEvent#getData()
	 */
	@Override
	public T getData() {
		return data;
	}
}
