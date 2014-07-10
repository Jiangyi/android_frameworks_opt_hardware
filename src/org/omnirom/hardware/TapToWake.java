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

import com.android.internal.R;

import org.omnirom.hardware.util.FileUtils;
import org.omnirom.hardware.util.DataParser;
import org.omnirom.hardware.util.DataParser.Data;

import java.io.File;

/**
 * Tap (usually double-tap) to wake. This *should always* be supported by
 * the hardware directly. A lot of recent touch controllers have a firmware
 * option for this
 */
public class TapToWake {

    private static DataParser dp;
    private static Data data = dp.getData(com.android.internal.R.array.hwf_tapToWake);

    private static final String PATH = data.value[0];

    /**
     * Whether device supports it
     *
     * @return boolean Supported devices must return always true
     */
    public static boolean isSupported() {
        if (data.supported && PATH != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method return the current activation state
     *
     * @return boolean Must be false when feature is not supported or 
     * disabled.
     */
    public static boolean isEnabled() {
        if (PATH != null) {
            return (Integer.parseInt(FileUtils.readOneLine(PATH)) == 1);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * This method allows to set activation state
     *
     * @param state The new state
     * @return boolean for on/off, exception if unsupported
     */
    public static boolean setEnabled(boolean state) {
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
