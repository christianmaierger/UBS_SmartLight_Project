package de.cmlab.ubicomp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helps to adjust the brightness of the screen for windows systems
 */

public class BrightnessHelper {
    boolean graphicsDriverDoesNotSUpportPSCommand=false;


    /**
     * Through calling a cmd command adjuts the brightness of the screen by a put in value
     *
     * @param brightness value of screen brightness from 0-100
     */
    public void setBrightness(int brightness)
            {

                if (graphicsDriverDoesNotSUpportPSCommand) {
                    return;
                }

        String command = new String();

              command  = String.format("$brightness = %d;", brightness)
                + "$delay = 0;"
                + "$screen = Get-WmiObject -Namespace root\\wmi -Class WmiMonitorBrightnessMethods;"
                + "$screen.wmisetbrightness($delay, $brightness)";

        command = "powershell.exe  " + command;


                Process powerShell = null;
                try {
                    powerShell = Runtime.getRuntime().exec(command);
                    powerShell.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                    graphicsDriverDoesNotSUpportPSCommand=true;
                }



        //Report any error messages
        String line;

        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShell.getErrorStream()));
                try {
                    line = stderr.readLine();
                    if (line != null) {
                        System.err.println("Standard Error:");
                        do
                        {
                            System.err.println(line);
                        } while ((line = stderr.readLine()) != null);

                    }
                    stderr.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
    }

}