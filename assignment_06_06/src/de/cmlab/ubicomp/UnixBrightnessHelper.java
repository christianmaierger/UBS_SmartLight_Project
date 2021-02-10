package de.cmlab.ubicomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helps to adjust the brightness of the screen for unix systems
 */

public class UnixBrightnessHelper {
    boolean graphicsDriverDoesNotSUpportPSCommand = false;


    /**
     * Through calling a command adjuts the brightness of the screen by a put in value
     *
     * @param brightness value of screen brightness from 0-1
     */
    public void setBrightness(float brightness, boolean noErrorLog) {

        if (graphicsDriverDoesNotSUpportPSCommand) {
            return;
        }
        // tested command line:
        // ( xterm && screenname=$(xrandr | grep ' connected' | cut -f1 -d ' ') && xrandr --output $screenname --brightness 0.7 )

        String command = new String();

        command = String.format("$brightness = %d;", brightness)
                + "& " +
                "screenname=$(xrandr | grep \' connected\' |cut -f1 -d \' \') " +
                " && " +
                "xrandr --output $screenname --brightness $brightness; )";

        command = "( xterm " + command;

        Process pr = null;
        try {
            Runtime rt = Runtime.getRuntime();
            pr = rt.exec(command);

        } catch (IOException e) {
            // try to not call console command again
            System.err.println("This OS does not support xterm: " + e.getMessage());
            graphicsDriverDoesNotSUpportPSCommand = true;
        }


        //Report any error messages
        String line;

        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                pr.getErrorStream()));
        try {
            line = stderr.readLine();
            if (line != null && !noErrorLog) {
                System.err.println("This OS does not support xterm");
                // try to not call console again to prevent errors
                graphicsDriverDoesNotSUpportPSCommand=true;
                // one print for debbuging
                do {
                    System.err.println(line);
                } while ((line = stderr.readLine()) != null);

            }
            stderr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
