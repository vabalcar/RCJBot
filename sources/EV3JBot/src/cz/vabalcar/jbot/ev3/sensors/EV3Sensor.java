package cz.vabalcar.jbot.ev3.sensors;

import cz.vabalcar.jbot.sensors.SensorImpl;
import cz.vabalcar.util.FloatArray;
import lejos.hardware.sensor.BaseSensor;

public class EV3Sensor extends SensorImpl<FloatArray> {
	private final BaseSensor sensor;
	private final FloatArray buffer;

	public EV3Sensor(BaseSensor sensor, int frequency) {
	    super(sensor.getClass().getSimpleName(), FloatArray.class, frequency);
		this.sensor = sensor;
		this.buffer = new FloatArray(sensor.sampleSize());
	}
	
	@Override
	public void initialize() {
		// Nothing to do here.
	}
	
	@Override
	public FloatArray readData() {
		sensor.fetchSample(buffer.getArray(), 0);
		return buffer;
	}

	@Override
	public void close() throws Exception {
	    super.close();
	    sensor.close();
	}
}
