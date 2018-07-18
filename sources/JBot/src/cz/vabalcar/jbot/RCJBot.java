package cz.vabalcar.jbot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataListener;
import cz.vabalcar.jbot.events.DataProviderInfo;
import cz.vabalcar.jbot.moving.MovementDataEvent;
import cz.vabalcar.jbot.moving.MovementProcessor;
import cz.vabalcar.jbot.moving.MovementProcessorActionListener;
import cz.vabalcar.jbot.networking.PointToPointNetworkConnection;
import cz.vabalcar.jbot.sensors.Sensor;
import cz.vabalcar.jbot.serialization.DeserializingThread;
import cz.vabalcar.jbot.serialization.SerializingDataListener;

/**
 * The Class RCJBot.
 *
 * @param <T> the generic type
 */
public class RCJBot<T extends Serializable> implements JBot<T> {
    
    /** The inner JBot. */
    private final JBot<T> innerJBot;
    
    /** The auto close inner JBot. */
    private final boolean autoCloseInnerJBot;
    
    /** The connection. */
    private PointToPointNetworkConnection connection;
    
    /** The deserializer. */
    private DeserializingThread deserializer = new DeserializingThread();
    
    /** The serializer. */
    private SerializingDataListener<T> serializer;
    
    /**
     * Instantiates a new RCJBot.
     *
     * @param jBot the JBot
     */
    public RCJBot(JBot<T> jBot) {
        this(jBot, false);
    }
    
    /**
     * Instantiates a new RCJBot.
     *
     * @param jBot the JBot
     * @param autoCloseJBot the auto close JBot
     */
    public RCJBot(JBot<T> jBot, boolean autoCloseJBot) {
        autoCloseInnerJBot = autoCloseJBot;
        innerJBot = jBot;
    }
    
    /**
     * Wait for connection.
     *
     * @param port the port
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void waitForConnection(int port) throws IOException {
        connection = new PointToPointNetworkConnection(port);
        boolean success = connection.acceptConnection();
        if (success) {
            enableStreaming(connection.getInputStream(), connection.getOutputStream());
        }
    }
    
    /**
     * Enable streaming.
     *
     * @param inputStream the input stream
     * @param outputStream the output stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void enableStreaming(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (deserializer.getState() != Thread.State.NEW) {
            deserializer = new DeserializingThread();
        }
        deserializer.setInputStream(inputStream);
        deserializer.addStreamListener(MovementDataEvent.class, new MovementProcessorActionListener(innerJBot.getMovementProcessor()));
        deserializer.start();
        serializer = new SerializingDataListener<>(getProvidedDataType(), outputStream);
        addListener(serializer);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#hasStarted()
     */
    @Override
    public boolean hasStarted() {
        return innerJBot.hasStarted();
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#isAlive()
     */
    @Override
    public boolean isAlive() {
        return innerJBot.isAlive();
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#getProvidedDataType()
     */
    @Override
    public Class<T> getProvidedDataType() {
        return innerJBot.getProvidedDataType();
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#getInfo()
     */
    @Override
    public DataProviderInfo<T> getInfo() {
        return innerJBot.getInfo();
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#addListener(cz.vabalcar.jbot.events.DataListener)
     */
    @Override
    public boolean addListener(DataListener<? extends T> listener) {
        return innerJBot.addListener(listener);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#removeListener(cz.vabalcar.jbot.events.DataListener)
     */
    @Override
    public boolean removeListener(DataListener<? extends T> listener) {
        return innerJBot.removeListener(listener);
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#raiseDataEvent(cz.vabalcar.jbot.events.DataEvent)
     */
    @Override
    public boolean raiseDataEvent(DataEvent<? extends T> event) {
        return innerJBot.raiseDataEvent(event);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.JBot#getMovementProcessor()
     */
    @Override
    public MovementProcessor getMovementProcessor() {
        return innerJBot.getMovementProcessor();
    }
    
    /**
     * Checks if is connected.
     *
     * @return true, if is connected
     */
    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.JBot#getSensor(java.lang.String, java.lang.Class)
     */
    @Override
    public <R extends T> Sensor<R> getSensor(String sensor, Class<R> sensorDataType) {
        return innerJBot.getSensor(sensor, sensorDataType);
    }
    
    /**
     * Adds the interruption listener.
     *
     * @param listener the listener
     */
    public void addInterruptionListener(Callable<Void> listener) {
        deserializer.addInterruptionListener(listener);
    }
    
    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
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
