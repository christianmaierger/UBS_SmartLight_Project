import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

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


			// create a scanner so we can read the command-line input
			Scanner scanner = new Scanner(System.in);

			//  prompt for the user's input
			System.out.print("Please hit 1 to start or 2 to close programm: ");

			// get their input as a String
			String input;
			int in;

			if (scanner.hasNext()) {
				input = scanner.next();

				try {
					in = Integer.parseInt(input);
					if (in != 1 && in != 2) {
						// prompt for their age
						System.out.println("Yout input was: " + in);
						System.out.println("Only ! or 2 allowed as input");
					}
				}
				catch (Exception e){
					return;
				}




				if (in == 1) {
					if (sensorValues.getAmbientlight() >= 1 && sensorValues.getAmbientlight() < 20) {
						System.out.println("At least there is some light");
						System.out.println("the actual value is: " + sensorValues.getAmbientlight());
						return;
					}
					if (sensorValues.getAmbientlight() >= 20 && sensorValues.getAmbientlight() < 200) {
						System.out.println("Now light value is over 20 lux");
						System.out.println("exact value is: " + sensorValues.getAmbientlight());
						return;
					}
					if (sensorValues.getAmbientlight() >= 200 && sensorValues.getAmbientlight() < 300) {
						System.out.println("Now light value is over 200 lux");
						System.out.println("exact value is: " + sensorValues.getAmbientlight());
						return;
					}
					if (sensorValues.getAmbientlight() >= 300) {
						System.out.println("Now light value is over 300 lux");
						System.out.println("the exact value is: " + sensorValues.getAmbientlight());
						return;
					}
					System.out.println("============================");
				}
				if (in == 2) {
					System.exit(-1);
				}
			}
		}
	
	}
}
