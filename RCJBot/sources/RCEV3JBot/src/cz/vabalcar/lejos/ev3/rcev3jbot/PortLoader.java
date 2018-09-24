package cz.vabalcar.lejos.ev3.rcev3jbot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import cz.vabalcar.jbot.ev3.parsing.FormatException;

/**
 * The Class PortLoader.
 */
public class PortLoader {

    /** The port. */
    private int port = 0;
    
    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }
    
    /**
     * Load.
     *
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws FormatException the format exception
     */
    public void load(String file) throws IOException, FormatException {
        load(new FileReader(file));
    }
    
    /**
     * Load.
     *
     * @param reader the reader
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws FormatException the format exception
     */
    public void load(Reader reader) throws IOException, FormatException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String line = bufferedReader.readLine();
            if (line == null) {
                throw new FormatException();
            }
            int parsedNumber;
            try {
                parsedNumber = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new FormatException();
            }
            if (parsedNumber < 0 || parsedNumber > 65535) {
                throw new FormatException();
            }
            port = parsedNumber;
        } finally {
            bufferedReader.close();
        }
    }

}
