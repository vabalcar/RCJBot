package cz.vabalcar.jbot.events;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RewritingDataListener<T> extends DataListenerImpl<T> {

	private final PrintWriter outputPrinter;
	private final StringBuilder messageBuilder = new StringBuilder();
	
	public RewritingDataListener(Class<T> knownType, OutputStream output) {
		super(knownType);
		outputPrinter = new PrintWriter(output, true);
	}

	@Override
	public boolean processDataEvent(DataEvent<? extends T> event) {
		messageBuilder.setLength(0);
	    messageBuilder.append(event.getSourceName()).append(": ").append(event.getData());
	    outputPrinter.println(messageBuilder);
        return true;
	}
	
	@Override
	public void close() {
		outputPrinter.close();
	}
}
