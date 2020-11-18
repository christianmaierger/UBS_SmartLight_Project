import java.util.Observable;
import java.util.Observer;

import de.cmlab.ubicomp.lib.SensorUDPReceiver;
import de.cmlab.ubicomp.lib.model.AndroidSensor;

/**
 * This class is for testing the SensorUDPReceiver
 *
 *@author Hesham Omran
 */
public class AndroidTest {

	public static void main(String[] args){
		/*initiate a receiver by defining a port 
		number that will be sent to the receiver from the app*/
		SensorUDPReceiver receiver = new SensorUDPReceiver(5000);
		/*create a listener as shown below and let it implement Observer to
		to get the app updates*/
		SensorUDPListener listener = new SensorUDPListener();
		receiver.addObserver(listener);
	}

	public static class SensorUDPListener implements Observer{

		public SensorUDPListener() {
			// TODO Auto-generated constructor stub

		}

		@Override
		public void update(Observable o, Object arg) {
			/*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
			AndroidSensor sensorValues = (AndroidSensor)arg;
			System.out.println(sensorValues.toString());
			//access to ambient light and orientation sensor
			System.out.println("============================");
			System.out.println("Current Lux: " + sensorValues.getAmbientlight());
			if(sensorValues.getAmbientlight() <= 10.0){

				System.out.println("Should I switch the light on for you?");
				System.out.println("If YES, then please change the orientation of your smartphone to the right.");
				//weird sensor data
				if(sensorValues.getOrientation()[2] >= 0.40){
					System.out.println("Your request is processed ... smart home office to be continued :D");
				}
			}
			else{
				System.out.println(" \n");
			}
			System.out.println("============================");

		}

	}
}
