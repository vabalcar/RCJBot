package cz.vabalcar.jbot.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataEventRaiser;
import cz.vabalcar.jbot.events.DataListener;
import cz.vabalcar.jbot.events.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class DeserializingThread.
 */
public class DeserializingThread extends Thread implements AutoCloseable {
	
	/** The streamlisteners. */
	private Map<Class<?>, DataListener<?>> streamlisteners = new HashMap<>();
	
	/** The input stream. */
	private ObjectInputStream inputStream;
	
	/** The outer interruption. */
	private boolean outerInterruption = true;
	
	/** The connection end listeners. */
	private List<Callable<Void>> connectionEndListeners = new ArrayList<>();
	
	public DeserializingThread() {
	}
	
	public DeserializingThread(DeserializingThread deserializingThread) {
		streamlisteners = deserializingThread.streamlisteners;
		connectionEndListeners = deserializingThread.connectionEndListeners;
	}
	
	/**
	 * Sets the input stream.
	 *
	 * @param inputStream the new input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void setInputStream(InputStream inputStream) throws IOException {
        this.inputStream = new ObjectInputStream(inputStream);
    }
	
	/**
	 * Adds the stream listener.
	 *
	 * @param <E> the element type
	 * @param <D> the generic type
	 * @param targetType the target type
	 * @param listener the listener
	 */
	public <E, D> void addStreamListener(Class<E> targetType, DataListener<?  extends D> listener) {
	    streamlisteners.put(targetType, listener);
	}
	
	/**
	 * Register data provider.
	 *
	 * @param <E> the element type
	 * @param <D> the generic type
	 * @param targetType the target type
	 * @param provider the provider
	 */
	public <E, D extends Serializable> void registerDataProvider(Class<E> targetType, DataProvider<D> provider) {
	    addStreamListener(targetType, new DataEventRaiser<>(provider));
	}
	
	/**
	 * Adds the interruption listener.
	 *
	 * @param listener the listener
	 */
	public void addInterruptionListener(Callable<Void> listener) {
	    connectionEndListeners.add(listener);
	}
	
	private boolean synced = false;
	private Semaphore syncSemaphore = new Semaphore(0);
	
	public boolean syncWithSerializer() {
		if (synced) {
			return true;
		}
		
		try {
			syncSemaphore.acquire();
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
	@Override
	public void run() {
	    if (inputStream == null) {
	        throw new IllegalStateException();
	    }
	    
		while(!interrupted()) {
		    Object readObject;
			try {
				readObject = inputStream.readObject();
			} catch (IOException | ClassNotFoundException e) {
				break;
			}
			
			if (!synced) {
				syncSemaphore.release();
				synced = true;
				continue;
			}
			
			if (!(readObject instanceof DataEvent<?>)) {
			    break;
			}
			
			DataListener<?> listener = streamlisteners.get(readObject.getClass());
			if (listener == null) {
			    break;
			}
			listener.processUnknownDataEvent((DataEvent<?>) readObject);
		}
		
		if (outerInterruption) {
		    for(Callable<Void> listener : connectionEndListeners) {
	            try {
	                listener.call();
	            } catch (Exception e) {
	                System.err.println(e);
	            }
	        }
		    outerInterruption = false;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
	    outerInterruption = false;
	    interrupt();
	    if (inputStream != null) {
	        inputStream.close();
	    }
	}
}
