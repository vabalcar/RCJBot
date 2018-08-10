package cz.vabalcar.jbot.networking;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RepeatingBroadcaster extends Broadcaster {
	
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private final long period;
	private final TimeUnit unit;
	private ScheduledFuture<?> currentBroadcast;
	
	public RepeatingBroadcaster(int localPort, int remotePort, long period, TimeUnit unit) throws SocketException, UnknownHostException {
		super(localPort, remotePort);
		this.period = period;
		this.unit = unit;
	}
	
	public RepeatingBroadcaster(int localPort, int remotePort) throws SocketException, UnknownHostException {
		this(localPort, remotePort, 250, TimeUnit.MILLISECONDS);
	}
	
	public void broadcastRepeatedly(final byte[] data) {
		if (currentBroadcast != null) {
			currentBroadcast.cancel(true);
		}
		currentBroadcast = executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				broadcast(data);
			}
		}, 0, period, unit);
	}
	
	@Override
	public void close() throws SocketException {
		if (currentBroadcast != null) {
			currentBroadcast.cancel(true);
		}
		executor.shutdownNow();
		super.close();
	}
}
