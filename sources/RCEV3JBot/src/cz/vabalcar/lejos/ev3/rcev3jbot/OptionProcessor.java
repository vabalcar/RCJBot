package cz.vabalcar.lejos.ev3.rcev3jbot;

/**
 * The Class OptionProcessor.
 */
public class OptionProcessor {
    
    /** The Constant ROBOT_CONF_OPTION. */
    public static final String ROBOT_CONF_OPTION = "-c";
    
    /** The Constant PORT_CONF_OPTION. */
    public static final String PORT_CONF_OPTION = "-p";
    
    /** The robot configuration file. */
    private String robotConfigurationFile = "robot.conf";
    
    /** The port file. */
    private String portConfigurationFile = "port.conf";

    /**
     * Gets the robot configuration file.
     *
     * @return the robot configuration file
     */
    public String getRobotConfigurationFile() {
        return robotConfigurationFile;
    }
    
    /**
     * Gets the port file.
     *
     * @return the port file
     */
    public String getPortConfigurationFile() {
        return portConfigurationFile;
    }
    
    /**
     * Process.
     *
     * @param args the args
     * @return true, if successful
     */
    public boolean process(String[] args) {
        if (args.length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
            case ROBOT_CONF_OPTION:
                robotConfigurationFile = args[i + 1];
                break;
            case PORT_CONF_OPTION:
                portConfigurationFile = args[i + 1];
                break;
            default:
                return false;
            }
        }
        return true;
    }
}
