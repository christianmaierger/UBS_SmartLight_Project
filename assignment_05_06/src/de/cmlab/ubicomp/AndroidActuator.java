package de.cmlab.ubicomp;

import de.cmlab.ubicomp.lib.SensorUDPReceiver;
import de.cmlab.ubicomp.lib.model.AndroidSensor;


import static com.sun.javafx.PlatformUtil.isWindows;
import static com.sun.javafx.util.Utils.isUnix;

//todo customize more for indivudal users and/or make availible for more os and/or systems in general, like lighted keyboards
//or other company`s laptops with builtin leds
/**
 * Class acts as Actuator to filter and interpret the sensor input ragrding the light value, it changes the state of a
 * ThinkLight, that is a Led built inside many ThinkPads, a highly used notebook by companies, and also adjusts the screen
 * brightness of ms windows systems a bit
 */

public class AndroidActuator {
	private boolean lightSwitched;
	private BrightnessHelper brightnessHelperWindows;
	private ThinkLightHelper thinkLightHelper;
	// checks for starting time of programm
	private long start;
	//check if linux or windows distribution is used, later versions will include other os
	private boolean osIsWindows;
	private boolean osIsUnix;
	//check for space of file to be executed to control ThinkLight
	private boolean skipThinkLight=false;


	public AndroidActuator() {

		lightSwitched=false;

		if(isWindows()) {
			System.out.println("System is Windows");
			brightnessHelperWindows = new BrightnessHelper();
			this.osIsWindows=true;
		} else if(isUnix()) {
			this.osIsUnix=true;
			//osBrightnessHelper erstellen
			System.out.println("System is Windows");
		}


		thinkLightHelper = new ThinkLightHelper();
		// check returns true if no dll is found
		skipThinkLight = thinkLightHelper.checkForDLL();


		start = System.currentTimeMillis();
	}

//todo gui is in the making
	/**
	 * Just main class to start whole process
	 */

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
	 * This method interprets the light values sent by a smartphone via udp every second and adjusts the screen brightness
	 * on windows systems and controls the led built into ThinkPads
	 *
	 * params sensorValues the values send via UDP from the sensor/smartphone
	 *
	 **/
	public void lightActuator(AndroidSensor sensorValues) {

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

				if (!skipThinkLight) {
					thinkLightHelper.switchThinkLight();
				}
				if (osIsWindows) {
					brightnessHelperWindows.setBrightness(100);
				}
				System.out.println("Now light value is over 0 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
				System.out.println("LIGHT is ON");
			}
			if (lightSwitched==true) {
				System.out.println(lightSwitched +"Licht ist an und unter 50");
				System.out.println("LIGHT is ON");
			}
		}

		if (sensorValues.getAmbientlight() >= 50 && sensorValues.getAmbientlight() < 120) {
			if (lightSwitched == false) {
				lightSwitched = true;
				if (!skipThinkLight) {
					thinkLightHelper.switchThinkLight();
				}
				if (osIsWindows) {
					brightnessHelperWindows.setBrightness(95);
				}
				System.out.println("Now light value is over 50 lux, Switch ON");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
			}
			if (lightSwitched == true) {
				System.out.println(lightSwitched + "Licht ist an und über 50-200");
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
			}
		}


			if (lightSwitched==true && sensorValues.getAmbientlight() > 120) {
				System.out.println("the exact value is: " + sensorValues.getAmbientlight());
				lightSwitched=false;

				if (!skipThinkLight) {
					thinkLightHelper.switchThinkLight();
				}


				if (osIsWindows) {
					brightnessHelperWindows.setBrightness(85);
				}
				System.out.println(lightSwitched +"Licht ist aus");
			}
			if (lightSwitched==false && sensorValues.getAmbientlight() > 200) {
                System.out.println("last trigger light out over 200 lux");
				if (osIsWindows) {
					brightnessHelperWindows.setBrightness(85);
				}
				return;
			}
		System.out.println("============================");
		System.out.println("Licht ist  : " + lightSwitched);
		System.out.println("the exact value is: " + sensorValues.getAmbientlight());
		return;
	}

}