package cz.vabalcar.lejos.ev3.rcev3jbot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import cz.vabalcar.jbot.RCJBot;
import cz.vabalcar.jbot.ev3.EV3JBot;
import cz.vabalcar.jbot.ev3.EV3KeysMonitor;
import cz.vabalcar.jbot.ev3.parsing.FormatException;
import cz.vabalcar.util.FloatArray;
import lejos.hardware.ev3.LocalEV3;

public class Main {
    
	private static int port;
    private static EV3JBot ev3JBot;
    private static boolean programTerminatedByUser = false;
    private static Semaphore semaphore = new Semaphore(0);
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    
    public static void main(String[] args) {
        System.out.println("running");
        
        OptionProcessor optionProcessor = new OptionProcessor();
        optionProcessor.process(args);
        
        String portConfigurationFile = optionProcessor.getPortConfigurationFile();
        String robotConfigurationFile = optionProcessor.getRobotConfigurationFile();
        
        boolean initializationSucessful = initialize(portConfigurationFile, robotConfigurationFile);
        
        if (initializationSucessful) {
            start();
        }
        
        dispose();
    }
    
    private static boolean initialize(String portConfigurationFile, String robotConfigurationFile) {
    	LED.blinkingYellow();
    	PortLoader portLoader = new PortLoader();
        try {
			portLoader.load(portConfigurationFile);
			port = portLoader.getPort();
		} catch (FormatException e1) {
			notifyError("Port configuration file has wrong format!");
			return false;
		} catch (IOException e1) {
			notifyError("Error while reading port configuration file!");
			return false;
		}
        
        try {
        	ev3JBot = EV3JBot.fromFile(robotConfigurationFile);
        } catch (FormatException e) {
            notifyError("Configuration file has wrong format!");
            return false;
        } catch (FileNotFoundException e) {
        	notifyError("Configuration file wasn't found!");
            return false;
        } catch (IOException e) {
        	notifyError("Error while reading configuration file!");
            return false;
        } catch (Exception e) {
        	notifyError(e.getMessage());
            return false;
        }
        return true;
    }
    
    private static void start() {
    	while(!programTerminatedByUser) {
            LED.staticYellow();
            try (RCJBot<FloatArray> rcJBot = new RCJBot<>(ev3JBot, false);
            	 EV3KeysMonitor keysMonitor = new EV3KeysMonitor()) {
                
            	keysMonitor.onNextAnyButtonPress(new Callable<Void>() {
            		@Override
            		public Void call() {
            			programTerminatedByUser = true;
            			semaphore.release();
            			return null;
            		}
            	});
            	
            	rcJBot.addInterruptionListener(new Callable<Void>() {                 
                    @Override
                    public Void call() {
                    	semaphore.release();
                        return null;
                    }
                });
                
                executor.execute(new Runnable() {
					@Override
					public void run() {
						LED.blinkingGreen();
		                rcJBot.waitForConnection(port);
		                if (rcJBot.isConnected()) {
		                	LED.staticGreen();
		                }
					}
				});
                
                semaphore.acquire();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    private static void notifyError(String message) {
    	LED.staticRed();
    	System.err.println(message);
    	System.out.println("Press any key to continue...");
    	LocalEV3.ev3.getKeys().waitForAnyPress();
    }
    
    private static void dispose() {
    	if (ev3JBot != null) {
    		LED.blinkingRed();
    		try {
				ev3JBot.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	executor.shutdownNow();
        LED.turnOff();
    }
}
