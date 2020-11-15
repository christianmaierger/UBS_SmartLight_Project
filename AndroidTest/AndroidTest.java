import java.util.Observable;
import java.util.Observer;

import de.cmlab.ubicomp.lib.SensorUDPReceiver;
import de.cmlab.ubicomp.lib.model.AndroidSensor;
import de.cmlab.ubicomp.util.*;
import java.net.*;

/**
* This class is for testing the SensorUDPReceiver
*
*@author Hesham Omran
*/
public class AndroidTest {

	public static void main(String[] args){
		
		// now I will try to fiddel around with a datagramSocket,
		//as this seems to be necessary when sending/recieving data with UDP
		
		byte[] data = new byte[ 1024 ];
		DatagramSocket socket;
		DatagramPacket packet;
		
		try {
			socket = new DatagramSocket( 5000 );
		} catch (Exception e) {
			return;
		}
		packet = new DatagramPacket( data, data.length );
		
		try {
			socket.receive(packet);
		} catch (Exception e) {
			return;
		}
		
		
		
		/*initiate a receiver by defining a port 
		number that will be sent to the receiver from the app*/
		// reciver is a Observable!
		SensorUDPReceiver receiver = new SensorUDPReceiver(5000);
		/*create a listener as shown below and let it implement Observer to
		to get the app updates*/
		SensorUDPListener listener = new SensorUDPListener();
		receiver.addObserver(listener);
		
		//here I instanciate a new Sensor with an id like the constructor states
		// but not sure if I will need the instance
		AndroidSensor firstSensor = new AndroidSensor("one");
		// I try to get the Sensor data
		float light = firstSensor.getAmbientlight();
		
		BytesToSensorValueConverter converter = new BytesToSensorValueConverter();
		// converter instance is not needed it seems? As the following method is static!
		float lightCon = BytesToSensorValueConverter.extractAmbientLightData(packet.getData());
		
		// update is called on a Observer, that is listener and takes a Observable,
		// which is receiver here and an Object, that will be cast to AndroidSensor
		// I am not sure what to take as Objet but probably converted bytes, but converter can
		//not be cast to AndroidSensor?!? What should I pass as Object?
		listener.update(receiver, packet);
	}
	
	public static class SensorUDPListener implements Observer{

		public SensorUDPListener() {
			// TODO Auto-generated constructor stub
			
			//BytesToSensorValueConverter converter = new BytesToSensorValueConverter();
			
		}
		
		@Override
		public void update(Observable o, Object arg) {
			/*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
			// why cast some object to sensor, I thought as arg there was meant to be
			// real sensor data obtained e.g with getAmbientlight as float
			AndroidSensor sensorValues = (AndroidSensor)arg;
			System.out.println(sensorValues.getAmbientlight());
			System.out.println("test");
			System.out.println(sensorValues.toString());
			System.out.println("============================");
		}
	
	}
}
