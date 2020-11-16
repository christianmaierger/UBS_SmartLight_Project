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
			AndroidSensor sensorValues = (AndroidSensor)arg;
			//System.out.println(sensorValues.toString());

			if (sensorValues.getAmbientlight()>=200 && sensorValues.getAmbientlight()<300) {
				System.out.println("Jetzt ist es über 200 Lux hell");
				System.out.println("Der genaue Wert ist: " + sensorValues.getAmbientlight());
			}
			if (sensorValues.getAmbientlight()>=20 && sensorValues.getAmbientlight()<200) {
				System.out.println("Jetzt ist es über 20 Lux hell");
				System.out.println("Der genaue Wert ist: " + sensorValues.getAmbientlight());
			}
			if (sensorValues.getAmbientlight()>=300) {
				System.out.println("Jetzt ist es über 300 Lux hell");
				System.out.println("Der genaue Wert ist: " + sensorValues.getAmbientlight());
			}
			System.out.println("============================");
		}
	
	}
}
