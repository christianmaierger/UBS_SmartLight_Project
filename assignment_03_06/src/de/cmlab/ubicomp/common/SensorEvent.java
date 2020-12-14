package de.cmlab.ubicomp.common;

/**
 * Container of a simple sensor event
 * 
 * @author Christoph Beckmann
 * TODO: make dateStamp as Calendar object
 */
public class SensorEvent {

	private String sensorID;
	private String dateStamp;
	private String value;

	/**
	 * C'tor 
	 * @param sensorID Identification string of the sensor
	 * @param dateStamp Date string of the sensor event
	 * @param value String contents of the sensor event
	 */
	public SensorEvent(String sensorID, String dateStamp, String value) {
		this.sensorID = sensorID;
		this.dateStamp = dateStamp;
		this.value = value;
	}

	/**
	 * Gets the identification string of the sensor
	 * @return the sensorID
	 */
	public String getSensorID() {
		return this.sensorID;
	}

	/**
	 * Gets the date string of the sensor event
	 * @return the dateStamp
	 */
	public String getDateStamp() {
		return this.dateStamp;
	}

	/**
	 * Gets the string contents of the sensor event
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * String representation of the sensor event
	 * @return String representation of the sensor event
	 */
	public String toString(){
		return this.sensorID+": "+this.value+" @"+this.dateStamp;
	}

}
