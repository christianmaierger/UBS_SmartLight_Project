package de.cmlab.ubicomp;

import de.cmlab.ubicomp.lib.SensorUDPReceiver;
import de.cmlab.ubicomp.lib.model.AndroidSensor;

import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbServices;
import java.awt.*;


public class AndroidTest {
	private float lightValue=1;
	private boolean lightSwitched=false;

	public static void main(String[] args){
		/*initiate a receiver by defining a port
		number that will be sent to the receiver from the app*/
		SensorUDPReceiver receiver = new SensorUDPReceiver(5000);
		/*create a listener as shown below and let it implement Observer to
		to get the app updates*/
		SensorUDPListener listener = new SensorUDPListener();
		receiver.addObserver(listener);
	}

	/**
	 * This method prints the current value the ambient light sensor of a smortphone sents in lux
	 * According to the value it informs the user between which values the current one lies
	 *
	 **/
	public boolean adjustLightVolume(AndroidSensor sensorValues) {
		if (sensorValues.getAmbientlight() >= 0 && sensorValues.getAmbientlight() < 50) {
			if (lightSwitched==false) {
				lightSwitched=true;
				System.out.println("Now light value is over 0 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
			}

			return true;
		}


		if (sensorValues.getAmbientlight() >= 50 && sensorValues.getAmbientlight() < 200) {
			if (lightSwitched==false) {
				lightSwitched=true;
				System.out.println("Now light value is over 50 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
				return true;
				// wenn licht an ist und über 260 ca. kann mans ausmachen, hellgenug finde ich
			}


			if (lightSwitched==true && sensorValues.getAmbientlight() > 250) {
				//to turnout light
				lightSwitched=false;
				return true;
			}
		}

		System.out.println("============================");
		return false;
	}

	public void getLightVolume(AndroidSensor sensorValues) {
		this.lightValue=sensorValues.getAmbientlight();
	}
}
