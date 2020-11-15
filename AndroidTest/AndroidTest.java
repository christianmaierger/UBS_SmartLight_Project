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
		// reciver is a Observable!
		SensorUDPReceiver receiver = new SensorUDPReceiver(5000);
		/*create a listener as shown below and let it implement Observer to
		to get the app updates*/
		SensorUDPListener listener = new SensorUDPListener();
		receiver.addObserver(listener);
		
		//Ich leg jetzt mal nen Sensor an und geb ihm wie im COnst gefordert eine Id
		AndroidSensor firstSensor = new AndroidSensor("one");
		float light = firstSensor.getAmbientlight();
		// update wird auf einen Observer aufgerufen, was listener ist, aber 
		listener.update(receiver, firstSensor);
	}
	
	public static class SensorUDPListener implements Observer{

		public SensorUDPListener() {
			// TODO Auto-generated constructor stub
			
			
		}
		
		@Override
		public void update(Observable o, Object arg) {
			/*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
			// why cast some object to sensor, I thought as arg there was meant to be
			// real sensor data obtained e.g with getAmbientlight as float
			AndroidSensor sensorValues = (AndroidSensor)arg;
			System.out.println(sensorValues.getAmbientlight());
			System.out.println(o.getAmbientlight());
			System.out.println(sensorValues.toString());
			System.out.println("============================");
		}
	
	}
}
