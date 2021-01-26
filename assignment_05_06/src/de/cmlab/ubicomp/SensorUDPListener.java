package de.cmlab.ubicomp;

import de.cmlab.ubicomp.lib.model.AndroidSensor;

import java.util.Observable;
import java.util.Observer;

public class SensorUDPListener implements Observer {
    AndroidTest test = new AndroidTest();

    public SensorUDPListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(Observable o, Object arg) {


        /*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
        AndroidSensor sensorValues = (AndroidSensor) arg;


        // print the values of the AmbientLight Sensor with different comments according to intensity of current lux value
        test.adjustLightVolume(sensorValues);
    }
}
