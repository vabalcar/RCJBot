package cz.vabalcar.android.rcjbotcontrollercommon;

import java.io.Serializable;
import java.net.Inet4Address;

import cz.vabalcar.jbot.RemoteJBot;
import cz.vabalcar.jbot.networking.NetworkInterfaces;

public class RemoteJBotInfo implements Serializable {
    /**
     * JBot's address
     */
    private final Inet4Address address;
    /**
     * JBot's name
     */
    private final String name;
    /**
     * Used NI's name
     */
    private final String usedNIName;

    /**
     * Just a contructor
     * @param remoteJBot a RemoteJBot to construct data from.
     */
    public RemoteJBotInfo(RemoteJBot<?> remoteJBot) {
        address = NetworkInterfaces.getIPv4Address(remoteJBot.getConnection().getUsedNI());
        name = remoteJBot.getName();
        usedNIName = remoteJBot.getConnection().getUsedNI().getDisplayName();
    }

    /**
     * Gets JBot's address in the context of current connection.
     * @return JBot's address
     */
    public Inet4Address getAddress() {
        return address;
    }

    /**
     * Gets JBot's name.
     * @return JBot's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of a network interface used to connect with the JBot.
     * @return the used NI's name
     */
    public String getUsedNIName() {
        return usedNIName;
    }
}
