package cz.vabalcar.jbot.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * The Class PointToPointNetworkConnection.
 */
public class PointToPointNetworkConnection implements AutoCloseable {
    
    /** The server socket. */
    private ServerSocket serverSocket;
	
	/** The socket. */
	private Socket socket;
	
	/** The port. */
	private final int port;
	
	/**
	 * Instantiates a new point to point network connection.
	 *
	 * @param port the port
	 */
	public PointToPointNetworkConnection(int port) {
        this.port = port;
    }
	
	/**
	 * Gets the remote socket.
	 *
	 * @return the remote socket
	 */
	public Socket getRemoteSocket() {
        return socket;
    }
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
        return port;
    }
	
	/**
	 * Gets the input stream.
	 *
	 * @return the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public InputStream getInputStream() throws IOException {
	    return socket == null ? null : socket.getInputStream();
	}
	
	/**
	 * Gets the output stream.
	 *
	 * @return the output stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public OutputStream getOutputStream() throws IOException {
	    return socket == null ? null : socket.getOutputStream();
	}
	
	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
	    return socket == null ? false : socket.isConnected();
	}
	
	/**
	 * Connect to.
	 *
	 * @param serverName the server name
	 * @return true, if successful
	 */
	public boolean connectTo(String serverName) {
		try {
		    System.out.println("Connecting to server...");
			socket = new Socket(serverName, port);
			initializeSocket(socket);
			System.out.println("Connected");
			return true;
		} catch (IOException e) {
		    return false;
		}
	}
	
	/**
	 * Accept connection.
	 *
	 * @return true, if successful
	 */
	public boolean acceptConnection() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
		    this.serverSocket = serverSocket;
		    System.out.println("Waiting on port " + port + " for client...");
		    socket = serverSocket.accept();
		    initializeSocket(socket);
		    this.serverSocket = null;
		    System.out.println("Client has connected");
			return true;
		} catch (IOException e) {
		    return false;
		}
	}
	
	/**
	 * Initialize socket.
	 *
	 * @param socket the socket
	 */
	private void initializeSocket(Socket socket) {
	    try {
            socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            // Nothing to do here
        }
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
	    if (serverSocket != null) {
	        serverSocket.close();
	    }
		if (socket != null) {
			socket.close();
		}
	}
}
