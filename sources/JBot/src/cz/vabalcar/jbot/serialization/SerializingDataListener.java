package cz.vabalcar.jbot.serialization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataListenerImpl;

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
public class SerializingDataListener<T extends Serializable> extends DataListenerImpl<T> {
    
    private final ObjectOutputStream outputStream;
    
    public SerializingDataListener(Class<T> knownType, OutputStream outputStream) throws IOException {
        super(knownType);
        this.outputStream = new ObjectOutputStream(outputStream);
        this.outputStream.flush();
    }

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

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

}
