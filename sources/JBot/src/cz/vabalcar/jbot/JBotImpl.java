package cz.vabalcar.jbot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cz.vabalcar.jbot.events.DataEventRaiser;
import cz.vabalcar.jbot.events.DataProviderImpl;
import cz.vabalcar.jbot.moving.MovementProcessor;
import cz.vabalcar.jbot.sensors.Sensor;
import cz.vabalcar.jbot.sensors.SensorScheduler;

/**
 * The Class JBotImpl.
 *
 * @param <T> the generic type
 */
public abstract class JBotImpl<T extends Serializable> extends DataProviderImpl<T> implements JBot<T> {
    
    /** The movement processor. */
    private MovementProcessor movementProcessor;
	
	/** The sensors. */
	private Map<String, Sensor<?>> sensors = new HashMap<>();
	
	/** The sensor scheduler. */
	private final SensorScheduler sensorScheduler = new SensorScheduler();
	
	/**
	 * Instantiates a new JBot impl.
	 *
	 * @param providedType the provided type
	 * @param movementProcessor the movement processor
	 * @param sensors the sensors
	 */
	@SuppressWarnings("unchecked")
    public JBotImpl(Class<T> providedType, MovementProcessor movementProcessor, Sensor<? extends T>... sensors) {
	    super(providedType);
		this.movementProcessor = movementProcessor;
		for(Sensor<? extends T> sensor : sensors) {
		    ((Sensor<T>)sensor).addListener(new DataEventRaiser<>(providedType, this));
			this.sensors.put(sensor.getInfo().getName(), sensor);
			sensorScheduler.addSensor(sensor);
		}
		sensorScheduler.start();
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.JBot#getMovementProcessor()
	 */
	@Override
	public MovementProcessor getMovementProcessor() {
		return movementProcessor;
	}
	
	/**
	 * Read data.
	 *
	 * @param <R> the generic type
	 * @param sensor the sensor
	 * @param dataType the data type
	 * @return the r
	 */
	@SuppressWarnings("unchecked")
	public <R extends T> R readData(String sensor, Class<R> dataType) {
		Object data = sensors.get(sensor).readData();
		if (!dataType.isAssignableFrom(data.getClass())) {
			throw new IllegalArgumentException();
		}
		return (R) data;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.JBot#getSensor(java.lang.String, java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <R extends T> Sensor<R> getSensor(String sensor, Class<R> sensorDataType) {
		Sensor<?> foundSensor = sensors.get(sensor);
		if (foundSensor == null) {
			return null;
		}
		if (!sensorDataType.isAssignableFrom(foundSensor.getProvidedDataType())) {
			throw new IllegalArgumentException();
		}
		return (Sensor<R>) foundSensor;
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProviderImpl#close()
	 */
	@Override
	public void close() throws Exception {
	    sensorScheduler.close();
	}
}
