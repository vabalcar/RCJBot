package cz.vabalcar.jbot.moving;

import cz.vabalcar.jbot.events.DataProviderInfo;

/**
 * The Class MovementProcessorInfo.
 */
public class MovementProcessorInfo implements DataProviderInfo<MovementProcessorAction> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private final String name;
    
    /**
     * Instantiates a new movement processor info.
     *
     * @param name the name
     */
    public MovementProcessorInfo(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProviderInfo#getName()
     */
    @Override
    public String getName() {
        return name;
    }

}
