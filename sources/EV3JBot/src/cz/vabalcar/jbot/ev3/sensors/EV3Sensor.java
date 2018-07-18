package cz.vabalcar.jbot.ev3.sensors;

import cz.vabalcar.jbot.sensors.NonInitializingSensorImpl;
import cz.vabalcar.util.FloatArray;
import lejos.hardware.sensor.BaseSensor;

/**
 * The Class EV3Sensor.
 */
public class EV3Sensor extends NonInitializingSensorImpl<FloatArray> {
	
	/** The sensor. */
	private final BaseSensor sensor;
	
	/** The buffer. */
	private final FloatArray buffer;
	
	/**
	 * Instantiates a new EV 3 sensor.
	 *
	 * @param sensor the sensor
	 * @param frequency the frequency
	 */
	public EV3Sensor(BaseSensor sensor, int frequency) {
	    super(sensor.getClass().getSimpleName(), FloatArray.class, frequency);
		this.sensor = sensor;
		this.buffer = new FloatArray(sensor.sampleSize());
	}
	
	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.sensors.Sensor#readData()
	 */
	@Override
	public FloatArray readData() {
		sensor.fetchSample(buffer.getArray(), 0);
		return buffer;
	}

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataProviderImpl#close()
	 */
	@Override
	public void close() throws Exception {
	    super.close();
	    sensor.close();
	}
}
