package de.cmlab.ubicomp;

import de.cmlab.ubicomp.lib.SensorUDPReceiver;
import de.cmlab.ubicomp.lib.model.AndroidSensor;

import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbServices;
import java.awt.*;
import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class AndroidTest {
	private float lightValue;
	private boolean lightSwitched;
	BrightnessHelper brightnessHelper;
	ThinkLightHelper thinkLightHelper;
	BlockingQueue<InfoMessage> queue;
	long start;


	public AndroidTest() {
		float lightValue=1;
		lightSwitched=false;
		brightnessHelper = new BrightnessHelper();
		thinkLightHelper = new ThinkLightHelper();
		thinkLightHelper.checkForDLL();

		if(thinkLightHelper.isNoThinkLight()) {

		}

		queue = new ArrayBlockingQueue<>(5, true);
		start = System.currentTimeMillis();
	}

	public static void main(String[] args){
		/*initiate a receiver by defining a port
		number that will be sent to the receiver from the app*/

		//das ist ja die erste Klasse die erzeugt wird, die dann wiederum einen async ausgeführten thread startet, der ständig
		// per while true daten sendet
		SensorUDPReceiver receiver = new SensorUDPReceiver(5000);
		/*create a listener as shown below and let it implement Observer to
		to get the app updates*/
		SensorUDPListener listener = new SensorUDPListener();
		receiver.addObserver(listener);

		//irgendwann wäre ich hier durch
	}

	/**
	 * This method prints the current value the ambient light sensor of a smortphone sents in lux
	 * According to the value it informs the user between which values the current one lies
	 *
	 **/
	public void adjustLightVolume(AndroidSensor sensorValues) {

		// way to get results just every x milis, put the amount in the if clause
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		if (timeElapsed>=1000) {
			start=System.currentTimeMillis();
		} else {
			return;
		}

		if (sensorValues.getAmbientlight() >= 0 && sensorValues.getAmbientlight() < 50) {

			if (lightSwitched==false) {
				lightSwitched=true;
				thinkLightHelper.switchThinkLight();
				brightnessHelper.setBrightness(100);
				System.out.println("Now light value is over 0 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
				return;
			}
			if (lightSwitched==true) {
				System.out.println(lightSwitched +"Licht ist an und unter 50");
				return;
			}


		}

		if (sensorValues.getAmbientlight() >= 50 && sensorValues.getAmbientlight() < 200) {
			if (lightSwitched==false) {
				lightSwitched=true;
				thinkLightHelper.switchThinkLight();
				System.out.println("Now light value is over 50 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
				return;
				// wenn licht an ist und über 260 ca. kann mans ausmachen, hellgenug finde ich
			}
			if (lightSwitched==true) {
				System.out.println(lightSwitched +"Licht ist an und über 50-200");
			}


			if (lightSwitched==true && sensorValues.getAmbientlight() > 200) {
				//to turnout light
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
				lightSwitched=false;
				thinkLightHelper.switchThinkLight();
				brightnessHelper.setBrightness(75);
				System.out.println(lightSwitched +"Licht ist aus");
				return;
			}
		}

		System.out.println("============================");
		System.out.println("Licht ist  : " + lightSwitched);
		return;
	}

	public void getLightVolume(AndroidSensor sensorValues) {
		this.lightValue=sensorValues.getAmbientlight();
	}
}
