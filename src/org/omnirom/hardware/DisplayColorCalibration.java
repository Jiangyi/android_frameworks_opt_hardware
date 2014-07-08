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
 * Display RGB intensity calibration (kcal)
 *
 * Exports methods to get the valid value boundaries, the
 * current color values, and a method to set new ones.
 *
 * Values exported by min/max can be the direct values required
 * by the hardware, or a local (to DisplayColorCalibration) abstraction
 * that's internally converted to something else prior to actual use. The
 * Settings user interface will normalize these into a 0-100 (percentage)
 * scale before showing them to the user, but all values passed to/from
 * the client (Settings) are in this class' scale.
 */

public class DisplayColorCalibration {

    private static Data data = DataParser.getData(R.array.hwf_displayColorCaLib);

    private static int numOfValues = data.value.length;

    // We support the four most common color calibration methods
    private static String COLOR_FILE;
    private static String COLOR_FILE_CTRL;
    private static String[] COLOR_CHANNEL_FILE;
    private static String COLOR_MIN;
    private static String COLOR_MAX;
    private static String COLOR_DEF;

    // 3 of the values are max, min, and default; The rest are paths
    if (numOfValues - 3 == 1) {
        // Only one path for color calibration
        COLOR_FILE = data.value[0];
        COLOR_MIN = data.value[1];
        COLOR_MAX = data.value[2];
        COLOR_DEF = data.value[3];
    } else if (numOfValues - 3 == 2) {
        // One path for color calibration, the other for control
        COLOR_FILE = data.value[0];
        COLOR_FILE_CTRL = data.value[1];
        COLOR_MIN = data.value[2];
        COLOR_MAX = data.value[3];
        COLOR_DEF = data.value[4];
    } else if (numOfValues - 3 == 3) {
        // Three paths for R, G, B respectively
        COLOR_CHANNEL_FILE = new String[] { data.value[0],
                                    data.value[1],
                                    data.value[2] };
        COLOR_MIN = data.value[3];
        COLOR_MAX = data.value[4];
        COLOR_DEF = data.value[5];
    } else if (numOfValues - 3 == 4) {
        // Three paths for R, G, B respectively, plus an alternate path
        COLOR_FILE = data.value[0];
        COLOR_CHANNEL_FILE = new String[] { data.value[1],
                                    data.value[2],
                                    data.value[3] };
        COLOR_MIN = data.value[4];
        COLOR_MAX = data.value[5];
        COLOR_DEF = data.value[6];
    }

    /*
     * All HAF classes should export this boolean.
     * Real implementations must, of course, return true
     */

    public static boolean isSupported() {
        if (data.supported && COLOR_FILE != null || COLOR_CHANNEL_FILE[0] != null) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Set the RGB values to the given input triplet. Input is
     * expected to consist of 3 values, space-separated, each to
     * be a value between the boundaries set by get(Max|Min)Value
     * (see below), and it's meant to be locally interpreted/used.
     */

    public static boolean setColors(String colors) {
        if (COLOR_FILE != null) {
            boolean result = FileUtils.writeLine(COLOR_FILE, colors);
            if (result && COLOR_FILE_CTRL != null) {
                result = FileUtils.writeLine(COLOR_FILE_CTRL, "1");
            }
            return result;
        } else if (COLOR_CHANNEL_FILE[0] != null) {
            String[] valuesSplit = colors.split(" ");

            boolean result = true;
            for (int i = 0; i < valuesSplit.length; i++) {
                String targetFile = COLOR_FILE[i];
                result &= FileUtils.writeLine(COLOR_FILE[i], valuesSplit[i]);
            }
            return result;
        } else {
           throw new UnsupportedOperationException();
        }
    }

    /*
     * What's the maximum integer value we take for a color
     */

    public static int getMaxValue() {
        if (COLOR_MAX != null) {
            return Integer.parseInt(COLOR_MAX);
        } else {
            throw UnsupportedOperationException();
        }
    }

    /*
     * What's the minimum integer value we take for a color
     */

    public static int getMinValue() {
        if (COLOR_MIN != null) {
            return Integer.parseInt(COLOR_MIN);
        } else {
            throw UnsupportedOperationException();
        }
    }

    /*
     * What's the default integer value we take for a color
     */

    public static int getDefValue() {
        if (COLOR_DEF != null) {
            return Integer.parseInt(COLOR_DEF);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /*
     * What's the current RGB triplet?
     * This should return a space-separated set of integers in
     * a string, same format as the input to setColors()
     */

    public static String getCurColors() {
        if (COLOR_FILE != null) {
            return FileUtils.readOneLine(COLOR_FILE);
        } else if (COLOR_CHANNEL_FILE[0] != null) {
            StringBuilder value = new StringBuilder()
            for (String filePath : COLOR_CHANNEL_FILE) {
                values.append(FileUtils.readOneLine(filePath)).append(" ");
            }
            return values.toString();
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
