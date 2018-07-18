package cz.vabalcar.jbot.sensors;

import java.io.Serializable;

/**
 * The Class NonInitializingSensorImpl.
 *
 * @param <T> the generic type
 */
public abstract class NonInitializingSensorImpl<T extends Serializable> extends SensorImpl<T> {

    /**
     * Instantiates a new non initializing sensor impl.
     *
     * @param name the name
     * @param providedType the provided type
     * @param frequency the frequency
     */
    public NonInitializingSensorImpl(String name, Class<T> providedType, int frequency) {
        super(name, providedType, frequency);
    }

    /**
     * Instantiates a new non initializing sensor impl.
     *
     * @param name the name
     * @param providedType the provided type
     */
    public NonInitializingSensorImpl(String name, Class<T> providedType) {
        super(name, providedType);
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.sensors.Sensor#initialize()
     */
    @Override
    public void initialize() {
        // Nothing to do here.
    }
}
