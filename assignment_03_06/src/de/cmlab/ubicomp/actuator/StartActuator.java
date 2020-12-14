package de.cmlab.ubicomp.actuator;

import java.io.IOException;

import org.apache.xmlrpc.XmlRpcException;

import de.cmlab.ubicomp.common.SensationConnector;
import de.cmlab.ubicomp.common.SensationConnector.SubscriptionException;

/**
 * Invokes the AmbientLightActuator
 * 
 * @author group6
 *
 */
public class StartActuator {

	/**
	 * Invokes the AmbientLightActuator
	 * 
	 * @param args
	 * @throws SubscriptionException
	 * @throws XmlRpcException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws SubscriptionException, XmlRpcException, IOException, InterruptedException {
		// connect to Sens-ation running on localhost:5000
		SensationConnector sensation = new SensationConnector("localhost", 5000);
		// start actuator server on localhost:8080
		AmbientLightActuator actuator = new AmbientLightActuator(sensation, 8080);
		// subscribe to sensor G6AmbientLight
		actuator.subscribe("G6AmbientLight");

		// Wait a while
		Thread.sleep(10000);

		// shutdown
		actuator.unsubscribe("G6AmbientLight");
		actuator.close();
	}
}
