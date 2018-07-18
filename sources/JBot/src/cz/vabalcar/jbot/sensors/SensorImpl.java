package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataProviderImpl;
import cz.vabalcar.jbot.events.DataProviderInfo;

/**
 * The Class SensorImpl.
 *
 * @param <T> the generic type
 */
public abstract class SensorImpl<T extends Serializable> extends DataProviderImpl<T> implements Sensor<T> {
	
	/** The Constant UNSET_REPEATING. */
	public static final int UNSET_REPEATING = -1;
    
    /** The last data hash code. */
    private int lastDataHashCode = Integer.MIN_VALUE;
	
	/** The info. */
	private final SensorInfo<T> info;
	
	/** The period. */
	private final int period;
	
	/**
	 * Instantiates a new sensor impl.
	 *
	 * @param name the name
	 * @param providedType the provided type
	 * @param frequency the frequency
	 */
	public SensorImpl(String name, Class<T> providedType, int frequency) {
		super(providedType);
		info = new SensorInfo<>(name);
		period = frequency == UNSET_REPEATING ? UNSET_REPEATING : 1000 / frequency;
		initialize();
	}
	
	/**
	 * Instantiates a new sensor impl.
	 *
	 * @param name the name
	 * @param providedType the provided type
	 */
	public SensorImpl(String name, Class<T> providedType) {
        this(name, providedType, UNSET_REPEATING);
    }
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProvider#getInfo()
	 */
	@Override
	public DataProviderInfo<T> getInfo() {
	    return info;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.sensors.Sensor#isRepeatingSet()
	 */
	@Override
	public boolean isRepeatingSet() {
	    return period == UNSET_REPEATING;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.sensors.Sensor#getPeriod()
	 */
	@Override
	public int getPeriod() {
	    return period;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.sensors.Sensor#update()
	 */
	@Override
	public synchronized void update() {
	    T currentData = readData();
	    int currentDataHashCode = currentData.hashCode();
	    if (currentDataHashCode != lastDataHashCode) {
	        raiseDataEvent(new SensorDataEvent<>(getInfo(), currentData));
	        lastDataHashCode = currentDataHashCode;
	    }
	}
}
