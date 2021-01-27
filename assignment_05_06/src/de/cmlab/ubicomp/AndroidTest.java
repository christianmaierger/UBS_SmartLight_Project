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
	long start;


	//todo customize more for indivudal users and/or make availible for more os and/or systems in general, like lighted keyboards
	//or other company`s laptops with builtin leds
	/**
	 * Class acts as Actuator to filter and interpret the sensor input ragrding the light value, it changes the state of a
	 * ThinkLight, that is a Led built inside many ThinkPads, a highly used notebook by companies, and also adjusts the screen
	 * brightness of ms windows systems a bit
	 */
	public AndroidTest() {
		float lightValue=1;
		lightSwitched=false;
		brightnessHelper = new BrightnessHelper();
		thinkLightHelper = new ThinkLightHelper();
		//check for space of file to be executed to control ThinkLight
		thinkLightHelper.checkForDLL();

		
			//todo move functionallity to exclude method calls from LightHelper over here
		   //so we free up the resources if device is not a thinkpad or does not have necessry features installed
		if(thinkLightHelper.isNoThinkLight()) {

		}

		start = System.currentTimeMillis();
	}

	//todo gui is in the making
	/**
	 * Just main class to start whole process
	 */
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
	 * This method interprets the light values sent by a smartphone via udp every second and adjusts the screen brightness
	 * on windows systems and controls the led built into ThinkPads
	 *
	 * params sensorValues the values send via UDP from the sensor/smartphone
	 **/
	public boolean adjustLightVolume(AndroidSensor sensorValues) {

		// way to get results just every x milis, put the amount in the if clause
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		if (timeElapsed>=1000) {
			start=System.currentTimeMillis();
			System.out.println("Schon Zeit und start ist jetzt" + start);
		} else {
			System.out.println(finish-start);
			return true;
		}


		//todo clean up and remove printouts that help debugging the code
		if (sensorValues.getAmbientlight() >= 0 && sensorValues.getAmbientlight() < 50) {

			if (lightSwitched==false) {
				lightSwitched=true;
				thinkLightHelper.switchThinkLight();
				brightnessHelper.setBrightness(100);
				System.out.println("Now light value is over 0 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());

			}
			if (lightSwitched==true) {
				System.out.println(lightSwitched +"Licht ist an und unter 50");
			}

			return true;
		}

		if (sensorValues.getAmbientlight() >= 50 && sensorValues.getAmbientlight() < 200) {
			if (lightSwitched==false) {
				lightSwitched=true;
				thinkLightHelper.switchThinkLight();
				System.out.println("Now light value is over 50 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
				return true;
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
				return true;
			}
		}

		System.out.println("============================");
		System.out.println("Licht ist  : " + lightSwitched);
		return false;
	}

	public void getLightVolume(AndroidSensor sensorValues) {
		this.lightValue=sensorValues.getAmbientlight();
	}
}
