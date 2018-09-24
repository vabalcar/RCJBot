package cz.vabalcar.rcjbotcontroller;

/**
 * This class provides an interface to load the last connected JBot's name and port (if any) and store new ones.
 */
public class JBotConfiguration {
    
    /** key of the configuration property containing name on last controlled JBot's name */
    private static final String NAME = "jbot.name";
    
    /** key of the configuration property containing name on last controlled JBot's port */
    private static final String PORT = "jbot.port";
    
    /** the last verified name */
    private String lastVerifiedName;
    
    /** the last verified port */
    private String lastVerifiedPort;
    
    /** the parsed port number. */
    private int portNumber;
    
    /**
     * Gets the JBot's name.
     *
     * @return the JBot's name
     */
    public String getName() {
        return ConfigurationManager.instance().get(NAME);
    }
    
    /**
     * Sets the JBot's name.
     *
     * @param name the JBot's name
     */
    public void setName(String name) {
        if (name != lastVerifiedName && !verifyName(name)) {
            throw new IllegalArgumentException();
        }
        ConfigurationManager.instance().set(NAME, name);
    }
    
    /**
     * Gets the port.
     *
     * @return the port
     */
    public String getPort() {
        return ConfigurationManager.instance().get(PORT);
    }
    
    /**
     * Sets the port.
     *
     * @param port the String containing just a port number
     */
    public void setPort(String port) {
        if (port != lastVerifiedPort && !verifyName(port)) {
            throw new IllegalArgumentException();
        }
        ConfigurationManager.instance().set(PORT, port);
    }
    
    /**
     * Gets the port number.
     *
     * @return the port number
     */
    public int getPortNumber() {
        return portNumber;
    }
    
    /**
     * Verifies that the first of given Strings contains a possible name (hostname) of a JBot and the second
     * contains just a port number. 
     *
     * @param name the name to verify
     * @param port the port to verify
     * @return true, if verification was successful
     */
    public boolean verify(String name, String port) {
        return verifyName(name) && verifyPort(port);
    }
    
    /**
     * Verifies that given String contains a possible name (hostname) of a JBot. 
     *
     * @param name the name
     * @return true, if verification was successful
     */
    public boolean verifyName(String name) {
        boolean isNameVerified = name != null && !name.isEmpty();
        if (isNameVerified) {
            lastVerifiedName = name;
        }
        return isNameVerified;
    }
    
    /**
     * Verifies that given String contains just a port number.
     *
     * @param port the port
     * @return true, if verification was successful
     */
    public boolean verifyPort(String port) {
        try {
            int parsedInt = Integer.parseInt(port);
            if (parsedInt < 0 || parsedInt > 65535) {
                return false;
            }
            portNumber = parsedInt;
        } catch (NumberFormatException e) {
            return false;
        }
        lastVerifiedPort = port;
        return true;
    }
    
}
