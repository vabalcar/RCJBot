package cz.vabalcar.jbot.moving;

import cz.vabalcar.jbot.events.DataEventImpl;
import cz.vabalcar.jbot.events.DataProvider;

public class MovementDataEvent<T extends Movement> extends DataEventImpl<T> {
    
    private static final long serialVersionUID = 1L;

    public MovementDataEvent(DataProvider<T> source, T data) {
        super(source, data);
    }
}