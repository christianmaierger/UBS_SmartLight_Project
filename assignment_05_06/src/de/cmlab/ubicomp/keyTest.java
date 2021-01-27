package de.cmlab.ubicomp;


import com.melloware.jintellitype.*;

import java.io.IOException;


/**
 * obsolete class just to show our effort
 */
public class keyTest {

    public static void main(String[] args) {

       /* try {
           BrightnessHelper.setBrightness(50);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, (int)'A');

       /* System.out.println("Start");

        JIntellitype.getInstance().registerHotKey(1, JIntellitype.APPCOMMAND_TREBLE_UP, (int)'A');
        JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_ALT + JIntellitype.MOD_SHIFT, (int)'B');

        System.out.println("End");*/


    }



    }

