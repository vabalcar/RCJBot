package cz.vabalcar.jbot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataListener;
import cz.vabalcar.jbot.moving.MovementDataEvent;
import cz.vabalcar.jbot.moving.MovementVisitor;
import cz.vabalcar.jbot.moving.MovementListener;
import cz.vabalcar.jbot.networking.PTPConnection;
import cz.vabalcar.jbot.networking.Role;
import cz.vabalcar.jbot.sensors.Sensor;
import cz.vabalcar.jbot.serialization.DeserializingThread;
import cz.vabalcar.jbot.serialization.SerializingDataListener;

public class RCJBot<T extends Serializable> implements JBot<T> {
	
    private final JBot<T> innerJBot;
    private final boolean autoCloseInnerJBot;
    private PTPConnection connection;
    private DeserializingThread deserializer = new DeserializingThread();
    private SerializingDataListener<T> serializer;
    
    public RCJBot(JBot<T> jBot) {
        this(jBot, false);
    }
    
    public RCJBot(JBot<T> jBot, boolean autoCloseJBot) {
        autoCloseInnerJBot = autoCloseJBot;
        innerJBot = jBot;
    }
    
    public void waitForConnection(int port) {
    	connection = new PTPConnection(Role.SERVER, port, port, getName());
        if (connection.connect()) {
        	try {
        		enableStreaming(connection.getInputStream(), connection.getOutputStream());
        	} catch (IOException e) {
        		// Nothing to do here.
        	}
        }
    }
    
    private void enableStreaming(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (deserializer.getState() != Thread.State.NEW) {
            deserializer = new DeserializingThread();
        }
        deserializer.setInputStream(inputStream);
        deserializer.addStreamListener(MovementDataEvent.class, new MovementListener(innerJBot.getMovementProcessor()));
        deserializer.start();
        serializer = new SerializingDataListener<>(getProvidedDataType(), outputStream);
        addListener(serializer);
    }

    @Override
    public boolean hasStarted() {
        return innerJBot.hasStarted();
    }

    @Override
    public boolean isAlive() {
        return innerJBot.isAlive();
    }
    
    @Override
    public Class<T> getProvidedDataType() {
        return innerJBot.getProvidedDataType();
    }
    
    @Override
    public String getName() {
        return innerJBot.getName();
    }

    @Override
    public boolean addListener(DataListener<? extends T> listener) {
        return innerJBot.addListener(listener);
    }

    @Override
    public boolean removeListener(DataListener<? extends T> listener) {
        return innerJBot.removeListener(listener);
    }
    
    @Override
    public boolean raiseDataEvent(DataEvent<? extends T> event) {
        return innerJBot.raiseDataEvent(event);
    }

    @Override
    public MovementVisitor getMovementProcessor() {
        return innerJBot.getMovementProcessor();
    }
    
    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    @Override
    public <R extends T> Sensor<R> getSensor(String sensor, Class<R> sensorDataType) {
        return innerJBot.getSensor(sensor, sensorDataType);
    }
    
    public void addInterruptionListener(Callable<Void> listener) {
        deserializer.addInterruptionListener(listener);
    }
    
    @Override
    public void close() throws Exception {
        if (deserializer != null) {
            deserializer.close();
        }
        if (serializer != null) {
            serializer.close();
            removeListener(serializer);
        }
        if (connection != null) {
            connection.close();
        }
        if (autoCloseInnerJBot && innerJBot != null) {
            innerJBot.close();
        }
    }
}
