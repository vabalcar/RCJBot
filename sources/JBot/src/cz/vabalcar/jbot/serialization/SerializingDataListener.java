package cz.vabalcar.jbot.serialization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.UnknownDataEventRecogniser;

/**
 * The listener interface for receiving serializingData events.
 * The class that is interested in processing a serializingData
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSerializingDataListener</code> method. When
 * the serializingData event occurs, that object's appropriate
 * method is invoked.
 *
 * @param <T> the generic type
 */
public class SerializingDataListener<T extends Serializable> extends UnknownDataEventRecogniser<T> {
    
    /** The output stream. */
    private final ObjectOutputStream outputStream;
    
    /**
     * Instantiates a new serializing data listener.
     *
     * @param knownType the known type
     * @param outputStream the output stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SerializingDataListener(Class<T> knownType, OutputStream outputStream) throws IOException {
        super(knownType);
        this.outputStream = new ObjectOutputStream(outputStream);
        this.outputStream.flush();
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.events.DataListener#processDataEvent(cz.vabalcar.jbot.events.DataEvent)
     */
    @Override
    public boolean processDataEvent(DataEvent<? extends T> event) {
        try {
            outputStream.writeObject(event);
            outputStream.flush();
            outputStream.reset();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() throws Exception {
        outputStream.close();
    }

}
