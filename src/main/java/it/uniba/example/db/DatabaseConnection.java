package it.uniba.example.db;

/**
 * This class is demo purpose only and must be deleted.
 */
public class DatabaseConnection {
	/**
	 * Database address.
	 */
	private String address;

	/**
	 * Database port.
	 */
	private int port;

	/**
	 * A value indicating whether the connection is alive or not.
	 */
	private boolean connected;

	/**
	 * Creates a connection to the database.
	 * 
	 * @param addr
	 *            server address
	 * @param prt
	 *            server port
	 */
	public DatabaseConnection(final String addr, final int prt) {
		this.address = addr;
		this.port = prt;
		this.connected = true;
	}

	/**
	 * Closes the connection.
	 */
	public void disconnect() {
		setConnected(false);
	}

	/**
	 * Set status of connection.
	 * 
	 * @param val
	 *            status of the connection as a boolean value
	 */
	protected void setConnected(final boolean val) {
		this.connected = val;
	}

	/**
	 * Returns the status of a connection.
	 * 
	 * @return <code>true</code> if connected, <code>false</code> otherwise
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Returns the server address.
	 * 
	 * @return the server address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the server address.
	 * 
	 * @param addr the server address
	 */
	protected void setAddress(final String addr) {
		this.address = addr;
	}

	/**
	 * The port where the server is listening.
	 * 
	 * @return the server port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port to connect to.
	 * 
	 * @param prt
	 *            the server port
	 */
	protected void setPort(final int prt) {
		this.port = prt;
	}

}
