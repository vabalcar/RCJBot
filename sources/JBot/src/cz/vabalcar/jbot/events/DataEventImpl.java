package cz.vabalcar.jbot.events;

import java.io.Serializable;

public abstract class DataEventImpl<T extends Serializable> implements DataEvent<T> {
    
    private static final long serialVersionUID = 1L;
	
    private final String sourceName;
	private final T data;
	
	public DataEventImpl(DataProvider<T> source, T data) {
		sourceName = source.getName();
		this.data = data;
	}
	
	@Override
	public String getSourceName() {
		return sourceName;
	}
	
	@Override
	public T getData() {
		return data;
	}
}
