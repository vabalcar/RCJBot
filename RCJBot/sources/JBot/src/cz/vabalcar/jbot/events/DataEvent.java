package cz.vabalcar.jbot.events;

import java.io.Serializable;

public interface DataEvent<T extends Serializable> extends Serializable {
	String getSourceName();
	T getData();
}
