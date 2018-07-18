package cz.vabalcar.jbot.moving;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * The Class RemoteMovementProcessor.
 */
public class RemoteMovementProcessor extends MovementProcessorImpl implements AutoCloseable {
    
    /** The info. */
    private final MovementProcessorInfo info = new MovementProcessorInfo("RemoteMovementProcessor");
    
    /** The object output stream. */
    private final ObjectOutputStream objectOutputStream;

    /**
     * Instantiates a new remote movement processor.
     *
     * @param outputStream the output stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public RemoteMovementProcessor(OutputStream outputStream) throws IOException {
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.flush();
    }
    
    /**
     * Serialize.
     *
     * @param <T> the generic type
     * @param movementProcessorAction the movement processor action
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected <T extends MovementProcessorAction> void serialize(T movementProcessorAction) throws IOException {
        objectOutputStream.writeObject(new MovementDataEvent<>(info, movementProcessorAction));
        objectOutputStream.flush();
        objectOutputStream.reset();
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.AngularAccelerationChange)
     */
    @Override
    public void apply(AngularAccelerationChange angularAccelerationChange) {
        try {
            serialize(angularAccelerationChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.AngularSpeedChange)
     */
    @Override
    public void apply(AngularSpeedChange angularSpeedChange) {
        try {
            serialize(angularSpeedChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.LinearAccelerationChange)
     */
    @Override
    public void apply(LinearAccelerationChange linearAccelerationChange) {
        try {
            serialize(linearAccelerationChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.LinearSpeedChange)
     */
    @Override
    public void apply(LinearSpeedChange linearSpeedChange) {
        try {
            serialize(linearSpeedChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#apply(cz.vabalcar.jbot.moving.MinRadiusChange)
     */
    @Override
    public void apply(MinRadiusChange minRadiusChange) {
        try {
            serialize(minRadiusChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.MotorMovement)
     */
    @Override
    public void process(MotorMovement motorMovement) {
        try {
            serialize(motorMovement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.ArcTranslation)
     */
    @Override
    public void process(ArcTranslation arcTranslation) {
        try {
            serialize(arcTranslation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.LinearTranslation)
     */
    @Override
    public void process(LinearTranslation linearTranslation) {
        try {
            serialize(linearTranslation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.Rotation)
     */
    @Override
    public void process(Rotation rotation) {
        try {
            serialize(rotation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.moving.MovementProcessor#process(cz.vabalcar.jbot.moving.Stop)
     */
    @Override
    public void process(Stop stop) {
        try {
            serialize(stop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() throws Exception {
        objectOutputStream.close();
    }

}
