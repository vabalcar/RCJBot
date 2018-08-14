package cz.vabalcar.jbot.sensors;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SensorScheduler implements AutoCloseable {
	
	private final ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
	private final Queue<Sensor<?>> sensors = new LinkedList<>();
	
	private boolean running = false;
	
	public void add(Sensor<?> sensor) {
		if (running) {
			throw new IllegalStateException();
		}
		sensors.add(sensor);
	}
	
	public void start() {
		running = true;
		while (!sensors.isEmpty()) {
			final Sensor<?> sensor = sensors.poll();
			threadPool.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					sensor.update();
				}
			}, 0, sensor.getPeriod(), TimeUnit.MILLISECONDS);
		}
	}
	
	public void close() {
		threadPool.shutdownNow();
	}
}
