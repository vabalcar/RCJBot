package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataEventImpl;
import cz.vabalcar.jbot.events.DataProvider;

public class SensorDataEvent<T extends Serializable> extends DataEventImpl<T> {
    
	private static final long serialVersionUID = 1L;
    
	public SensorDataEvent(DataProvider<T> source, T data) {
		super(source, data);
	}
}
