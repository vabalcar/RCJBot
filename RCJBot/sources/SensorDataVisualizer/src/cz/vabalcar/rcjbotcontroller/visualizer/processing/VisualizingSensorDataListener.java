package cz.vabalcar.rcjbotcontroller.visualizer.processing;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataListenerImpl;

import cz.vabalcar.util.FloatArray;

/**
 * This class is pusher of read data from a DataProvider and if the data are read from 
 * EV3IRSensor, EV3TouchSensor or EV3ColorSensor, the data gets visualized by a SensorDataVisualizer.
 */
public class VisualizingSensorDataListener extends DataListenerImpl<FloatArray> {    
    
	private final SensorDataProcessor sensorDataProcessor;

    /**
     * Instantiates a new VisualizingSensorDataListener.
     *
     * @param visualizer the visualizer to work with
     */
    public VisualizingSensorDataListener(SensorDataProcessor processor) {
        super(FloatArray.class);
        sensorDataProcessor = processor;
    }

    @Override
    public boolean processDataEvent(DataEvent<? extends FloatArray> event) {
        sensorDataProcessor.process(event.getSourceName(), event.getData().getArray());
        return true;
    }

	@Override
	public void close() {
		// Nothing to do here.
	}

}
