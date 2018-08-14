package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataProviderImpl;

public abstract class SensorImpl<T extends Serializable> extends DataProviderImpl<T> implements Sensor<T> {
	private final String name;
	private final int period;
    private int lastDataHashCode = Integer.MIN_VALUE;

    public SensorImpl(String name, Class<T> providedType, int frequency) {
		super(providedType);
		this.name = name;
		period = 1000 / frequency;
		initialize();
	}
    
    @Override
    public String getName() {
    	return name;
    }
	
	@Override
	public int getPeriod() {
	    return period;
	}
	
	@Override
	public synchronized void update() {
	    T currentData = readData();
	    int currentDataHashCode = currentData.hashCode();
	    if (currentDataHashCode != lastDataHashCode) {
	        raiseDataEvent(new SensorDataEvent<>(this, currentData));
	        lastDataHashCode = currentDataHashCode;
	    }
	}
}
