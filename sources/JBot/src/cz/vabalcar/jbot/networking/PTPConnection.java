package cz.vabalcar.jbot.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class PTPConnection implements AutoCloseable {
    
    private final Role role;
	private final int localPort;
	private final int remotePort;
	private final byte[] broadcastMessage;
	
	private ServerSocket serverSocket;
	private Socket socket;
	private NetworkInterface usedNI;
	
	private final Semaphore stateSemaphore = new Semaphore(1);
	private BroadcastReceiver broadcastReceiver;
	private RepeatingBroadcaster broadcaster;
	
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
	    return socket != null && socket.isConnected() && !socket.isClosed();
	}
	
	
	public boolean connect() {
		try {
			stateSemaphore.acquire();
		} catch (InterruptedException e) {
			return false;
		}
		
		boolean success = false;
		
		switch (role) {
		case SERVER:
			success = connectAsServer();
			break;
		case CLIENT:
			success = connectAsClient();
			break;
		default:
			break;
		}
		
		if (success) {
			try {
		    	socket.setTcpNoDelay(true);
		    	usedNI = NetworkInterfaces.getPhysicalNI(socket.getLocalAddress());
	        } catch (SocketException e) {
	        	success = false;
	        }
		}
		
		stateSemaphore.release();
		return success;
	}
	
	private boolean connectAsServer() {
		try(RepeatingBroadcaster broadcaster = new RepeatingBroadcaster(localPort, remotePort);
			ServerSocket serverSocket = new ServerSocket(localPort)) {
			this.broadcaster = broadcaster;
			this.serverSocket = serverSocket;
			
			broadcaster.broadcastRepeatedly(broadcastMessage);
		    System.out.println("Waiting on port " + localPort + " for client...");
		    
		    stateSemaphore.release();
		    socket = serverSocket.accept();
		    stateSemaphore.acquire();
			
		    return isConnected();
		} catch (Exception e) {
			return false;
		} finally {
			broadcaster = null;
			serverSocket = null;
		}
	}
	
	private boolean connectAsClient() {
		try(BroadcastReceiver receiver = new BroadcastReceiver(localPort)) {
		    this.broadcastReceiver = receiver;
			
		    DatagramPacket receivedPacket = new DatagramPacket(new byte[broadcastMessage.length], broadcastMessage.length);
		    System.out.println("Connecting...");
		    
		    stateSemaphore.release();
		    receiver.receive(receivedPacket);
		    stateSemaphore.acquire();
		    
		    if (Arrays.equals(receivedPacket.getData(), broadcastMessage)) {
		    	socket = new Socket(receivedPacket.getAddress(), remotePort);
				return true;
		    } else {
		    	return false;
		    }
		} catch (Exception e) {
			return false;
		} finally {
			broadcastReceiver = null;
		}
	}
	
	@Override
	public void close() throws IOException {
		try {
			stateSemaphore.acquire();
		} catch (InterruptedException e) {
			// Just continue
		}
		if (broadcaster != null) {
			broadcaster.close();
		}
		if (broadcastReceiver != null) {
			broadcastReceiver.close();
		}
	    if (serverSocket != null) {
	        serverSocket.close();
	    }
		if (socket != null) {
			socket.close();
		}
		stateSemaphore.release();
	}
}
