package de.cmlab.ubicomp.sensor;

import java.io.IOException;

import org.apache.xmlrpc.XmlRpcException;

import de.cmlab.ubicomp.common.SensationConnector;
import de.cmlab.ubicomp.common.SensationConnector.SensorException;

/**
 * Invokes the AmbientLightSensor
 * @author group6
 *
 */
public class StartSensor {

	/**
	 * Invokes the AmbientLightSensor
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws XmlRpcException
	 * @throws IOException
	 * @throws SensorException
	 */
	public static void main(String[] args) throws InterruptedException, XmlRpcException, IOException, SensorException {
		// connect to Sens-ation running on localhost:5000
		SensationConnector sensation = new SensationConnector("localhost", 5000);
		// create sensor
		AmbientLightSensor sensor = new AmbientLightSensor(sensation);
		// register sensor and start running
		sensor.activate();
		Thread t = new Thread(sensor);
		t.start();
		
		// Wait a while
		Thread.sleep(10000);
		
		// Shutdown
		sensor.stop();
	}

}
