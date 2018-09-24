package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataEvent;

public class RemoteSensor<T extends Serializable> extends SensorImpl<T> {
    private T lastReceivedData = null;
    
    public RemoteSensor(String name, Class<T> sensorDataType) {
        super(name, sensorDataType, 0);
    }
    
    @Override
	public void initialize() {
		// TODO Auto-generated method stub
	}

    @Override
    public T readData() {
        return lastReceivedData;
    }
    
    @Override
    public boolean raiseDataEvent(DataEvent<? extends T> event) {
        if (event.getSourceName().equals(getName())) {
            lastReceivedData = event.getData();
            return super.raiseDataEvent(event);
        } else {
            return false;
        }
    }
}
