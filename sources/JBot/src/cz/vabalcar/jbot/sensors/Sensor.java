package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

import cz.vabalcar.jbot.events.DataProvider;

public interface Sensor<T extends Serializable> extends DataProvider<T> {
    int getPeriod();
	void initialize();
	T readData();
	void update();
}
