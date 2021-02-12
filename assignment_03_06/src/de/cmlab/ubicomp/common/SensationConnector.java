package de.cmlab.ubicomp.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

/**
 * Wrapper class for accessing Sens-ation
 * 
 * @author Christoph Beckmann
 * 
 */
public class SensationConnector {
	
	/**
	 * Exception for failures while subscribing / unsubscribing
	 * @author Christoph Beckmann
	 *
	 */
	public class SubscriptionException extends Exception {

		private static final long serialVersionUID = -7370997122471375637L;
		
		/**
		 * C'tor
		 * @param sensorID Identification of the sensor
		 * @param reason Description of the failure
		 */
		public SubscriptionException(String sensorID, String reason) {
			super("Subscription for " + sensorID + " failed: " + reason);
		}
	}
	
	/**
	 * Exception for failures on a sensor
	 * 
	 * @author Christoph Beckmann
	 *
	 */
	public class SensorException extends Exception {

		private static final long serialVersionUID = 7694357158573455651L;

		/**
		 * C'tor
		 * @param xml XML representation of the sensor
		 * @param reason Description of the failure
		 */
		public SensorException(String xml, String reason) {
			super("Registration for " + xml + " failed: " + reason);
		}
	}
	
	/**
	 * Exception for failures while transmitting a sensor event
	 * 
	 * @author Christoph Beckmann
	 *
	 */
	public class NotificationException extends Exception {

		private static final long serialVersionUID = 4566487091922379784L;

		/**
		 * C'tor
		 * @param event Sensor event causing the failure
		 * @param reason Description of the failure
		 */
		public NotificationException(SensorEvent event, String reason) {
			super("Notification for " + event + " failed: " + reason);
		}
	}

	private XmlRpcClient client;
	private Vector<String> params;

	/**
	 * C'tor -- initiates connection client 
	 * @param hostname Fully qualified hostname or IPv4 address of the Sens-ation host
	 * @param port Port number for Sens-ation
	 * @throws MalformedURLException
	 */
	public SensationConnector(String hostname, int port) throws MalformedURLException {
		client = new XmlRpcClient("http://" + hostname + ":" + port);
		params = new Vector<String>();
	}

	/**
	 * Subscribes a client to Sens-ation; the client implements a StableXMLRPCClient on a host and port
	 * 
	 * @param sensorID Identification of the sensor to subscribe to
	 * @param hostname Host name of the StableXMLRPCClient
	 * @param port Port of the StableXMLRPCClient
	 * @throws IOException 
	 * @throws XmlRpcException 
	 * @throws Exception 
	 */
	public synchronized void subscribe(String sensorID, String hostname, String port) throws SubscriptionException, XmlRpcException, IOException {
		params.clear();
		params.add(hostname);
		params.add(sensorID);
		params.add(port);
		
		Object result = client.execute("GatewayXMLRPC.register", params);
		if (result instanceof String) {
			if (((String) result).equals("done")) {
				System.out.println("Subscribed to Sensor: " + sensorID);
			} else {
				throw new SubscriptionException(sensorID, (String) result);
			}
		} else {
			throw new SubscriptionException(sensorID, "false return type");
		}
	}

	/**
	 * Revokes subscription of a client
	 * 
	 * @param sensorID Identification of the sensor to subscribe to
	 * @param hostname Host name of the StableXMLRPCClient
	 * @throws XmlRpcException
	 * @throws IOException
	 * @throws SubscriptionException
	 */
	public void unsubscribe(String sensorID, String hostname) throws XmlRpcException, IOException, SubscriptionException {
		params.clear();
		params.add(hostname);
		params.add(sensorID);
		
		Object result = client.execute("GatewayXMLRPC.unregister", params);
		if (result instanceof String) {
			if (((String) result).equals("done")) {
				System.out.println("Unsubscribed to Sensor: " + sensorID);
			} else {
				throw new SubscriptionException(sensorID, (String) result);
			}
		} else {
			throw new SubscriptionException(sensorID, "false return type");
		}
	}

	/**
	 * Creates a new sensor at Sens-ation
	 * 
	 * @param xml XML description of the sensor
	 * @return Identification of sensor at Sens-ation's side
	 * @throws XmlRpcException
	 * @throws IOException
	 * @throws SensorException
	 */
	public String registerSensor(String xml) throws XmlRpcException, IOException, SensorException {
		params.clear();
		params.addElement(xml);
		
		Object result = client.execute("SensorPort.registerSensor", params);
		if (result instanceof String) {
				System.out.println("Registered sensor as: " + result);
				return (String) result;
		} else {
			throw new SensorException(xml, "false return type");
		}
	}

	/**
	 * Notifies Sens-ation of a new {@link SensorEvent}
	 * 
	 * @param event The event occurred
	 * @throws XmlRpcException
	 * @throws IOException
	 * @throws NotificationException
	 */
	public void notify(SensorEvent event) throws XmlRpcException, IOException, NotificationException {
		params.clear();
		params.add(event.getSensorID());
		params.add(event.getDateStamp());
		params.add(event.getValue());
		
		Object result = client.execute("SensorPort.notify", params);
		if (result instanceof Boolean) {
			if (!(Boolean) result) {
				throw new NotificationException(event, "false result");
			}
		} else {
			throw new NotificationException(event, "false return type");
		}
		System.out.println("Notified Sens-ation about: " + event);
	}

}
