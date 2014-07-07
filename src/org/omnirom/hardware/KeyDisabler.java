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
 * Disable capacitive keys
 *
 * This is intended for use on devices in which the capacitive keys
 * can be fully disabled for replacement with a soft navbar. You
 * really should not be using this on a device with mechanical or
 * otherwise visible-when-inactive keys
 */

public class KeyDisabler {

    private static Data data = DataParser.getData(R.array.hwf_keyDisabler);

    private static final String PATH = data.value[0];

    /*
     * All HAF classes should export this boolean.
     * Real implementations must, of course, return true
     */

    public static boolean isSupported() {
        if (data.supported && PATH != null) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Are the keys currently blocked?
     */

    public static boolean isActive() {
        if (PATH != null) {
            return (Integer.parseInt(FileUtils.readOneLine(PATH)) == 1);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /*
     * Disable capacitive keys
     */

    public static boolean setActive(boolean state) {
        if (PATH != null) {
            if (state == true) {
                return FileUtils.writeLine(PATH, "1");
            } else {
                return FileUtils.writeLine(PATH, "0");
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
