package de.cmlab.ubicomp.sensor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/*g6 erweiterung*/
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
	
	private float ambientlight;
	private DatagramSocket serverSocket;
    private byte[] receivedData; 
    private DatagramPacket receivePacket;
	
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
		this.datatype = "Float";
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
		
		try{
            //get sensor data from SensorUdp app via udp packages
            serverSocket = new DatagramSocket(8888);
			receivedData = new byte[1024];
            float preValue = -1;
            while (active) {
                receivePacket = new DatagramPacket(receivedData, receivedData.length);
                try{
                    serverSocket.receive(receivePacket);
                    this.setAmbientlight(extractAmbientLightData(receivedData));
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    String dateStamp = df.format(new Date());
                    //create new event, if sensor value changed
                    if(preValue != getAmbientlight()){
                        String light = String.format("%f", this.getAmbientlight());
                        preValue = getAmbientlight();
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
                    
                } catch (IOException e) {
							
                }    
            }
        } catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /** copied from BytesToSensorValueConverter.java, assignment 1 --> look there for detailed infos
     *
     */
	public static float extractAmbientLightData(byte[] receivedData){
		float[] light = extractingData(56, 59, receivedData,4);
        return light[0];
	}
	
	/** copied from BytesToSensorValueConverter.java, assignment 1 --> look there for detailed infos
     *
     */
	private static float[] extractingData(int startindex, int endindex, byte[] receivedData, int step )
	{
		int counter = 0;
		int bigCounter = 0;
		byte[] packet = new  byte[4];
		double size = (((float)endindex-(float)startindex)+1)/4;
		int resultsize = (int)Math.ceil(size);
		float[] result = new float[resultsize];
		
		for(int i=startindex; i<endindex+1; i++){
			if(counter < step-1){
				packet[counter] = receivedData[i];
				counter++;
			}else{
				float num = ByteBuffer.wrap(packet).order(ByteOrder.BIG_ENDIAN).getFloat();
				result[bigCounter] = num;
				counter = 0;
				packet = new  byte[4];
				bigCounter++;
			}
		}
		return result;
	}
	
    /**
	 * @return the ambientlight
	 */
	public float getAmbientlight() {
		return ambientlight;
	}

	/**
	 * @param ambientlight the ambientlight to set
	 */
	public void setAmbientlight(float ambientlight) {
		this.ambientlight = ambientlight;
	}
}
