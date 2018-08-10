package cz.vabalcar.jbot.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class BroadcastReceiver implements AutoCloseable {
	private final DatagramSocket socket;
	
	public BroadcastReceiver(int localPort) throws SocketException {
		socket = new DatagramSocket(localPort);
	}
	
	public boolean receive(DatagramPacket packet) {
		try {
			System.out.println("receiving at " + socket.getLocalSocketAddress().toString());
			socket.receive(packet);
			System.out.println("received " + new String(packet.getData()) + " from " + packet.getSocketAddress());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	@Override
	public void close() throws SocketException {
		socket.close();
	}
}
