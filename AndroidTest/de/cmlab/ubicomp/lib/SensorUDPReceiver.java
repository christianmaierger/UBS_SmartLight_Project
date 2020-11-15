package de.cmlab.ubicomp.lib;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Array;
import java.util.Observable;

import de.cmlab.ubicomp.util.*;

import de.cmlab.ubicomp.lib.model.AndroidSensor;

public class SensorUDPReceiver extends Observable {
	DatagramSocket serverSocket;
	byte[] receivedData; 
	DatagramPacket receivePacket;
	
	public SensorUDPReceiver(int portnum){
		try {
			serverSocket = new DatagramSocket(portnum);
			receivedData = new byte[1024];
			Thread thread = new Thread(){
				public void run(){
					System.out.println("Starting Server...");
		            while(true)
		               {
		            	  receivePacket = new DatagramPacket(receivedData, receivedData.length);
		                  try {
							serverSocket.receive(receivePacket);
							AndroidSensor sensor = new AndroidSensor(receivePacket.getAddress().getHostAddress());
			                sensor.setAcceleration(BytesToSensorValueConverter.extractAccelData(receivedData));
			                sensor.setGravity(BytesToSensorValueConverter.extractGravityData(receivedData));
			                sensor.setRotation(BytesToSensorValueConverter.extractRotationData(receivedData));
			                sensor.setOrientation(BytesToSensorValueConverter.extractOrientationData(receivedData));
			                sensor.setTouchedButtons(BytesToSensorValueConverter.extractTouchedButtons(receivedData));
			                sensor.setAmbientlight(BytesToSensorValueConverter.extractAmbientLightData(receivedData));
			                sensor.setProximity(BytesToSensorValueConverter.extractProximityData(receivedData));
			                setChanged();
			                //notify observers for change
			                notifyObservers(sensor);
						} catch (IOException e) {
							
						}
		               }
					}
			};
			thread.start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
	
