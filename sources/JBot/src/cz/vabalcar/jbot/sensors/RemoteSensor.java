package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataEvent;

/**
 * The Class RemoteSensor.
 *
 * @param <T> the generic type
 */
public class RemoteSensor<T extends Serializable> extends NonInitializingSensorImpl<T> {
    
    /** The last received data. */
    private T lastReceivedData = null;
    
    /**
     * Instantiates a new remote sensor.
     *
     * @param sensor the sensor
     * @param sensorDataType the sensor data type
     */
    public RemoteSensor(String sensor, Class<T> sensorDataType) {
        super(sensor, sensorDataType);
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProviderImpl#raiseDataEvent(cz.vabalcar.jbot.events.DataEvent)
     */
    @Override
    public boolean raiseDataEvent(DataEvent<? extends T> event) {
        if (event.getSourceInfo().equals(getInfo())) {
            lastReceivedData = event.getData();
            return super.raiseDataEvent(event);
        } else {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.sensors.Sensor#readData()
     */
    @Override
    public T readData() {
        return lastReceivedData;
    }
}
