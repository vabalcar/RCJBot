package cz.vabalcar.jbot.ev3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.vabalcar.jbot.ev3.moving.EV3MovementProcessor;
import cz.vabalcar.jbot.ev3.parsing.EV3JBotParser;
import cz.vabalcar.jbot.ev3.parsing.FormatException;
import cz.vabalcar.jbot.ev3.sensors.EV3Sensor;
import cz.vabalcar.jbot.moving.MovementVisitor;
import cz.vabalcar.jbot.JBotImpl;
import cz.vabalcar.util.FloatArray;

import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.BaseSensor;

public class EV3JBot extends JBotImpl<FloatArray> {
	
	private final long KEY_READING_PERIOD_MS = 50;
	private final EV3 ev3 = LocalEV3.get();
    
	private boolean waitingInterrupted = false;
    	
    public EV3JBot(List<WheelConfiguration> wheelsConfigurations, 
	        List<MotorConfigration> otherMotorsConfigurations, 
	        List<SensorConfiguration> sensorsConfigurations) {
		super(FloatArray.class, initializeMovementProcessor(wheelsConfigurations, otherMotorsConfigurations), initializeSensors(sensorsConfigurations));
	}
	
	private static MovementVisitor initializeMovementProcessor(List<WheelConfiguration> wheelsConfigurations,
	        List<MotorConfigration> otherMotorsConfigurations) {
	    if (wheelsConfigurations == null || wheelsConfigurations.size() == 0) {
	        return null;
	    }
		
	    if (wheelsConfigurations.size() == 2) {
	        WheelConfiguration leftWheelConfiguration, rightWheelConfiguration;
	        
	        if (wheelsConfigurations.get(0).getOffset() < wheelsConfigurations.get(1).getOffset()) {
	            leftWheelConfiguration = wheelsConfigurations.get(0);
	            rightWheelConfiguration = wheelsConfigurations.get(1);
	        } else {
	            leftWheelConfiguration = wheelsConfigurations.get(1);
	            rightWheelConfiguration = wheelsConfigurations.get(0);
	        }
	        
	        return new EV3MovementProcessor(leftWheelConfiguration, rightWheelConfiguration, otherMotorsConfigurations);
	    } else {
	        throw new IllegalArgumentException();
	    }
	}
	
	private static EV3Sensor[] initializeSensors(List<SensorConfiguration> sensorsConfigurations) {
	    if (sensorsConfigurations == null) {
	        sensorsConfigurations = new ArrayList<>();
	    }
		EV3Sensor[] sensors = new EV3Sensor[sensorsConfigurations.size()];
		for (int i = 0; i < sensors.length; i++) {
			BaseSensor sensor = SensorFactory.instance().create(sensorsConfigurations.get(i));
			sensors[i] = new EV3Sensor(sensor, sensorsConfigurations.get(i).getReadingFrequency());
		}
		return sensors;
	}
	
	public static EV3JBot fromFile(String file) throws FileNotFoundException, IOException, FormatException {
	    return new EV3JBotParser().parse(new FileReader(file));
	}
	
	@Override
	public String getName() {
		return ev3.getName();
	}
    
    public void waitForAnyKeyPress() {
        waitingInterrupted = false;
        int keyState = ev3.getKeys().readButtons();
        while(!waitingInterrupted) {
            try {
                Thread.sleep(KEY_READING_PERIOD_MS);
            } catch (InterruptedException e) {
                return;
            }
            if (keyState != ev3.getKeys().readButtons()) {
                return;
            }
        }
    }
    
    public void interruptWaitingForKeyPress() {
        waitingInterrupted = true;
    }
}
