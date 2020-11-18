import java.util.Observable;
import java.util.Observer;

import de.cmlab.ubicomp.lib.SensorUDPReceiver;
import de.cmlab.ubicomp.lib.model.AndroidSensor;

/**
 * This class is for testing the SensorUDPReceiver
 *
 *@author Hesham Omran
 */
public class 	AndroidTest {

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
			AndroidSensor sensorValues = (AndroidSensor) arg;
			//System.out.println(sensorValues.toString());

			// print the values of the AmbientLight Sensor with different comments according to intensity of current lux value
			printValues(sensorValues);
		}
	}

	 /**
		* This method prints the current value the ambient light sensor of a smortphone sents in lux
	    * According to the value it informs the user between which values the current one lies
		*
		**/
	public static boolean printValues(AndroidSensor sensorValues) {
		if (sensorValues.getAmbientlight() >= 1 && sensorValues.getAmbientlight() < 20) {
			System.out.println("At least there is some light");
			System.out.println("the actual value is: " + sensorValues.getAmbientlight());
			return true;
		}
		if (sensorValues.getAmbientlight() >= 20 && sensorValues.getAmbientlight() < 200) {
			System.out.println("Now light value is over 20 lux");
			System.out.println("exact value is: " + sensorValues.getAmbientlight());
			return true;
		}
		if (sensorValues.getAmbientlight() >= 200 && sensorValues.getAmbientlight() < 300) {
			System.out.println("Now light value is over 200 lux");
			System.out.println("exact value is: " + sensorValues.getAmbientlight());
			return true;
		}
		if (sensorValues.getAmbientlight() >= 300) {
			System.out.println("Now light value is over 300 lux");
			System.out.println("the exact value is: " + sensorValues.getAmbientlight());
			return true;
		}
		System.out.println("============================");
		return false;
	}

}