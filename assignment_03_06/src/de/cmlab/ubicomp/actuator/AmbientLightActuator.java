package de.cmlab.ubicomp.actuator;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.apache.xmlrpc.XmlRpcException;

import de.cmlab.ubicomp.common.SensationConnector;
import de.cmlab.ubicomp.common.SensationConnector.SubscriptionException;
import de.cmlab.ubicomp.common.SensorEvent;
import de.cmlab.ubicomp.common.StableXMLRPCClient;

/**
 * Implementation of a AmbientLightActuator
 * 
 * @author group6
 * 
 */
public class AmbientLightActuator implements Observer {

	private SensationConnector sensation;
	private StableXMLRPCClient actuatorClient;

	/**
	 * C'tor
	 * 
	 * @param sensation Connector to Sens-ation
	 * @param port Local port to start internal Web-server
	 */
	public AmbientLightActuator(SensationConnector sensation, int port) {
		this.sensation = sensation;
		this.actuatorClient = new StableXMLRPCClient(port);
		this.actuatorClient.addObserver(this);
	}

	/**
	 * Subscribes to Sens-ation
	 * 
	 * @param sensorID Identification of the sensor
	 * @throws SubscriptionException
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public void subscribe(String sensorID) throws SubscriptionException, XmlRpcException, IOException {
		String hostname = this.actuatorClient.getIp();
		String port = new Integer(this.actuatorClient.getPort()).toString();
        if(hostname == null){
            hostname = "localhost";
        }
		this.sensation.subscribe(sensorID, hostname, port);
	}

	/**
	 * Unsubscribes from Sens-ation
	 * 
	 * @param sensorID Identification of the sensor
	 * @throws XmlRpcException
	 * @throws IOException
	 * @throws SubscriptionException
	 */
	public void unsubscribe(String sensorID) throws XmlRpcException, IOException, SubscriptionException {
		String hostname = this.actuatorClient.getIp();
		this.sensation.unsubscribe(sensorID, hostname);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SensorEvent) {
			SensorEvent event = (SensorEvent) arg;
			System.out.println("### Received sensor event: " + event);
			printValues(event);
		} else {
			System.err.print("Received unknown object of type: " + arg.getClass().getSimpleName());
		}
	}
	/**
	 * Prints information for the user according to input value
	 */ 
	public void printValues(SensorEvent event){
		float currentValue = Float.parseFloat(event.getValue());
		
		if(currentValue >= 0 && currentValue < 20){
			System.out.println("At least there is some light");
			System.out.println("the current value is: " + event.getValue());
		}
		if(currentValue >= 20 && currentValue < 200){
			System.out.println("Now light value is over 20 lux");
			System.out.println("the current value is: " + event.getValue());
		}
		if(currentValue >= 200 && currentValue < 300){
			System.out.println("Now light value is over 200 lux");
			System.out.println("the current value is: " + event.getValue());
		}
		if(currentValue >= 300){
			System.out.println("there is enough light for you");
			System.out.println("the current value is: " + event.getValue());
		}
	} 
	/**
	 * Closes the internal web server -- required for shutdown
	 */
	public void close() {
		this.actuatorClient.close();
	}
}
