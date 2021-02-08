package de.cmlab.ubicomp;

import de.cmlab.ubicomp.lib.model.AndroidSensor;

import java.util.Observable;
import java.util.Observer;


//todo give apropriate credit to architect of the class
/**
 * class we got from the course
 */
public class SensorUDPListener implements Observer {
   AndroidActuator actuator;

    public SensorUDPListener(AndroidActuator actuator) {
        // TODO Auto-generated constructor stub
        actuator=actuator;
    }

    @Override
    public void update(Observable o, Object arg) {


        /*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
        AndroidSensor sensorValues = (AndroidSensor) arg;


        actuator.lightActuator(sensorValues);
    }
}
