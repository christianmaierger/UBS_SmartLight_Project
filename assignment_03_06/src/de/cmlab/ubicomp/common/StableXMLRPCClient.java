package de.cmlab.ubicomp.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Observable;

import org.apache.xmlrpc.WebServer;

/**
 * Implements the notification client for Sens-ation
 * 
 * @author Christoph Beckmann
 * 
 */
public class StableXMLRPCClient extends Observable {

	WebServer server;
	int port;

	/**
	 * C'tor
	 */
	public StableXMLRPCClient(int port) {
		this.port = port;
		server = new WebServer(this.port);
		server.addHandler("StableXMLRPCClient", this);
		server.start();
	}

	/**
	 * Notification method called form Sens-ation
	 * 
	 * @param sensorID
	 *          identification string of the sensor
	 * @param dateStamp
	 *          Date string of the sensor event
	 * @param value
	 *          String contents of the sensor event
	 * @return OK on successful notification
	 */
	public String notify(String sensorID, String dateStamp, String value) {
		setChanged();
		notifyObservers(new SensorEvent(sensorID, dateStamp, value));
		return "";
	}

	/**
	 * Gets the local IPv4 address the Web-Server is running on
	 * 
	 * @return String of local IPv4 address
	 */
	public String getIp() {
		String ipAddress = null;
		Enumeration<NetworkInterface> net = null;
		try {
			net = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}

		while (net.hasMoreElements()) {
			NetworkInterface element = net.nextElement();
			Enumeration<InetAddress> addresses = element.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress ip = addresses.nextElement();
				if (ip instanceof Inet4Address) {
					if (ip.isSiteLocalAddress()) {
						ipAddress = ip.getHostAddress();
					}
				}
			}
		}
		return ipAddress;
	}

	/**
	 * Gets the local port the Web-Server is running on
	 * 
	 * @return  Port number
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * Shuts down the server
	 */
	public void close() {
		this.server.shutdown();
	}
}
