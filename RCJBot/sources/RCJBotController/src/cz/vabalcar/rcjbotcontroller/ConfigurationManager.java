package cz.vabalcar.rcjbotcontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map.Entry;

import cz.vabalcar.util.Util;

/**
 * This class stores the configuration of the program on the memory  when the program is running and allows storing the configuration to the configuration file when the program stops.
 * ConfigurationManager implements the singleton pattern.
 */
public class ConfigurationManager {
    
    /** the hash map to store the configuration in */
    private HashMap<String, String> configuration = new HashMap<>();
    
    /** the name of a file to store configuration in (when the program is closed) */
    private String file = "RCJBotController.conf";
    
    /** the instance of ConfigurationManager */
    private static ConfigurationManager instance;
    
    /**
     * Get the instance of the ConfigurationManager. 
     *
     * @return the instance of configuration manager
     */
    public static ConfigurationManager instance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    /**
     * Instantiates a new configuration manager.
     */
    private ConfigurationManager() {
    }
    
    /**
     * Loads the configuration from the last used (during the run time of the program) configuration file. When no configuration file has been used yet, it uses default configuration file name "RCJBotController.conf".
     * The load operation extends current configuration by new properties and overwrites already existing properties with loaded values.
     *
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean load() throws IOException {
        if (new File(file).exists()) {
            load(file);
            return true;
        }
        return false;
    }
    
    /**
     * Loads the configuration from the given file. This file then becomes last used configuration file.
     * The load operation extends current configuration by new properties and overwrites already existing properties with loaded values.
     *
     * @param file the file to load configuration from
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void load(String file) throws IOException {
        this.file = file;
        load(new FileReader(file));
    }
    
    /**
     * Load the configuration from the given reader.
     * The load operation extends current configuration by new properties and overwrites already existing properties with loaded values.
     *
     * @param reader the reader to load configuration from
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void load(Reader reader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] tokens = Util.split(line, '=');
                if (tokens.length == 2) {
                    configuration.put(tokens[0], tokens[1]);
                }
            }
        }
    }
    
    /**
     * Gets the value of a property with the given key.
     *
     * @param key the key
     * @return a string which is the value of a property with the given key  
     */
    public String get(String key) {
        return configuration.get(key);
    }
    
    /**
     * Sets the value of property with the given key to given value.
     *
     * @param key the key of a property which value has to be changed
     * @param value the new value
     */
    public void set(String key, String value) {
        configuration.put(key, value);
    }
    
    /**
     * Sets the default value of a property with the given key to the given value.
     *
     * @param key the key of a property which default value has to be changed
     * @param value the default value
     */
    public void setDefault(String key, String value) {
        if (configuration.get(key) == null) {
            configuration.put(key, value);
        }
    }
    
    /**
     * Stores the configuration to the last used (during the run time of the program) configuration file. When no configuration file has been used yet, it uses default configuration file name "RCJBotController.conf".
     * This file is overwritten during the store operation.
     *
     * @throws IOException Signals that an I/O exception has occurred.     
     */
    public void store() throws IOException {
        System.out.println("saving configuration to " + file);
        store(file);
    }
    
    /**
     * Store the configuration to the given file.
     * This file is overwritten during the store operation.
     *
     * @param file the file to load configuration from
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void store(String file) throws IOException {
        store(new FileWriter(file));
    }
    
    /**
     * Stores the configuration using the given writer.
     *
     * @param writer the writer
     */
    public void store(Writer writer) {
        try (PrintWriter printWriter = new PrintWriter(writer)) {
            StringBuilder stringBuilder = new StringBuilder();
            for(Entry<String, String> entry : configuration.entrySet()) {
                String outputEntry = stringBuilder.append(entry.getKey()).append('=').append(entry.getValue()).toString();
                printWriter.println(outputEntry);
                stringBuilder.setLength(0);
            }
            printWriter.flush();
        }
    }
}
