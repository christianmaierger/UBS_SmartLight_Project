package de.cmlab.ubicomp;

import java.io.File;
import java.io.IOException;

public class ThinkLightHelper {
    private String dll1 = "C:\\DRIVERS\\HOTKEY\\OSD\\virtfn_0.dll,ThinkLight";
    private String dll2 = "C:\\Program Files\\Lenovo\\HOTKEY\\virtfn_0.dll,ThinkLight";
    private String dllFileUsed;
    private String runDll32 = "C:\\Windows\\System32\\Rundll32.exe";
    private Process process;



    public void checkForDLL() {
        File f = new File(dll1);
        File f2 = new File(dll2);
        if (f.exists() && !f.isFile()) {
            dllFileUsed = dll1;
        } else if (f2.exists() && !f2.isFile()) {
            dllFileUsed = dll2;
        }

    }

    public void switchThinkLight() {

        try {
            process = new ProcessBuilder(runDll32, dllFileUsed).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
