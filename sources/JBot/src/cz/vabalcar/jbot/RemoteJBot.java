package cz.vabalcar.jbot;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Callable;

import cz.vabalcar.jbot.events.DataEventRaiser;
import cz.vabalcar.jbot.events.DataProviderImpl;
import cz.vabalcar.jbot.events.DataProviderInfo;
import cz.vabalcar.jbot.moving.MovementProcessor;
import cz.vabalcar.jbot.moving.RemoteMovementProcessor;
import cz.vabalcar.jbot.networking.PTPConnection;
import cz.vabalcar.jbot.networking.Role;
import cz.vabalcar.jbot.sensors.RemoteSensor;
import cz.vabalcar.jbot.sensors.Sensor;
import cz.vabalcar.jbot.sensors.SensorDataEvent;
import cz.vabalcar.jbot.serialization.DeserializingThread;

/**
 * The Class RemoteJBot.
 *
 * @param <T> the generic type
 */
public class RemoteJBot<T extends Serializable> extends DataProviderImpl<T> implements JBot<T> {
    
    /** The deserializer. */
    private DeserializingThread deserializer = new DeserializingThread();
    
    /** The connection. */
    private PTPConnection connection;
    
    /** The movement processor. */
    private RemoteMovementProcessor movementProcessor;

    /**
     * Instantiates a new remote JBot.
     *
     * @param providedType the provided type
     */
    public RemoteJBot(Class<T> providedType) {
        super(providedType);
    }
    
    /**
     * Connect to.
     *
     * @param jbotName the jbot name
     * @param port the port
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean connectTo(String jbotName, int port) throws IOException {
        this.connection = new PTPConnection(Role.CLIENT, port, port, jbotName);
        if (connection.connect()) {
            movementProcessor = new RemoteMovementProcessor(connection.getOutputStream());
            
            if (deserializer.getState() != Thread.State.NEW) {
                deserializer = new DeserializingThread();
            }
            deserializer.setInputStream(connection.getInputStream());
            deserializer.registerDataProvider(SensorDataEvent.class, this);
            deserializer.start();
            return true;
        } else {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#getInfo()
     */
    @Override
    public DataProviderInfo<T> getInfo() {
        return null;
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.JBot#getMovementProcessor()
     */
    @Override
    public MovementProcessor getMovementProcessor() {
        return movementProcessor;
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
        RemoteSensor<R> remoteSensor = new RemoteSensor<>(sensor, sensorDataType);
        addListener(new DataEventRaiser<>(sensorDataType, remoteSensor));
        return remoteSensor;
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
     * @see cz.vabalcar.jbot.events.DataProviderImpl#close()
     */
    @Override
    public void close() throws Exception {
        if (deserializer != null) {
            deserializer.close();
        }
        super.close();
    }
}
