/*
 * Copyright (C) 2013 The CyanogenMod Project
 * Copyright (C) 2014 The OmniROM Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.omnirom.hardware;

import org.omnirom.hardware.util.FileUtils;
import org.omnirom.hardware.util.DataParser;
import org.omnirom.hardware.util.DataParser.Data;

import java.io.File;

/* 
 * Vibrator intensity adjustment
 *
 * Exports methods to get the valid value boundaries, the
 * default and current intensities, and a method to set
 * the vibrator.
 *
 * Values exported by min/max can be the direct values required
 * by the hardware, or a local (to VibratorHW) abstraction that's
 * internally converted to something else prior to actual use. The
 * Settings user interface will normalize these into a 0-100 (percentage)
 * scale before showing them to the user, but all values passed to/from
 * the client (Settings) are in this class' scale.
 */

/* This would be just "Vibrator", but it conflicts with android.os.Vibrator */
public class VibratorHW {

    private static Data data = DataParser.getData(R.array.hwf_vibrator);

    private static final String LEVEL_PATH = data.value[0];
    private static final String LEVEL_MAX = data.value[1];
    private static final String LEVEL_MIN = data.value[2];
    private static final String LEVEL_DEFAULT = data.value[3];
    private static final String LEVEL_WARNING = data.value[4];
    
    /* 
     * All HAF classes should export this boolean. 
     * Real implementations must, of course, return true 
     */

    public static boolean isSupported() {
        if (data.supported && LEVEL_PATH != null) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Set the vibrator intensity to given integer input. That'll
     * be a value between the boundaries set by get(Max|Min)Intensity
     * (see below), and it's meant to be locally interpreted/used.
     */

    public static boolean setIntensity(int intensity)  {
        File f = new File(LEVEL_PATH);

        if (f.exists()) {
            return FileUtils.writeLine(LEVEL_PATH, String.valueOf(intensity));
        } else {
            return false;
        }
    }

    /* 
     * What's the maximum integer value we take for setIntensity()?
     */

    public static int getMaxIntensity()  {
        if (LEVEL_MAX != null) {
            File f = new File(LEVEL_MAX);

            if (f.exists()) {
               return Integer.parseInt(FileUtils.readOneLine(LEVEL_MAX));
            } else {
            // LEVEL_MAX might simply be an integer, not a path
               return Integer.parseInt(LEVEL_MAX);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* 
     * What's the minimum integer value we take for setIntensity()?
     */

    public static int getMinIntensity()  {
        if (LEVEL_MIN != null) {
            File f = new File(LEVEL_MIN);

            if (f.exists()) {
               return Integer.parseInt(FileUtils.readOneLine(LEVEL_MIN));
            } else {
            // LEVEL_MIN might simply be an integer, not a path
               return Integer.parseInt(LEVEL_MIN);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* 
     * Is there a value between the 2 above which is considered
     * the safe max? If not, return anything < 0
     */

    public static int getWarningThreshold()  {
        if (LEVEL_WARNING != null) {
            File f = new File(LEVEL_WARNING);

            if (f.exists()) {
               return Integer.parseInt(FileUtils.readOneLine(LEVEL_WARNING));
            } else {
            // LEVEL_WARNING might simply be an integer, not a path
               return Integer.parseInt(LEVEL_WARNING);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* 
     * What's the current intensity value?
     */

    public static int getCurIntensity()  {
        if (LEVEL_PATH != null) {
            File f = new File(LEVEL_PATH);

            if(f.exists()) {
                return Integer.parseInt(FileUtils.readOneLine(LEVEL_PATH));
            } else {
                return 0;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* 
     * What's the shipping intensity value?
     */

    public static int getDefaultIntensity()  {
        if (LEVEL_DEFAULT != null) {
            File f = new File(LEVEL_DEFAULT);

            if (f.exists()) {
               return Integer.parseInt(FileUtils.readOneLine(LEVEL_WARNING));
            } else {
            // LEVEL_WARNING might simply be an integer, not a path
               return Integer.parseInt(LEVEL_WARNING);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * This method returns the type of setting this is
     *
     * @return String Used to determine what kind of UI setup this is going to use.
     */
    public static String getType() {
        return data.type;
    }
}
