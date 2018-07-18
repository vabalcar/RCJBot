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
import cz.vabalcar.jbot.events.DataProviderInfo;
import cz.vabalcar.jbot.JBotImpl;
import cz.vabalcar.jbot.moving.MovementProcessor;
import cz.vabalcar.util.FloatArray;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.BaseSensor;

/**
 * The Class EV3JBot.
 */
public class EV3JBot extends JBotImpl<FloatArray> {
    
    /** The waiting interrupted. */
    private boolean waitingInterrupted = false;
    
    /** The key reading period ms. */
    private long KEY_READING_PERIOD_MS = 50;
    
    /** The ev 3. */
    private EV3 ev3 = LocalEV3.get();
	
    
    /**
     * {@link }.
     *
     * @param wheelsConfigurations the wheels configurations
     * @param otherMotorsConfigurations the other motors configurations
     * @param sensorsConfigurations the sensors configurations
     */
	public EV3JBot(List<WheelConfiguration> wheelsConfigurations, 
	        List<MotorConfigration> otherMotorsConfigurations, 
	        List<SensorConfiguration> sensorsConfigurations) {
		super(FloatArray.class, initializeMovementProcessor(wheelsConfigurations, otherMotorsConfigurations), initializeSensors(sensorsConfigurations));
	}
	
	/**
	 * Initialize movement processor.
	 *
	 * @param wheelsConfigurations the wheels configurations
	 * @param otherMotorsConfigurations the other motors configurations
	 * @return the movement processor
	 */
	private static MovementProcessor initializeMovementProcessor(List<WheelConfiguration> wheelsConfigurations,
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
	
	/**
	 * Initialize sensors.
	 *
	 * @param sensorsConfigurations the sensors configurations
	 * @return the EV 3 sensor[]
	 */
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
	
	/**
	 * From file.
	 *
	 * @param file the file
	 * @return the EV 3 Jbot
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FormatException the format exception
	 */
	public static EV3JBot fromFile(String file) throws FileNotFoundException, IOException, FormatException {
	    return new EV3JBotParser().parse(new FileReader(file));
	}

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataProvider#getInfo()
     */
    @Override
    public DataProviderInfo<FloatArray> getInfo() {
        return null;
    }
    
    /**
     * Wait for any key press.
     */
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
    
    /**
     * Interrupt waiting for key press.
     */
    public void interruptWaitingForKeyPress() {
        waitingInterrupted = true;
    }
}
