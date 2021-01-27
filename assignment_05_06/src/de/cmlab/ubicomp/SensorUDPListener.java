package de.cmlab.ubicomp;

import de.cmlab.ubicomp.lib.model.AndroidSensor;

import java.util.Observable;
import java.util.Observer;


//todo give apropriate credit to architect of the class
/**
 * class we got from the course
 */
public class SensorUDPListener implements Observer {
    AndroidActuator test = new AndroidActuator();

    public SensorUDPListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(Observable o, Object arg) {


        /*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
        AndroidSensor sensorValues = (AndroidSensor) arg;


        test.lightActuator(sensorValues);
    }
}
