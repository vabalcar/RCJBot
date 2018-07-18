package cz.vabalcar.jbot.sensors;

import cz.vabalcar.jbot.events.DataProviderInfo;

/**
 * The Class SensorInfo.
 *
 * @param <T> the generic type
 */
public class SensorInfo<T> implements DataProviderInfo<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;
    
    /**
     * Instantiates a new sensor info.
     *
     * @param name the name
     */
    public SensorInfo(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProviderInfo#getName()
     */
    @Override
    public String getName() {
        return name;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof SensorInfo<?>) {
            SensorInfo<?> sensorInfo = (SensorInfo<?>) object;
            return name.equals(sensorInfo.getName());
        }
        return false;
    }

}
