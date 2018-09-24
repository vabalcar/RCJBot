package cz.vabalcar.jbot.ev3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import lejos.hardware.Keys;
import lejos.hardware.ev3.LocalEV3;

public class EV3KeysMonitor implements AutoCloseable {
	private Runnable keysScan = new Runnable() {
		private final Keys ev3Keys = LocalEV3.get().getKeys();
		private int lastButtonState = ev3Keys.getButtons();
		
		@Override
		public void run() {
			synchronized (internalLock) {
				int buttonState = ev3Keys.readButtons();
				if (lastButtonState != buttonState) {
					lastButtonState = buttonState;
					while(!callbacks.isEmpty()) {
						try {
							callbacks.poll().call();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	};
	
	private final Queue<Callable<?>> callbacks = new LinkedList<>();
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private final Object internalLock = new Object();
	private final ScheduledFuture<?> readingTask;
	
	public void onNextAnyButtonPress(Callable<?> callback) {
		synchronized (internalLock) {
			callbacks.add(callback);
		}
	}
	
	public EV3KeysMonitor(long readingPeriod) {
		readingTask = executor.scheduleAtFixedRate(keysScan, 0, readingPeriod, TimeUnit.MILLISECONDS);
	}
	
	public EV3KeysMonitor() {
		this(50);
	}
	
	public void close() {
		readingTask.cancel(true);
		executor.shutdownNow();
	}
}
