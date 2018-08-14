package cz.vabalcar.jbot;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataProvider;
import cz.vabalcar.jbot.moving.MovementVisitor;
import cz.vabalcar.jbot.sensors.Sensor;

public interface JBot<T extends Serializable> extends DataProvider<T>  {
	MovementVisitor getMovementProcessor();
	<R extends T> Sensor<R> getSensor(String sensor, Class<R> sensorDataType);
}
