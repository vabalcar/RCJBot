package cz.vabalcar.jbot;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Callable;

import cz.vabalcar.jbot.events.DataEventRaiser;
import cz.vabalcar.jbot.events.DataProviderImpl;
import cz.vabalcar.jbot.moving.MovementVisitor;
import cz.vabalcar.jbot.moving.MovementSerializer;
import cz.vabalcar.jbot.networking.PTPConnection;
import cz.vabalcar.jbot.networking.Role;
import cz.vabalcar.jbot.sensors.RemoteSensor;
import cz.vabalcar.jbot.sensors.Sensor;
import cz.vabalcar.jbot.sensors.SensorDataEvent;
import cz.vabalcar.jbot.serialization.DeserializingThread;

public class RemoteJBot<T extends Serializable> extends DataProviderImpl<T> implements JBot<T> {
    
    private DeserializingThread deserializer = new DeserializingThread();
    private PTPConnection connection;
    private MovementSerializer movementProcessor;
    
    private String name = null;

    public RemoteJBot(Class<T> providedType) {
        super(providedType);
    }
    
    public boolean connectTo(String jbotName, int port) throws IOException {
        connection = new PTPConnection(Role.CLIENT, port, port, jbotName);
        if (connection.connect()) {
            movementProcessor = new MovementSerializer(connection.getOutputStream());
            
            if (deserializer.getState() != Thread.State.NEW) {
                deserializer = new DeserializingThread(deserializer);
            }
            deserializer.setInputStream(connection.getInputStream());
            deserializer.registerDataProvider(SensorDataEvent.class, this);
            deserializer.start();
            name = jbotName;
            return true;
        } else {
            return false;
        }
    }
    
    public PTPConnection getConnection() {
		return connection;
	}
    
    @Override
	public String getName() {
		return name;
	}

    @Override
    public MovementVisitor getMovementProcessor() {
        return movementProcessor;
    }
    
    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }
    
    @Override
    public <R extends T> Sensor<R> getSensor(String sensor, Class<R> sensorDataType) {
        RemoteSensor<R> remoteSensor = new RemoteSensor<>(sensor, sensorDataType);
        addListener(new DataEventRaiser<>(remoteSensor));
        return remoteSensor;
    }
    
    public void addInterruptionListener(Callable<Void> listener) {
        deserializer.addInterruptionListener(listener);
    }
    
    @Override
    public void close() throws Exception {
    	if (deserializer != null) {
            deserializer.close();
        }
    	if (connection != null) {
    		connection.close();
    	}
        super.close();
    }
}
