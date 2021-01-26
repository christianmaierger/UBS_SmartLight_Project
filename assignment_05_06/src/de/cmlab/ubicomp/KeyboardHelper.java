package de.cmlab.ubicomp;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardHelper {
 private boolean lightOn;
 Robot robot1;

    public KeyboardHelper() {
        this.lightOn=false;
        try {
           robot1 = new Robot();
        } catch (AWTException e) {
            System.err.println("Robot could not be created to emulate keyboard input: " + e.getCause());
        }
    }
/*
    public static void main(String[] args) {
        try {
           Robot robot = new Robot();
            robot.keyPress(255);

            robot.keyRelease(255);

        } catch (AWTException e) {
            System.err.println("Robot could not be created to emulate keyboard input: " + e.getCause());
        }

    }*/

   public  void switchLight() {

        try {
        Robot  robot = new Robot();

            robot.keyPress(KeyEvent.VK_SPACE);

            robot.keyRelease(255);
        } catch (AWTException e) {
            System.err.println("Robot could not be created to emulate keyboard input: " + e.getCause());
        }



    }


}
