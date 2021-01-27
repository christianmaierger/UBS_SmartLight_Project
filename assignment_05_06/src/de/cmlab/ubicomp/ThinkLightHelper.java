package de.cmlab.ubicomp;

import java.io.File;
import java.io.IOException;


/**
 * Class helps to control the ThinkLight built in to many models of the Lenovo Thinkpad Series on windows systems
 */
public class ThinkLightHelper {
    private String dll1 = "C:\\DRIVERS\\HOTKEY\\OSD\\virtfn_0.dll,ThinkLight";
    private String dll2 = "C:\\Program Files\\Lenovo\\HOTKEY\\virtfn_0.dll,ThinkLight";
    private String dllFileUsed;
    private String runDll32 = "C:\\Windows\\System32\\Rundll32.exe";
    private Process process;
    private boolean noThinkLight=false;

    public boolean isNoThinkLight() {
        return noThinkLight;
    }

    public void setNoThinkLight(boolean noThinkLight) {
        this.noThinkLight = noThinkLight;
    }

    /**
     * checks if there is a file to control the ThinkLight and were it is located, uses the two most common places
     * a reserch by the author revealed
     */
    public void checkForDLL() {
        File f = new File(dll1);
        File f2 = new File(dll2);
        if (f.exists() && !f.isFile()) {
            dllFileUsed = dll1;
        } else if (f2.exists() && !f2.isFile()) {
            dllFileUsed = dll2;
        } else {
            noThinkLight=true;
        }

    }


    // note, there must be another way to work with hw controler of keyboard, but this is way over my understanding
    // of the matter
    /**
     * uses a pretty hacky way to execute a dll, that controlls the state of the ThinkLight
     */
    public void switchThinkLight() {
        if (noThinkLight == true) {
            return;
        }

        try {
            process = new ProcessBuilder(runDll32, dllFileUsed).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
