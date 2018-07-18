package cz.vabalcar.lejos.ev3.rcev3jbot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

import lejos.hardware.ev3.LocalEV3;
import cz.vabalcar.jbot.RCJBot;
import cz.vabalcar.jbot.ev3.EV3JBot;
import cz.vabalcar.jbot.ev3.parsing.FormatException;
import cz.vabalcar.util.FloatArray;

/**
 * The Class Main.
 */
public class Main {
    
    /** The robot configuration file. */
    private static String robotConfigurationFile;
    
    /** The port file. */
    private static String portFile;
    
    /** The program interrupted by user. */
    private static boolean programInterruptedByUser;
    
    /**
     * Sets the program interrupted by user.
     *
     * @param value the new program interrupted by user
     */
    private static void setProgramInterruptedByUser(boolean value) {
        programInterruptedByUser = value;
    }
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println("running");
        LED.blinkingYellow();
        
        OptionProcessor optionProcessor = new OptionProcessor();
        optionProcessor.process(args);
        
        robotConfigurationFile = optionProcessor.getRobotConfigurationFile();
        portFile = optionProcessor.getPortFile();
        
        EV3JBot parsedEv3JBot = null;
        boolean initializationSuccessful = false;
        try {
            parsedEv3JBot = EV3JBot.fromFile(robotConfigurationFile);
            initializationSuccessful = true;
        } catch (FormatException e) {
            System.err.println("Configuration file has wrong format!");
        } catch (FileNotFoundException e) {
            System.err.println("Configuration file wasn't found!");
        } catch (IOException e) {
            System.err.println("Error while reading configuration file!");
        } catch (Exception e) {
            System.err.println(e);
        }
        
        final EV3JBot ev3JBot = initializationSuccessful ? parsedEv3JBot : null;
        
        if (ev3JBot == null) {
            LED.staticRed();
            System.out.println("Press any button to exit...");
            LocalEV3.ev3.getKeys().waitForAnyPress();
            LED.turnOff();
            return;
        } 
        
        boolean stop = false;
        while(!stop) {
            LED.staticYellow();
            try (final RCJBot<FloatArray> rcJBot = new RCJBot<>(ev3JBot, false)) {
                programInterruptedByUser = true;
                rcJBot.addInterruptionListener(new Callable<Void>() {                        
                    @Override
                    public Void call() throws Exception {
                        setProgramInterruptedByUser(false);
                        ev3JBot.interruptWaitingForKeyPress();
                        return null;
                    }
                });
                
                final PortLoader portLoader = new PortLoader();
                portLoader.load(portFile);
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LED.blinkingGreen();
                        try {
                            rcJBot.waitForConnection(portLoader.getPort());
                            LED.staticGreen();
                        } catch (IOException e) {
                        }
                    }
                }).start();
                
                ev3JBot.waitForAnyKeyPress();
                stop = programInterruptedByUser;
            } catch (Exception e) {
                LED.staticRed();
                System.err.println(e);
                e.printStackTrace();
                stop = true;
            }
        }
        
        try {
            ev3JBot.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        LED.turnOff();
    }
}
