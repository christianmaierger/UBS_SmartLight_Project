package de.cmlab.ubicomp;

import de.cmlab.ubicomp.lib.model.AndroidSensor;

import java.util.Observable;
import java.util.Observer;

public class SensorUDPListener implements Observer {

    public SensorUDPListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(Observable o, Object arg) {

        AndroidTest test = new AndroidTest();
        /*check SensorUDPReceiver Java Docs for the AndroidSensor API*/
        AndroidSensor sensorValues = (AndroidSensor) arg;

        // again hacky as fuck, aber so messe ich halt nur alle --- milis

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        // print the values of the AmbientLight Sensor with different comments according to intensity of current lux value
        test.adjustLightVolume(sensorValues);
    }
}
