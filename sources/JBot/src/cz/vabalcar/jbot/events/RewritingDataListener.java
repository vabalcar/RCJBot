package cz.vabalcar.jbot.events;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * The listener interface for receiving rewritingData events.
 * The class that is interested in processing a rewritingData
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addRewritingDataListener</code> method. When
 * the rewritingData event occurs, that object's appropriate
 * method is invoked.
 *
 * @param <T> the generic type
 */
public class RewritingDataListener<T> extends DataListenerImpl<T> {

	/** The output printer. */
	private final PrintWriter outputPrinter;
	
	/** The end message. */
	private final String endMessage;
	
	/** The message builder. */
	private final StringBuilder messageBuilder = new StringBuilder();
	
	/**
	 * Instantiates a new rewriting data listener.
	 *
	 * @param knownType the known type
	 * @param output the output
	 * @param initMessage the init message
	 * @param endMessage the end message
	 */
	public RewritingDataListener(Class<T> knownType, OutputStream output, String initMessage, String endMessage) {
		super(knownType);
		outputPrinter = new PrintWriter(output, true);
		if (initMessage != null) {
		    outputPrinter.println(initMessage);
		}
		this.endMessage = endMessage; 
	}
	
	/**
	 * Instantiates a new rewriting data listener.
	 *
	 * @param knownType the known type
	 * @param output the output
	 */
	public RewritingDataListener(Class<T> knownType, OutputStream output) {
		this(knownType, output, null, null);
	}

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataListenerImpl#dataReceived(cz.vabalcar.jbot.events.DataProviderInfo, java.lang.Object)
	 */
	@Override
	public <U extends T> boolean dataReceived(DataProviderInfo<? extends T> source, U data) {
	    messageBuilder.setLength(0);
	    messageBuilder.append(source.getName()).append(": ").append(data);
	    outputPrinter.println(messageBuilder);
        return true;
	}

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataListenerImpl#dataProviderDied()
	 */
	@Override
	public void dataProviderDied() {
		if (endMessage != null) {
		    outputPrinter.println(endMessage);
		}
	}

	/* (non-Javadoc)
	 * @see cz.vabalcar.jbot.events.DataListenerImpl#close()
	 */
	@Override
	public void close() throws Exception {
		outputPrinter.close();
	}

}
