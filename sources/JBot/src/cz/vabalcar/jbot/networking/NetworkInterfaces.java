package cz.vabalcar.jbot.networking;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkInterfaces {
	public static List<NetworkInterface> getAvailablePhysicalNIs() {
		Enumeration<NetworkInterface> nis = null;
		List<NetworkInterface> availablePhysicalNIs = new ArrayList<>();
		try {
			nis = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return null;
		}
		NetworkInterface ni;
		while (nis.hasMoreElements()) {
			ni = nis.nextElement();
			if (ni.isVirtual() || !ni.getInetAddresses().hasMoreElements()) {
				continue;
			}
			availablePhysicalNIs.add(ni);
		}
		return availablePhysicalNIs;
	}
	
	public static List<InetAddress> getBroadcastAddresses() {
		List<NetworkInterface> availablePhysicalNIs = getAvailablePhysicalNIs();
		if (availablePhysicalNIs == null) {
			return null;
		}
		List<InetAddress> broadcastAdresses = new ArrayList<>();
		for (NetworkInterface ni : availablePhysicalNIs) {
			Enumeration<InetAddress> niAddresses = ni.getInetAddresses(); 
			InetAddress niAddress;
			while (niAddresses.hasMoreElements()) {
				niAddress = niAddresses.nextElement();
	            if (niAddress instanceof Inet4Address) {
	            	byte[] niRawAddress = niAddress.getAddress();
	            	niRawAddress[3] = (byte) 255;
	                try {
						broadcastAdresses.add(InetAddress.getByAddress(niRawAddress));
					} catch (UnknownHostException e) {
						// This should not happen.
					}
	            }
	        }
		}
		return broadcastAdresses;
	}
	
	public static List<SocketAddress> getBroadcastSocketAddresses(int remotePort) {
		List<InetAddress> broadcastAddresses = getBroadcastAddresses();
		if (broadcastAddresses == null) {
			return null;
		}
		List<SocketAddress> broadcastSocketAddresses = new ArrayList<>();
		for(InetAddress broadcastAddress : broadcastAddresses) {
			broadcastSocketAddresses.add(new InetSocketAddress(broadcastAddress, remotePort));
		}
		return broadcastSocketAddresses;
	}
	
	public static NetworkInterface getPhysicalNI(InetAddress address) {
		if (!(address instanceof Inet4Address)) {
			return null;
		}
		List<NetworkInterface> availablePhysicalNIs = getAvailablePhysicalNIs();
		if (availablePhysicalNIs == null) {
			return null;
		}
		byte[] rawAddress = address.getAddress();
		for (NetworkInterface ni : availablePhysicalNIs) {
			Enumeration<InetAddress> niAddresses = ni.getInetAddresses();
			InetAddress niAddress;
			while(niAddresses.hasMoreElements()) {
				niAddress = niAddresses.nextElement();
				if (niAddress instanceof Inet4Address) {
					byte[] rawNIAddress = niAddress.getAddress();
					boolean success = true;
					for (int i = 0; i < 3; i++) {
						if (rawAddress[i] != rawNIAddress[i]) {
							success = false;
							break;
						}
					}
					if (success) {
						return ni;
					}
				}
			}
		}
		return null;
	}
}
