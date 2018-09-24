package cz.vabalcar.jbot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cz.vabalcar.jbot.events.DataEventRaiser;
import cz.vabalcar.jbot.events.DataProviderImpl;
import cz.vabalcar.jbot.moving.MovementVisitor;
import cz.vabalcar.jbot.sensors.Sensor;
import cz.vabalcar.jbot.sensors.SensorScheduler;

public abstract class JBotImpl<T extends Serializable> extends DataProviderImpl<T> implements JBot<T> {
    
    private MovementVisitor movementProcessor;
	private Map<String, Sensor<?>> sensors = new HashMap<>();
	private final SensorScheduler sensorScheduler = new SensorScheduler();
	
	@SuppressWarnings("unchecked")
    public JBotImpl(Class<T> providedType, MovementVisitor movementProcessor, Sensor<? extends T>... sensors) {
	    super(providedType);
		this.movementProcessor = movementProcessor;
		for(Sensor<? extends T> sensor : sensors) {
		    ((Sensor<T>)sensor).addListener(new DataEventRaiser<>(this));
			this.sensors.put(sensor.getName(), sensor);
			sensorScheduler.add(sensor);
		}
		sensorScheduler.start();
	}
	
	@Override
	public MovementVisitor getMovementProcessor() {
		return movementProcessor;
	}
	
	@SuppressWarnings("unchecked")
	public <R extends T> R readData(String sensor, Class<R> dataType) {
		Object data = sensors.get(sensor).readData();
		if (!dataType.isAssignableFrom(data.getClass())) {
			throw new IllegalArgumentException();
		}
		return (R) data;
	}
	
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
	
	@Override
	public void close() throws Exception {
	    sensorScheduler.close();
	}
}
