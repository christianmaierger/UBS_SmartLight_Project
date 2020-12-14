package de.cmlab.ubicomp.sensor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.xmlrpc.XmlRpcException;

import de.cmlab.ubicomp.common.SensationConnector;
import de.cmlab.ubicomp.common.SensationConnector.SensorException;
import de.cmlab.ubicomp.common.SensorEvent;

/**
 * Implementation of a AmbientLightSensor
 * 
 * @author group6
 * 
 */
public class AmbientLightSensor implements Runnable {

	private boolean active = false;
	private SensationConnector sensation;
	
	private String sensorID;
	private String sensorClass;
	private String description;
	private String locationID;
	private String owner;
	private String comment;
	private String activity;
	private String datatype;
	private String minValue;
	private String maxValue;
	
	private static final String rawSensorXML =  "<Sensor id=\"null\" class=\"null\">" 
																						+ "<Description>null</Description>" 
																						+ "<HardwareID />" + "<Command />" 
																						+ "<LocationID>null</LocationID>" 
																						+ "<Owner>null</Owner>" 
																						+ "<Comment>null</Comment>" 
																						+ "<AvailableSince></AvailableSince>" 
																						+ "<AvailableUntil></AvailableUntil>" 
																						+ "<SensorActivity activity=\"null\" />" 
																						+ "<NativeDataType>null</NativeDataType>" 
																						+ "<MinimumValue>null</MinimumValue>" 
																						+ "<MaximumValue>null</MaximumValue>" 
																						+ "</Sensor>";

	/**
	 * C'tor
	 * 
	 * @param sensation Connector to Sens-ation
	 */
	public AmbientLightSensor(SensationConnector sensation) {
		this.sensation = sensation;

		this.sensorID = "G6AmbientLight";
		this.sensorClass = "Light";
		this.description = "A simple light sensor";
		this.locationID = "WE5/01.045";
		this.owner = "Group6";
		this.comment = "Provides light intensity measurement in lux.";
		this.activity = "active";
		this.datatype = "String";
		this.minValue = "1.0";
		this.maxValue = "10000.0";
	}

	/**
	 * Activates the sensor (register + set active)
	 * 
	 * @throws XmlRpcException
	 * @throws IOException
	 * @throws SensorException
	 */
	public void activate() throws XmlRpcException, IOException, SensorException {
		// register sensor at Sens-ation
		String obtainedSensorID = sensation.registerSensor(this.getXML());
		if (!this.sensorID.equals(obtainedSensorID)){
			System.err.println("Sensor ID has changed! Original: " + this.sensorID + " New: " + obtainedSensorID + " (sensor was already registered in Sens-ation; NOT USING NEW SENSOR ID!)");
		}
		
		// activate sensor
		this.active = true;
	}

	/**
	 * Gets XML description of the sensor
	 * @return XML string according to the sensor XML of Sens-ation
	 */
	public String getXML() {
		String sensor;
		sensor = rawSensorXML.replaceFirst("null", sensorID);
		sensor = sensor.replaceFirst("null", sensorClass);
		sensor = sensor.replaceFirst("null", description);
		sensor = sensor.replaceFirst("null", locationID);
		sensor = sensor.replaceFirst("null", owner);
		sensor = sensor.replaceFirst("null", comment);
		sensor = sensor.replaceFirst("null", activity);
		sensor = sensor.replaceFirst("null", datatype);
		sensor = sensor.replaceFirst("null", minValue);
		sensor = sensor.replaceFirst("null", maxValue);
		return sensor;
	}

	/**
	 * Deactivates the sensor
	 */
	public void stop() {
		this.active = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (active) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			String dateStamp = df.format(new Date());
			// dice a lux value
			String light = this.randInt(1, 5000) + "." + this.randInt(1, 10);
			// create sensor event
			SensorEvent event = new SensorEvent(this.sensorID, dateStamp, light);
			try {
				// send to Sens-ation
				sensation.notify(event);
				// Sensor has a sampling interval of about 500ms
				Thread.sleep(500);
			} catch (Throwable e) {
				active = false;
				e.printStackTrace();
			}
		}
	}

	/**
	 * Random integer generator
	 * @param min lower bound
	 * @param max upper bound
	 * @return Random integer between lower and upper bound
	 */
	public int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
