package cz.vabalcar.jbot.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Broadcaster implements AutoCloseable {	
	private final DatagramSocket localSocket;
	private final int remotePort;
	
	private List<SocketAddress> broadcastSocketsAddresses = new ArrayList<>();
	
	public Broadcaster(int localPort, int remotePort) throws SocketException, UnknownHostException {
		localSocket = new DatagramSocket(localPort);
		localSocket.setBroadcast(true);
		this.remotePort = remotePort;
		scanNIs();
	}
	
	public boolean scanNIs() {
		List<SocketAddress> broadcastSocketsAddresses = NetworkInterfaces.getBroadcastSocketAddresses(remotePort);
		if (broadcastSocketsAddresses == null) {
			return false;
		}
		this.broadcastSocketsAddresses = broadcastSocketsAddresses;
		return true;
	}
	
	public boolean broadcast(byte[] data) {
		boolean success = true;
		for(SocketAddress broadcastSocketAddress : broadcastSocketsAddresses) {
			success = broadcast(data, broadcastSocketAddress);
			if (!success) {
				break;
			}
		}
		return success;
	}
	
	public boolean broadcast(byte[] data, SocketAddress broadcastSocketAddress) {
		try {
			localSocket.send(new DatagramPacket(data, data.length, broadcastSocketAddress));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void close() throws SocketException {
		localSocket.close();
	}
}
