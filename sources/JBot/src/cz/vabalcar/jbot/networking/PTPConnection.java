package cz.vabalcar.jbot.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class PTPConnection implements AutoCloseable {
    
    private final Role role;
	private final int localPort;
	private final int remotePort;
	private final byte[] broadcastMessage;
	
	private ServerSocket serverSocket;
	private Socket socket;
	private NetworkInterface usedNI;
	
	public PTPConnection(Role role, int localPort, int remotePort, String connectionName) {
        this.role = role;
		this.localPort = localPort;
		this.remotePort = remotePort;
		this.broadcastMessage = connectionName.getBytes();
    }
	
	public Role getRole() {
		return role;
	}
	
	public int getPort() {
        return localPort;
    }
	
	public Socket getSocket() {
        return socket;
    }
	
	public NetworkInterface getUsedNI() {
		return usedNI;
	}
	
	public InputStream getInputStream() throws IOException {
	    return socket == null ? null : socket.getInputStream();
	}
	
	public OutputStream getOutputStream() throws IOException {
	    return socket == null ? null : socket.getOutputStream();
	}
	
	public boolean isConnected() {
	    return socket != null && socket.isConnected();
	}
	
	public boolean connect() {
		switch (role) {
		case SERVER:
			if (!connectAsServer()) {
				return false;
			}
			break;
		case CLIENT:
			if (!connectAsClient()) {
				return false;
			}
			break;
		default:
			return false;
		}
		
		try {
	    	socket.setTcpNoDelay(true);
	    	usedNI = NetworkInterfaces.getPhysicalNI(socket.getLocalAddress());
	    	return true;
        } catch (SocketException e) {
        	return false;
        }
	}
	
	private boolean connectAsServer() {
		try(RepeatingBroadcaster broadcaster = new RepeatingBroadcaster(localPort, remotePort);
			ServerSocket serverSocket = new ServerSocket(localPort)) {
			broadcaster.broadcastRepeatedly(broadcastMessage);
			this.serverSocket = serverSocket;
		    System.out.println("Waiting on port " + localPort + " for client...");
		    socket = serverSocket.accept();
			return true;
		} catch (SocketException e) {
			return false;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			serverSocket = null;
		}
	}
	
	private boolean connectAsClient() {
		try(BroadcastReceiver receiver = new BroadcastReceiver(localPort)) {
		    System.out.println("Connecting...");
		    DatagramPacket receivedPacket = new DatagramPacket(new byte[broadcastMessage.length], broadcastMessage.length);
		    receiver.receive(receivedPacket);
		    if (Arrays.equals(receivedPacket.getData(), broadcastMessage)) {
		    	socket = new Socket(receivedPacket.getAddress(), remotePort);
				return true;
		    } else {
		    	System.out.println(new String(receivedPacket.getData()) + " vs " + new String (broadcastMessage));
		    }
		    return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void close() throws IOException {
	    if (serverSocket != null) {
	        serverSocket.close();
	    }
		if (socket != null) {
			socket.close();
		}
	}
}
